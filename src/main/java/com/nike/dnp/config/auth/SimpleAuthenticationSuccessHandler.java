package com.nike.dnp.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.common.variable.SuccessCode;
import com.nike.dnp.config.jwt.JwtHelper;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.log.UserLoginLogSaveDTO;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.repository.user.UserRepository;
import com.nike.dnp.service.RedisService;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.log.UserLoginLogService;
import com.nike.dnp.service.user.UserMailService;
import com.nike.dnp.util.*;
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
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.*;

/**
 * 로그인 성공 후 핸들러
 *
 * @author [오지훈]
 * @since 2020. 7. 3. 오전 11:30:01
 * @implNote
 */
@Slf4j
@RequiredArgsConstructor
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	/**
	 * The constant REGEX
	 *
	 * @author [오지훈]
	 */
	private final static String REGEX = "NIKESPACE";

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
	 * 로그인 성공 처리
	 *
	 * @param request        the request
	 * @param response       the response
	 * @param authentication the authentication
	 * @throws IOException the io exception
	 * @author [오지훈]
	 * @since 2020. 7. 3. 오전 11:30:01
	 * @implNote
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
							FailCode.ExceptionError.NOT_FOUND.name()
							, MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name())
					));
			isValid = false;
		}

		//TODO[ojh] 2020-07-02 : 휴면회원 확인 (추후 로직 추가예정)
		if (isValid && ServiceCode.UserStatusEnumCode.DORMANT.toString().equals(user.get().getUserStatusCode())) {
			if (certCode.isEmpty()) {
				//TODO[ojh] 2020-07-02 : [인증코드] 메일 발송
				userMailService.createCertCode(authUserDTO.getUserId());

				JsonUtil.write(response.getWriter()
						, responseService.getSuccessResult(
								FailCode.ConfigureError.IS_DORMANT.name()
								, MessageUtil.getMessage(FailCode.ConfigureError.IS_DORMANT.name())
						));
				isValid = false;
			} else {
				final String decodeCertCode = CryptoUtil.decryptAES256(CryptoUtil.urlDecode(certCode), "Nike DnP").split(REGEX)[1];
				if (FailCode.ExceptionError.ERROR.name().equals(decodeCertCode)) {
					JsonUtil.write(response.getWriter()
							, responseService.getFailResult(
									FailCode.ConfigureError.EXPIRED_CERT_CODE.name()
									, MessageUtil.getMessage(FailCode.ConfigureError.EXPIRED_CERT_CODE.name())
							));
					isValid = false;
				}
				if (isValid && certCode.equals(decodeCertCode)) {
					user.get().updateStatus(ServiceCode.UserStatusEnumCode.NORMAL.toString());
					//TODO[ojh] 2020-07-02 : [휴면계정 해제 안내] 메일 발송
					userMailService.sendMailForChangeDormant(user.get());
				}
			}
		}

		// PW 90일 체크
		if (isValid && userRepository.countByPaswordChangePeriod(authUserDTO.getUserSeq()) > 0) {
			final String randomCode = RandomUtil.randomCertCode2(10);
			final String encodeCertCode = CryptoUtil.urlEncode(CryptoUtil.encryptAES256(authUserDTO.getUserId() + REGEX + randomCode, "Nike DnP"));
			if (FailCode.ExceptionError.ERROR.name().equals(encodeCertCode)) {
				JsonUtil.write(response.getWriter()
						, responseService.getFailResult(
								FailCode.ConfigureError.EXPIRED_CERT_CODE.name()
								, MessageUtil.getMessage(FailCode.ConfigureError.EXPIRED_CERT_CODE.name())
						));
				isValid = false;
			}

			if (isValid) {
				redisService.set("cert:"+authUserDTO.getUserId(), randomCode, 60);

				final HashMap<String, Object> map = new HashMap<>();
				map.put("certCode", encodeCertCode);
				JsonUtil.write(response.getWriter()
						, responseService.getFailResult(
								FailCode.ConfigureError.OVERTIME_PASSWORD.name()
								, MessageUtil.getMessage(FailCode.ConfigureError.OVERTIME_PASSWORD.name())
								, Collections.singleton(map)
						));
				isValid = false;
			}
		}

		// 비밀번호가 변경되었을 경우
		if (isValid && user.get().getPasswordChangeYn().equals("Y")) {
			if (ObjectUtils.isEmpty(certCode)) {
				// [인증코드] 메일 발송
				userMailService.sendMailForAuthEmail(user.get());

				JsonUtil.write(response.getWriter()
						, responseService.getFailResult(
								SuccessCode.ConfigureSuccess.SEND_EMAIL_CERT_CODE.name()
								, MessageUtil.getMessage(SuccessCode.ConfigureSuccess.SEND_EMAIL_CERT_CODE.name())
						));
				isValid = false;
			} else  {
				final String redisCertCode = (String) redisService.get("cert:"+authUserDTO.getUserId());
				if(ObjectUtils.isEmpty(redisCertCode)) {
					JsonUtil.write(response.getWriter()
							, responseService.getFailResult(
									FailCode.ConfigureError.EXPIRED_CERT_CODE.name()
									, MessageUtil.getMessage(FailCode.ConfigureError.EXPIRED_CERT_CODE.name())
							));
					isValid = false;
				} else if(!redisCertCode.equals(certCode)) {
					JsonUtil.write(response.getWriter()
							, responseService.getFailResult(
									FailCode.ConfigureError.NOT_MATCH_CERT_CODE.name()
									, MessageUtil.getMessage(FailCode.ConfigureError.NOT_MATCH_CERT_CODE.name())
							));
					isValid = false;
				}
			}
		}

		// 최초접속여부/약관동의여부
		if (isValid
				&& !ServiceCode.YesOrNoEnumCode.Y.toString().equals(termsAgreeYn)
				&& ServiceCode.YesOrNoEnumCode.N.toString().equals(user.get().getTermsAgreeYn())) {
			JsonUtil.write(response.getWriter()
					, responseService.getSuccessResult(
							SuccessCode.ConfigureSuccess.TERMS_AGREEMENT.name()
							, MessageUtil.getMessage(SuccessCode.ConfigureSuccess.TERMS_AGREEMENT.name())
					));
			isValid = false;
		}

		if (isValid) {
			final StringBuilder redisKey = new StringBuilder("auths:");
			redisKey.append(authUserDTO.getUsername())
					.append(LocalDateTime.now().getLong(ChronoField.MILLI_OF_DAY));
			final Algorithm algorithm = Algorithm.HMAC512(JwtHelper.SECRET.getBytes());
			// jwt 토큰 생성
			final HashMap<String, Object> head = new HashMap<>();
			head.put("typ","JWT");
			final String token = JWT.create()
									.withSubject(authUserDTO.getUsername())
									.withClaim("rds",redisKey.toString())
									.withClaim("iat",new Date())
									.withHeader(head)
									.withExpiresAt(new Date(System.currentTimeMillis() + JwtHelper.EXPIRATION_TIME))
					.sign(algorithm);

			// header 에 토큰 입력
			authUserDTO.setPassword(""); // 비밀번호 삭제
			response.addHeader(JwtHelper.HEADER_STRING, JwtHelper.TOKEN_PREFIX +token);
			JsonUtil.write(response.getWriter(), responseService.getSingleResult(authUserDTO));

			// 레디스 토큰 저장
			redisService.set(redisKey.toString(), token, Integer.parseInt(String.valueOf(BeanUtil.getBean("userSessionTime"))));

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
			if (ServiceCode.YesOrNoEnumCode.Y.toString().equals(termsAgreeYn)
					&& ServiceCode.YesOrNoEnumCode.N.toString().equals(user.get().getTermsAgreeYn())
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
