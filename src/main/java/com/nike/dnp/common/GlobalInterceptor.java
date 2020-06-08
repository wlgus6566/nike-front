package com.nike.dnp.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description Interceptor 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 *
 */

@Slf4j
public class GlobalInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 컨트롤러 실행 직전에 동작
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("Interceptor > preHandler");
		return true;
	}

	/**
	 * 컨트롤러 진입 후 view가 랜더링 되기 전 동작
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav)
			throws Exception {
		log.info("Interceptor > postHandler");
	}

	/**
	 * 컨트롤러 진입 후 view가 랜더링 된 후 동작
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)
			throws Exception {
		log.info("Interceptor > afterCompletion");
	}

	/**
	 * 비동기 요청 시 PostHandle와 afterCompletion메서드를 수행하지 않고 이 메서드를 수행
	 */
	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("Interceptor > afterConcurrentHandlingStarted");
	}

}
