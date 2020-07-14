package com.nike.dnp.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.common.variable.SuccessEnumCode;
import com.nike.dnp.config.jwt.JwtHelper;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.log.UserLoginLogSaveDTO;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.repository.user.UserRepository;
import com.nike.dnp.service.RedisService;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.log.UserLoginLogService;
import com.nike.dnp.service.user.UserMailService;
import com.nike.dnp.util.CryptoUtil;
import com.nike.dnp.util.JsonUtil;
import com.nike.dnp.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Optional;

/**
 * 로그인 성공 후 핸들러
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 3. 오전 11:30:01
 * @Description
 */
@Slf4j
@RequiredArgsConstructor
public class
SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	/**
	 * The Response service
	 *
	 * @author [오지훈]
	 */
	private final ResponseService responseService;

	/**
	 * The Login log service
	 *
	 * @author [오지훈]
	 */
	private final UserLoginLogService loginLogService;

	/**
	 * The Redis service
	 *
	 * @author [오지훈]
	 */
	private final RedisService redisService;

	/**
	 * The User repository
	 *
	 * @author [오지훈]
	 */
	private final UserRepository userRepository;

	/**
	 * The User mail service
	 *
	 * @author [오지훈]
	 */
	private final UserMailService userMailService;

	/**
	 * On authentication success.
	 *
	 * @param request        the request
	 * @param response       the response
	 * @param authentication the authentication
	 * @throws IOException the io exception
	 * @author [오지훈]
	 * @CreatedOn 2020. 7. 3. 오전 11:30:01
	 * @Description
	 */
	@Transactional
	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request,
										final HttpServletResponse response,
										final Authentication authentication) throws IOException {
		log.info("SimpleAuthenticationSuccessHandler.onAuthenticationSuccess");
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpStatus.OK.value());
		final AuthUserDTO authUserDTO = (AuthUserDTO) authentication.getPrincipal();
		final Optional<User> user = userRepository.findById(authUserDTO.getUserSeq());
		final String certCode = StringUtils.defaultString(request.getParameter("certCode"));
		final String termsAgreeYn = StringUtils.defaultString(request.getParameter("termsAgreeYn"), "N");
		boolean isValid = true;

		// 유저 존재여부 확인
		if(!user.isPresent()) {
			JsonUtil.write(response.getWriter()
					, responseService.getFailResult(
							ErrorEnumCode.UserError.NOT_FOUND.toString()
							, ErrorEnumCode.UserError.NOT_FOUND.getMessage()
					));
			isValid = false;
		}

		//TODO[ojh] 2020-07-02 : 휴면회원 확인 (추후 로직 추가예정)
		if (isValid && ServiceEnumCode.UserStatusEnumCode.DORMANT.toString().equals(user.get().getUserStatusCode())) {
			if (certCode.isEmpty()) {
				//TODO[ojh] 2020-07-02 : [인증코드] 메일 발송
				userMailService.createCertCode(authUserDTO.getUserId());

				JsonUtil.write(response.getWriter()
						, responseService.getFailResult(
								ErrorEnumCode.LoginError.IS_DORMANT.toString()
								, ErrorEnumCode.LoginError.IS_DORMANT.getMessage()
						));
				isValid = false;
			} else {
				final String decodeCertCode = CryptoUtil.decryptAES256(CryptoUtil.urlDecode(certCode), "Nike DnP").split("\\|")[1];
				if (certCode.equals(decodeCertCode)) {
					user.get().updateStatus(ServiceEnumCode.UserStatusEnumCode.NORMAL.toString());
					//TODO[ojh] 2020-07-02 : [휴면계정 해제 안내] 메일 발송
					userMailService.sendMailForChangeDormant(user.get());
				}
			}
		}

		// PW 90일 체크
		if (isValid && userRepository.countByPaswordChangePeriod(authUserDTO.getUserSeq()) > 0) {
			final String randomCode = RandomUtil.randomCertCode2(10);
			final String encodeCertCode = CryptoUtil.urlEncode(CryptoUtil.encryptAES256(authUserDTO.getUserId() + "|" + randomCode, "Nike DnP"));
			redisService.set("cert:"+authUserDTO.getUserId(), randomCode, 60);

			final HashMap<String, Object> payload = new HashMap<>();
			payload.put("certCode", encodeCertCode);
			JsonUtil.write(response.getWriter()
					, responseService.getFailResult(
							ErrorEnumCode.LoginError.OVERTIME_PASSWORD.toString()
							, ErrorEnumCode.LoginError.OVERTIME_PASSWORD.getMessage()
							, payload
					));
			isValid = false;
		}

		// 비밀번호가 변경되었을 경우
		if (isValid && user.get().getPasswordChangeYn().equals("Y")) {
			if (certCode.isEmpty()) {
				// [인증코드] 메일 발송
				userMailService.createCertCode(authUserDTO.getUserId());

				JsonUtil.write(response.getWriter()
						, responseService.getFailResult(
								SuccessEnumCode.LoginSuccess.SEND_EMAIL_CERT_CODE.toString()
								, SuccessEnumCode.LoginSuccess.SEND_EMAIL_CERT_CODE.getMessage()
						));
				isValid = false;
			} else  {
				final String redisCertCode = (String) redisService.get("cert:"+authUserDTO.getUserId());
				final String decodeCertCode = CryptoUtil.decryptAES256(CryptoUtil.urlDecode(certCode), "Nike DnP");

				if(ObjectUtils.isEmpty(redisCertCode)) {
					JsonUtil.write(response.getWriter()
							, responseService.getFailResult(
									ErrorEnumCode.LoginError.EXPIRED_CERT_CODE.toString()
									, ErrorEnumCode.LoginError.EXPIRED_CERT_CODE.getMessage()
							));
					isValid = false;
				} else if(!redisCertCode.equals(decodeCertCode.split("\\|")[1])) {
					JsonUtil.write(response.getWriter()
							, responseService.getFailResult(
									ErrorEnumCode.LoginError.NOT_MATCH_CERT_CODE.toString()
									, ErrorEnumCode.LoginError.NOT_MATCH_CERT_CODE.getMessage()
							));
					isValid = false;
				}
			}
		}

		// 최초접속여부/약관동의여부
		if (isValid && ServiceEnumCode.YesOrNoEnumCode.N.toString().equals(termsAgreeYn)
				&& ServiceEnumCode.YesOrNoEnumCode.N.toString().equals(user.get().getTermsAgreeYn())) {
			JsonUtil.write(response.getWriter()
					, responseService.getFailResult(
							SuccessEnumCode.LoginSuccess.TERMS_AGREEMENT.toString()
							, SuccessEnumCode.LoginSuccess.TERMS_AGREEMENT.getMessage()
					));
			isValid = false;
		}

		if (isValid) {
			// jwt 토큰 생성
			final String token = JWT.create().withSubject(authUserDTO.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + JwtHelper.EXPIRATION_TIME))
					.sign(Algorithm.HMAC512(JwtHelper.SECRET));

			// header 에 토큰 입력
			response.addHeader(JwtHelper.HEADER_STRING, JwtHelper.TOKEN_PREFIX +token);
			JsonUtil.write(response.getWriter(), responseService.getSuccessResult());

			// 로그인일자 / header정보 업데이트
			final HashMap<String, String> header = new HashMap<>();
			final Enumeration<String> eHeader = request.getHeaderNames();
			while (eHeader.hasMoreElements()) {
				final String headerName = eHeader.nextElement();
				final String headerValue = request.getHeader(headerName);
				header.put(headerName, headerValue);
			}
			user.ifPresent(value -> value.updateLoginDt(header.toString()));

			// 약관동의 업데이트
			if (ServiceEnumCode.YesOrNoEnumCode.Y.toString().equals(termsAgreeYn)
					&& ServiceEnumCode.YesOrNoEnumCode.N.toString().equals(user.get().getTermsAgreeYn())
			) {
				user.ifPresent(User::updateAgreement);
			}

			// 비밀번호 변경 여부 수정
			user.ifPresent(User::updatePasswordChange);

			// 로그인 로그 등록
			final UserLoginLogSaveDTO saveDTO = new UserLoginLogSaveDTO();
			saveDTO.setUserSeq(authUserDTO.getUserSeq());
			saveDTO.setLoginIp(request.getRemoteAddr()); //TODO[ojh] IP 어떻게 받는지..
			loginLogService.save(saveDTO);

			// redis 인증코드 삭제
			redisService.delete("cert:"+authUserDTO.getUserId());
		}
	}
}
