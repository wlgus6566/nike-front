package com.nike.dnp.config.auth;

import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.util.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleAuthenticationFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
										HttpServletResponse response,
										AuthenticationException e) throws IOException, ServletException {

		response.setStatus(HttpStatus.BAD_REQUEST.value());
		CommonResult commonResult = new CommonResult();


		if(e.getMessage().equals("userNotFound"))
		if (e instanceof UsernameNotFoundException){
			commonResult.setMsg("유저정보가 없습니다.");
		}else if(e instanceof BadCredentialsException){
			commonResult.setMsg("Invalid credentials");
		}else if(e instanceof InsufficientAuthenticationException){
			commonResult.setMsg("Invalid authentication request");
		}else{
			commonResult.setMsg("Authentication failure");
		}
		JsonUtil.write(response.getWriter(), commonResult);
	}
}
