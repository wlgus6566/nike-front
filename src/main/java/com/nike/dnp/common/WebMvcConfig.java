package com.nike.dnp.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The Class Web mvc config.
 *
 * @author [윤태호]
 * @since 2020. 8. 18. 오후 4:43:44
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


	/**
	 * Add cors mappings.
	 *
	 * @param registry the registry
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 8. 18. 오후 4:43:44
	 */
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
//	}
}
