package com.nike.dnp.config.auth;

import com.nike.dnp.exception.ErrorEnumCode;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleAccessDeniedHandler implements AccessDeniedHandler {

	@Autowired
	ResponseService responseService;

	@Override
	public void handle(final HttpServletRequest request,
					   final HttpServletResponse response,
					   final AccessDeniedException e) throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpStatus.FORBIDDEN.value());

		JsonUtil.write(response.getWriter(), responseService.getFailResult(ErrorEnumCode.commonError.COME01.toString(), ErrorEnumCode.commonError.COME01.getMessage()));
	}
}
