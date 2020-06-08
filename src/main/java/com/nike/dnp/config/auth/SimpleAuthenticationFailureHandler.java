package com.nike.dnp.config.auth;

import com.nike.dnp.exception.ErrorEnumCode;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * The type Simple authentication failure handler.
 */
@Slf4j
public class SimpleAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	ResponseService responseService;

	@Override
	public void onAuthenticationFailure(final HttpServletRequest request,
										final HttpServletResponse response,
										final AuthenticationException e
	) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		String errorMessage = "";
		String errorCode = "";
		for(ErrorEnumCode.LoginError message : ErrorEnumCode.LoginError.values()){
			if(message.toString().equals(e.getMessage())){
				errorMessage = message.getMessage();
				errorCode = e.getMessage();
			}
		}
		if(e instanceof BadCredentialsException){
			// 비밀번호 틀림
			JsonUtil.write(response.getWriter(), responseService.getFailResult(ErrorEnumCode.LoginError.LOGE07.toString(), ErrorEnumCode.LoginError.LOGE07.getMessage()));
		}else if(e instanceof InsufficientAuthenticationException){
			// 아이디 비번 입력 안함
			JsonUtil.write(response.getWriter(), responseService.getFailResult(errorCode, errorMessage));
		}else if(e instanceof InternalAuthenticationServiceException){
			// 계정 정보 없음..
			JsonUtil.write(response.getWriter(), responseService.getFailResult(ErrorEnumCode.LoginError.LOGE01.toString(), ErrorEnumCode.LoginError.LOGE01.getMessage()));
		}else{
			JsonUtil.write(response.getWriter(), responseService.getFailResult("인증 실패 하였습니다."));
		}

	}
}
