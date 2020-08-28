package com.nike.dnp.config.auth;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.util.JsonUtil;
import com.nike.dnp.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 접속 실패 핸들러
 */

@Slf4j
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
		log.info("SimpleAccessDeniedHandler.handle");
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpStatus.FORBIDDEN.value());

		JsonUtil.write(response.getWriter(), responseService.getFailResult(
				FailCode.ConfigureError.NO_AUTH.name()
				, MessageUtil.getMessage(FailCode.ConfigureError.NO_AUTH.name())));
	}
}
