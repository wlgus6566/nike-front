package com.nike.dnp.config.auth;

import com.nike.dnp.common.variable.ErrorEnumCode;
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
 */
@Slf4j
@RequiredArgsConstructor
public class SimpleAuthenticationFailureHandler implements AuthenticationFailureHandler {

	/**
	 * The Response service.
	 */
	private final ResponseService responseService;

	@Override
	public void onAuthenticationFailure(final HttpServletRequest request,
										final HttpServletResponse response,
										final AuthenticationException exception
	) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		String errorMessage = "";
		String errorCode = "";
		for(final ErrorEnumCode.LoginError message : ErrorEnumCode.LoginError.values()){
			if(message.toString().equals(exception.getMessage())){
				errorMessage = message.getMessage();
				errorCode = exception.getMessage();
			}
		}
		// 비밀번호 틀림
		if(exception instanceof BadCredentialsException){
			JsonUtil.write(response.getWriter(), responseService.getFailResult(ErrorEnumCode.LoginError.LOGE07.toString(), ErrorEnumCode.LoginError.LOGE07.getMessage()));

		// 아이디 비번 입력 안함
		}else if(exception instanceof InsufficientAuthenticationException){
			JsonUtil.write(response.getWriter(), responseService.getFailResult(errorCode, errorMessage));

		// 계정 정보 없음
		}else if(exception instanceof InternalAuthenticationServiceException){
			JsonUtil.write(response.getWriter(), responseService.getFailResult(ErrorEnumCode.LoginError.LOGE01.toString(), ErrorEnumCode.LoginError.LOGE01.getMessage()));

		// 기타
		}else{
			JsonUtil.write(response.getWriter(), responseService.getFailResult("인증 실패 하였습니다."));
		}

	}
}
