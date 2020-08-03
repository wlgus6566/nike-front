package com.nike.dnp.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 6:06:55
 * @implNote Interceptor 작성
 */
@Slf4j
@RequiredArgsConstructor
public class GlobalInterceptor extends HandlerInterceptorAdapter {

	/**
	 * Pre handle boolean.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param handler  the handler
	 * @return the boolean
	 * @author [오지훈]
	 * @since 2020. 6. 24. 오후 6:06:55
	 * @implNote 컨트롤러 실행 직전에 동작
	 */
	@Override
	public boolean preHandle(
			final HttpServletRequest request
			, final HttpServletResponse response
			, final Object handler) {
		log.info("GlobalInterceptor.preHandle");
		return true;
	}

	/**
	 * Post handle.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param handler  the handler
	 * @param mav      the mav
	 * @author [오지훈]
	 * @since 2020. 6. 24. 오후 6:06:55
	 * @implNote 컨트롤러 진입 후 view가 랜더링 되기 전 동작
	 */
	@Override
	public void postHandle(
			final HttpServletRequest request
			, final HttpServletResponse response
			, final Object handler
			, final ModelAndView mav) {
		log.info("GlobalInterceptor.postHandle");
	}

	/**
	 * After completion.
	 *
	 * @param request   the request
	 * @param response  the response
	 * @param handler   the handler
	 * @param exception the exception
	 * @author [오지훈]
	 * @since 2020. 6. 24. 오후 6:06:55
	 * @implNote 컨트롤러 진입 후 view가 랜더링 된 후 동작
	 */
	@Override
	public void afterCompletion(
			final HttpServletRequest request
			, final HttpServletResponse response
			, final Object handler
			, final Exception exception) {
		log.info("GlobalInterceptor.afterCompletion");
	}

	/**
	 * After concurrent handling started.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param handler  the handler
	 * @author [오지훈]
	 * @since 2020. 6. 24. 오후 6:06:55
	 * @implNote 비동기 요청 시 PostHandle와 afterCompletion메서드를 수행하지 않고 이 메서드를 수행
	 */
	@Override
	public void afterConcurrentHandlingStarted(
			final HttpServletRequest request
			, final HttpServletResponse response
			, final Object handler) {
		log.info("GlobalInterceptor.afterConcurrentHandlingStarted");
	}

}
