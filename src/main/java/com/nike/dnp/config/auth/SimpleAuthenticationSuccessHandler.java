package com.nike.dnp.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.config.jwt.JwtHelper;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.log.UserLoginLogSaveDTO;
import com.nike.dnp.repository.user.UserRepository;
import com.nike.dnp.service.RedisService;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.log.UserLoginLogService;
import com.nike.dnp.util.CryptoUtil;
import com.nike.dnp.util.JsonUtil;
import com.nike.dnp.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * 로그인 성공 후 핸들러
 */
@Slf4j
@RequiredArgsConstructor
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final ResponseService responseService;

	private final UserLoginLogService loginLogService;
	private final RedisService redisService;

	private final UserRepository userRepository;

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request,
										final HttpServletResponse response,
										final Authentication authentication) throws IOException {
		log.info("SimpleAuthenticationSuccessHandler.onAuthenticationSuccess");
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpStatus.OK.value());
		final AuthUserDTO authUserDTO = (AuthUserDTO) authentication.getPrincipal();

		Boolean isValid = true;
		System.out.println("=======================exception Start==========================");
		//TODO[ojh] 2020-07-02 : PW 90일 체크
		if (userRepository.countByPaswordChangePeriod(authUserDTO.getUserSeq()) > 0) {
			final String certCode = RandomUtil.randomCertCode2(10);
			final String encodeCertCode = CryptoUtil.urlEncode(CryptoUtil.encryptAES256(authUserDTO.getUserId() + "|" + certCode, "Nike DnP"));
			redisService.set("cert:"+authUserDTO.getUserId(), certCode, 60);
			HashMap<String, Object> payload = new HashMap<>();
			payload.put("certCode", encodeCertCode);
			JsonUtil.write(response.getWriter()
					, responseService.getFailResult(
							ErrorEnumCode.LoginError.PASSWORD_OVERTIME.toString()
							, ErrorEnumCode.LoginError.PASSWORD_OVERTIME.getMessage()
							, payload
					));
			isValid = false;
		}

		//TODO[ojh] 2020-07-02 : 비밀번호가 변경되었을 경우?
		//TODO[ojh] 2020-07-02 : 최초접속여부/약관동의여부
		System.out.println("=======================exception End============================");

		if (isValid) {
			// jwt 토큰 생성
			final String token = JWT.create().withSubject(authUserDTO.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + JwtHelper.EXPIRATION_TIME))
					.sign(Algorithm.HMAC512(JwtHelper.SECRET));
			System.out.println("======================================================1");

			// header 에 토큰 입력
			response.addHeader(JwtHelper.HEADER_STRING, JwtHelper.TOKEN_PREFIX +token);
			JsonUtil.write(response.getWriter(), responseService.getSuccessResult());
			System.out.println("======================================================2");

			// 로그인 로그 등록
			UserLoginLogSaveDTO saveDTO = new UserLoginLogSaveDTO();
			saveDTO.setUserSeq(authUserDTO.getUserSeq());
			saveDTO.setLoginIp(""); //TODO[ojh] IP 어떻게 받는지..
			loginLogService.save(saveDTO);
			System.out.println("======================================================3");
		}

	}
}
