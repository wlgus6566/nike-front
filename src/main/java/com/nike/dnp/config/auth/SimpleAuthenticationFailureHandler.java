package com.nike.dnp.config.auth;

import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.common.variable.ErrorEnumCode.LoginError;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 로그인 실패  핸들러
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 7. 오후 2:10:50
 * @Description
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
	 * On authentication failure.
	 *
	 * @param request   the request
	 * @param response  the response
	 * @param exception the exception
	 * @throws IOException the io exception
	 * @author [오지훈]
	 * @CreatedOn 2020. 7. 7. 오후 2:10:50
	 * @Description
	 */
	@Override
	public void onAuthenticationFailure(final HttpServletRequest request,
										final HttpServletResponse response,
										final AuthenticationException exception
	) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		String errorMessage = "";
		String errorCode = "";
		for(final LoginError message : LoginError.values()){
			if(message.toString().equals(exception.getMessage())){
				errorMessage = message.getMessage();
				errorCode = exception.getMessage();
			}
		}

		// 아이디 비번 입력 안함
		if (exception instanceof InsufficientAuthenticationException) {
			JsonUtil.write(response.getWriter()
					, responseService.getFailResult(
							errorCode
							, errorMessage
					));
		// 비밀번호 틀림
		} else if (exception instanceof BadCredentialsException) {
			JsonUtil.write(response.getWriter()
					, responseService.getFailResult(
							LoginError.WRONG_PASSWORD.toString()
							, LoginError.WRONG_PASSWORD.getMessage()
					));
		// 계정 정보 없음
		} else if (exception instanceof InternalAuthenticationServiceException) {
			JsonUtil.write(response.getWriter()
					, responseService.getFailResult(
							LoginError.NOT_JOIN.toString()
							, LoginError.NOT_JOIN.getMessage()
					));
		// 기타
		} else {
			JsonUtil.write(response.getWriter()
					, responseService.getFailResult(
							ErrorEnumCode.ExceptionError.ERROR.toString()
							, ErrorEnumCode.ExceptionError.ERROR.getMessage()
					));
		}

	}
}
