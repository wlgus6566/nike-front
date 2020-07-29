package com.nike.dnp.config.auth;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 인증 필터
 *
 * @author [오지훈]
 * @since 2020. 7. 7. 오후 2:11:02
 * @implNote
 */
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	/**
	 * The Auth manager
	 *
	 * @author [오지훈]
	 */
	private final AuthenticationManager authManager;

	/**
	 * 로그인 아이디 /비번 파라미터로 받아 인증 절차전 값 체크 및 인증 요청
	 *
	 * @param request  the request
	 * @param response the response
	 * @return the authentication
	 * @author [오지훈]
	 * @since 2020. 7. 7. 오후 2:11:02
	 * @implNote
	 */
	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request,
												final HttpServletResponse response) {
		UsernamePasswordAuthenticationToken token = null;
		try {
			final String username = obtainUsername(request);
			final String password = obtainPassword(request);
			token = new UsernamePasswordAuthenticationToken(username, password);
		} catch (AuthenticationException exception) {
			log.error("AuthenticationException", exception);
		}

		return authManager.authenticate(token);

	}
}
