package com.nike.dnp.config.auth;

import com.nike.dnp.dto.manage.auth.AuthUserDTO;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Simple authentication success handler.
 */
@Slf4j
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	ResponseService responseService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
										HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpStatus.OK.value());
		log.debug("authentication.getPrincipal() > " + authentication.getPrincipal());

		AuthUserDTO authUserDTO = (AuthUserDTO) authentication.getPrincipal();
		log.debug("authUserDTO.toString() > " + authUserDTO.toString());
		JsonUtil.write(response.getWriter(), responseService.getSuccessResult());

	}
}
