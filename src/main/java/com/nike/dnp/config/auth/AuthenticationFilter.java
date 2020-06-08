package com.nike.dnp.config.auth;


import com.nike.dnp.exception.ErrorEnumCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

	/**
	 * The Password encryptor delegator.
	 */
	@Autowired
	PasswordEncryptorDelegator passwordEncryptorDelegator;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
												HttpServletResponse response) throws AuthenticationException, UsernameNotFoundException {

		log.debug("Processing login request");
		String username = String.valueOf(request.getParameter("username"));
		String password = String.valueOf(request.getParameter("password"));
		log.debug("passwordEncryptorDelegator.encrypt(password) > ", passwordEncryptorDelegator.encrypt(password));
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
			if("".equals(username)){
				throw new InsufficientAuthenticationException(ErrorEnumCode.loginError.LOGE02.getMessage());
			}
			if(StringUtils.isEmpty(password)){
				throw new InsufficientAuthenticationException(ErrorEnumCode.loginError.LOGE03.getMessage());
			}
		}
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		return this.getAuthenticationManager().authenticate(token);


	}
}
