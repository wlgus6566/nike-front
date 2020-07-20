package com.nike.dnp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

/**
 * Swagger Config
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:08:28
 * @Description Swagger Config 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * @since 2020.05.21
 */
@Configuration
@EnableSwagger2
@Controller
@RequiredArgsConstructor
public class SwaggerConfig implements WebMvcConfigurer {

	/**
	 * Api docket.
	 *
	 * @return Swagger 설정값
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:08:28
	 * @Description
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/api/**"))
				.build()
				.globalOperationParameters(Arrays.asList(
						new ParameterBuilder().name("Authorization").defaultValue("Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9" +
																						  ".eyJyZHMiOiJhdXRoczp5dGg1OTE0MzE0OCIsInN1YiI6Inl0aCIsImV4cCI6MTYyNjc2NTk0MywiaWF0IjoxNTk1MjI5OTQzfQ.wg_VqONs3LYXKKHclCwYSHn0jyEv6jM13TMUqKp0vLo_Mhxp0Gwj-PWWFxNhGzsXQKhryIpGV85hXHX7t-DjVA")
								.modelRef(new ModelRef("string")).parameterType("header").required(true).modelRef(new ModelRef("String")).build()))
				.apiInfo(apiInfo()).useDefaultResponseMessages(false);
	}

	/**
	 * Api info api info.
	 *
	 * @return the api info
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:08:28
	 * @Description Swagger Info 작성
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("이 문서는 NIKE D&P Rest Api Project API 문서 입니다.")
				.description("Created by Emotion").license("Apache License Version 2.0")
				.licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE").version("1.0").build();
	}


	// 토큰 사용
	/*@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()) // 현재 RequestMapping으로
				// 할당된 모든 URL 리스트를 추출
				.paths(PathSelectors.ant("/api/**")) // 그중 /api/** 인 URL들만 필터링
				.build().apiInfo(apiInfo()).useDefaultResponseMessages(false).securitySchemes(Lists.newArrayList(apiKey())).securityContexts(Arrays.asList(securityContext()));
	}

	@Bean
	SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference("Authorization", authorizationScopes));
	}


	private ApiKey apiKey() {
		return new ApiKey("Authorization", "Authorization", "header");
	}*/


}
