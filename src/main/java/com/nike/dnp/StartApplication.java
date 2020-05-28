package com.nike.dnp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableCaching
//@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
@Slf4j
public class StartApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(StartApplication.class);
	}

}
