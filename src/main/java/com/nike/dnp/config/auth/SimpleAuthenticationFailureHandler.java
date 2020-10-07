package com.nike.dnp.config.auth;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.util.JsonUtil;
import com.nike.dnp.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 로그인 실패  핸들러
 *
 * @author [오지훈]
 * @since 2020. 7. 7. 오후 2:10:50
 * @implNote
 */
@Slf4j
@RequiredArgsConstructor
public class SimpleAuthenticationFailureHandler implements AuthenticationFailureHandler {

	/**
	 * The Response service.
	 *
	 * @author [오지훈]
	 */
	private final ResponseService responseService;

	/**
	 * 로그인 실패 처리
	 *
	 * @param request   the request
	 * @param response  the response
	 * @param exception the exception
	 * @throws IOException the io exception
	 * @author [오지훈]
	 * @since 2020. 7. 7. 오후 2:10:50
	 * @implNote
	 */
	@Override
	public void onAuthenticationFailure(final HttpServletRequest request,
										final HttpServletResponse response,
										final AuthenticationException exception
	) throws IOException {
		log.info("SimpleAuthenticationFailureHandler.onAuthenticationFailure");
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpStatus.BAD_REQUEST.value());

		// 비밀번호 틀림
		if (exception instanceof BadCredentialsException) {
			if (ObjectUtils.isEmpty(request.getParameter("password"))) {
				JsonUtil.write(response.getWriter()
						, responseService.getFailResult(
								FailCode.ConfigureError.CHECK_ID_PASSWORD.name()
								, MessageUtil.getMessage(FailCode.ConfigureError.CHECK_ID_PASSWORD.name())
						));
			}
			JsonUtil.write(response.getWriter()
					, responseService.getFailResult(
							FailCode.ConfigureError.CHECK_ID_PASSWORD.name()
							, MessageUtil.getMessage(FailCode.ConfigureError.CHECK_ID_PASSWORD.name())
					));

		// 계정 정보 없음
		} else if (exception instanceof InternalAuthenticationServiceException) {
			JsonUtil.write(response.getWriter()
					, responseService.getFailResult(
							FailCode.ConfigureError.CHECK_ID_PASSWORD.name()
							, MessageUtil.getMessage(FailCode.ConfigureError.CHECK_ID_PASSWORD.name())
					));

		// 기타
		} else {
			JsonUtil.write(response.getWriter()
					, responseService.getFailResult(
							FailCode.ExceptionError.ERROR.name()
							, MessageUtil.getMessage(FailCode.ExceptionError.ERROR.name())
					));
		}

	}
}
