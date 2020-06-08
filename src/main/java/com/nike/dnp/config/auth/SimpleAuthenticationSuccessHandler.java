package com.nike.dnp.config.auth;

import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.util.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
										HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
		response.setStatus(HttpStatus.OK.value());
		CommonResult commonResult = new CommonResult();
		commonResult.setMsg("authenticated");
		JsonUtil.write(response.getWriter(),commonResult);
	}
}
