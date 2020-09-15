package com.nike.dnp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger Config
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 6:08:28
 * @implNote Swagger Config 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * @since 2020.05.21
 */
@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfig implements WebMvcConfigurer {

	/**
	 * Api docket.
	 *
	 * @return Swagger 설정값
	 * @author [오지훈]
	 * @since 2020. 6. 24. 오후 6:08:28
	 * @implNote
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/api/**"))
				.build()
				.apiInfo(apiInfo()).useDefaultResponseMessages(false);
	}

	/**
	 * Api info api info.
	 *
	 * @return the api info
	 * @author [오지훈]
	 * @since 2020. 6. 24. 오후 6:08:28
	 * @implNote Swagger Info 작성
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("이 문서는 NIKE D&P Project API 문서 입니다.")
				.description("Created by Emotion").license("Apache License Version 2.0")
				.licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE").version("1.0").build();
	}

}
