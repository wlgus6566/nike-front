package com.nike.dnp.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The Class Device interceptor.
 * 디바이스 정보 인터셉터
 *
 * @author [이소정]
 * @since 2020. 11. 24. 오후 4:42:19
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class DeviceInterceptor implements WebMvcConfigurer {

	/**
	 * Device resolver handler interceptor device resolver handler interceptor.
	 *
	 * @return the device resolver handler interceptor
	 * @author [이소정]
	 * @implNote [디바이스 핸들러]
	 * @since 2020. 11. 24. 오후 4:42:19
	 */
	@Bean
	public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
		return new DeviceResolverHandlerInterceptor();
	}

	/**
	 * Add interceptors.
	 *
	 * @param registry the registry
	 * @author [이소정]
	 * @implNote [인터셉터에 디바이스 정보 추가]
	 * @since 2020. 11. 24. 오후 4:42:19
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(deviceResolverHandlerInterceptor());
	}
}
