package com.nike.dnp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
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
 * @since 2020.05.21
 * @author [오지훈]
 * @Description Swagger 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */

@Configuration
@EnableSwagger2
@Controller
@RequiredArgsConstructor
public class SwaggerConfig implements WebMvcConfigurer {

	/**
	 * @return Swagger 설정값
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any()) // 현재 RequestMapping으로
				// 할당된 모든 URL 리스트를 추출
				.paths(PathSelectors.ant("/api/**")) // 그중 /api/** 인 URL들만 필터링
				.build().apiInfo(apiInfo()).useDefaultResponseMessages(false);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("이 문서는 NIKE D&P Rest Api Project API 문서 입니다.")
				.description("Created by Emotion").license("Apache License Version 2.0")
				.licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE").version("1.0").build();
	}

}
