package com.nike.dnp.config.auth;


import com.nike.dnp.common.viriable.ErrorEnumCode;
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
 * The type Authentication filter.
 */
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	/**
	 *
	 */
	private final AuthenticationManager authManager;

	/**
	 * Instantiates a new Authentication filter.
	 */

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request,
												final HttpServletResponse response) throws AuthenticationException {


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

		final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		return authManager.authenticate(token);

	}
}
