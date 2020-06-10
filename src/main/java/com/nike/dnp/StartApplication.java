package com.nike.dnp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * StartApplication
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description StartApplication 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 *
 */

@Slf4j
@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class StartApplication extends SpringBootServletInitializer {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(final String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}

	@Override
	protected final SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
		return builder.sources(StartApplication.class);
	}

}
