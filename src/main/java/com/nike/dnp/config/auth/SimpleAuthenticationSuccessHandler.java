package com.nike.dnp.config.auth;

import com.nike.dnp.service.ResponseService;
import com.nike.dnp.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Simple authentication success handler.
 */
@Slf4j
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	/**
	 * The Response service.
	 */
	@Autowired
	/* default */ ResponseService responseService;

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request,
										final HttpServletResponse response,
										final Authentication authentication) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpStatus.OK.value());
		/*AuthUserDTO authUserDTO = (AuthUserDTO) authentication.getPrincipal();
		authUserDTO.toString();*/
		JsonUtil.write(response.getWriter(), responseService.getSuccessResult());
	}
}
