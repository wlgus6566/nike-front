package com.nike.dnp.config.auth;

import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 접속 실패 핸들러
 */
@RequiredArgsConstructor
public class SimpleAccessDeniedHandler implements AccessDeniedHandler {

	/**
	 * The Response service.
	 */
	private final ResponseService responseService;

	@Override
	public void handle(final HttpServletRequest request,
					   final HttpServletResponse response,
					   final AccessDeniedException exception) throws IOException {

		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpStatus.FORBIDDEN.value());

		JsonUtil.write(response.getWriter(), responseService.getFailResult(ErrorEnumCode.AuthError.NO_AUTH.toString(), ErrorEnumCode.AuthError.NO_AUTH.getMessage()));
	}
}
