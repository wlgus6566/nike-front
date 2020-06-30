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
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:04:47
 * @Description StartApplication 작성
 */
@Slf4j
@EnableCaching
@EnableJpaAuditing(auditorAwareRef = "userAuditAware")
@SpringBootApplication
@RequiredArgsConstructor
public class StartApplication extends SpringBootServletInitializer {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:04:47
	 * @Description
	 */
	public static void main(final String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}

	/**
	 * Configure spring application builder.
	 *
	 * @param builder the builder
	 * @return the spring application builder
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:04:47
	 * @Description
	 */
	@Override
	protected final SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
		return builder.sources(StartApplication.class);
	}

}
