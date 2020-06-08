package com.nike.dnp.config.auth;

import com.nike.dnp.service.ResponseService;
import com.nike.dnp.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Simple logout success handler.
 */
public class SimpleLogoutSuccessHandler implements LogoutSuccessHandler {
	@Autowired
	ResponseService responseService;

	@Override
	public void onLogoutSuccess(final HttpServletRequest request,
								final HttpServletResponse response,
								final Authentication authentication) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpStatus.OK.value());
		JsonUtil.write(response.getWriter(), responseService.getSuccessResult());
	}
}
