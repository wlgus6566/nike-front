package com.nike.dnp.config.auth;


import com.nike.dnp.common.variable.ErrorEnumCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 인증 필터
 */
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	/**
	 *
	 */
	private final AuthenticationManager authManager;

	/**
	 * 로그인 아이디 /비번 파라미터로 받아 인증 절차전 값 체크 및 인증 요청
	 */

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request,
												final HttpServletResponse response) {

		UsernamePasswordAuthenticationToken token = null;

		try {
			final String username = obtainUsername(request);
			final String password = obtainPassword(request);
			if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
				if("".equals(username)){
					throw new InsufficientAuthenticationException(ErrorEnumCode.LoginError.LOGE02.toString());
				}
				if(StringUtils.isEmpty(password)){
					throw new InsufficientAuthenticationException(ErrorEnumCode.LoginError.LOGE03.toString());
				}
			}

			token = new UsernamePasswordAuthenticationToken(username, password);
		} catch (AuthenticationException exception) {
			log.error("AuthenticationException", exception);
		}

		return authManager.authenticate(token);

	}
}
