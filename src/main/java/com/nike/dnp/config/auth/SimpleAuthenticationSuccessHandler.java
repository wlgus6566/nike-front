package com.nike.dnp.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nike.dnp.config.jwt.JwtHelper;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.log.UserLoginLogService;
import com.nike.dnp.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 로그인 성공 후 핸들러
 */
@Slf4j
@RequiredArgsConstructor
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	/**
	 * The Response service.
	 */
	private final ResponseService responseService;
	/**
	 *
	 */
	private final UserLoginLogService loginLogService;

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request,
										final HttpServletResponse response,
										final Authentication authentication) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpStatus.OK.value());
		final AuthUserDTO authUserDTO = (AuthUserDTO) authentication.getPrincipal();

		// jwt 토큰 생성
		final String token = JWT.create().withSubject(authUserDTO.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + JwtHelper.EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(JwtHelper.SECRET));

		// header 에 토큰 입력
		response.addHeader(JwtHelper.HEADER_STRING, JwtHelper.TOKEN_PREFIX +token);
		JsonUtil.write(response.getWriter(), responseService.getSuccessResult());

		//TODO[ojh] 로그인 로그 구현
		//loginLogService.save();
	}
}