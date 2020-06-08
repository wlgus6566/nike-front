package com.nike.dnp.config.auth;


import com.nike.dnp.exception.ErrorEnumCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Authentication filter.
 */
@Slf4j
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	/**
	 * Instantiates a new Authentication filter.
	 */
	public AuthenticationFilter() {
		super(new AntPathRequestMatcher("/api/authentications", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request,
												final HttpServletResponse response) throws AuthenticationException {


		final String username = String.valueOf(request.getParameter("username"));
		final String password = String.valueOf(request.getParameter("password"));
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
			if("".equals(username)){
				throw new InsufficientAuthenticationException(ErrorEnumCode.loginError.LOGE02.toString());
			}
			if(StringUtils.isEmpty(password)){
				throw new InsufficientAuthenticationException(ErrorEnumCode.loginError.LOGE03.toString());
			}
		}
		final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		return this.getAuthenticationManager().authenticate(token);


	}
}
