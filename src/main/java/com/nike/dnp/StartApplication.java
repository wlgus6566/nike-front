package com.nike.dnp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * StartApplication
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 6:04:47
 * @implNote StartApplication 작성
 */
@Slf4j
@EnableCaching
@EnableJpaAuditing(auditorAwareRef = "userAuditAware")
@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
public class StartApplication extends SpringBootServletInitializer {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 * @author [오지훈]
	 * @since 2020. 6. 24. 오후 6:04:47
	 * @implNote
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
	 * @since 2020. 6. 24. 오후 6:04:47
	 * @implNote
	 */
	@Override
	protected final SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
		return builder.sources(StartApplication.class);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				/*registry.addMapping("/api/open/upload").allowedOrigins("https://www.nikespace.co.kr", "http://localhost:8081", "https://devwww.nikespace.co.kr")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.maxAge(3600);*/
			}
		};
	}

}
