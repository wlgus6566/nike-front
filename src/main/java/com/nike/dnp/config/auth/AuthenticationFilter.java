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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public AuthenticationFilter() {
		super(new AntPathRequestMatcher("/api/authentications", "POST"));
	}

	@Autowired
	PasswordEncryptorDelegator passwordEncryptorDelegator;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
												HttpServletResponse response) throws AuthenticationException, UsernameNotFoundException {

		log.debug("Processing login request");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		log.debug("passwordEncryptorDelegator.encrypt(password) > " + passwordEncryptorDelegator.encrypt(password));
		if("".equals(username) || "".equals(password)){
			if("".equals(username)){
				throw new InsufficientAuthenticationException(ErrorEnumCode.loginError.LOGE02.getMessage());
			}
			if("".equals(password)){
				throw new InsufficientAuthenticationException(ErrorEnumCode.loginError.LOGE03.getMessage());
			}
		}
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		return this.getAuthenticationManager().authenticate(token);
	}
}
