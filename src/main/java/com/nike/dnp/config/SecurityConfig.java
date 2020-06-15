package com.nike.dnp.config;

import com.nike.dnp.config.auth.*;
import com.nike.dnp.config.jwt.JwtAuthorizationFilter;
import com.nike.dnp.repository.example.ManagerRepository;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.example.SecurityFillterMataService;
import com.nike.dnp.service.log.UserLoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Arrays;
import java.util.List;

/**
 * Security Config
 * 
 * @since 2020.05.21
 * @author [오지훈]
 * @Description Security Config 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 *
	 */
	private final ManagerRepository managerRepository;

	/**
	 *
	 */
	private final ResponseService responseService;

	/**
	 *
	 */
	private final UserLoginLogService loginLogService;

	private final SecurityFillterMataService securityFillterMataService;

	/**
	 * ignore
	 */
	private static final String[] PUBLIC = {
		"/error", "/login", "/logout", "/h2-console", "/h2-console/**"
		/*,"/api/**"*/
	};


	/**
	 * 암호화 모듈
	 *
	 * @return the password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		// 예외처리 목록 등록
		final String[] staticPatterns = {
				"/resources/**", "/static/**", "/favicon/**", "/favicon.ico", "/fileUpload/**", // Static 요소
				"/css/**", "/font/**", "/js/**", "/images/**", // Static 요소
				"/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/**" // Swagger 관련

		};
		web.ignoring().antMatchers(staticPatterns);

	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		http.authorizeRequests()
						.antMatchers(HttpMethod.POST,"/login").permitAll()
						.anyRequest().authenticated().accessDecisionManager(accessDecisionManager());

		http.addFilter(authenticationFilter()) // 인증 필터
			.addFilter(new JwtAuthorizationFilter(authenticationManager(), this.managerRepository)) //jwt 토큰 인증 필터
			.exceptionHandling().accessDeniedHandler(accessDeniedHandler()) // 권한 체크 핸들러
			.and()
			.csrf().disable() // csrf 사용 안함
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 사용안함

	}

	@Bean
	public AffirmativeBased accessDecisionManager(){
		List<AccessDecisionVoter<?>> decisionVoters = Arrays.asList(new RoleVoter(), new AuthAccessDecisionVoter(securityFillterMataService));
		return new AffirmativeBased(decisionVoters);
	}

	/**
	 * 인증 필터
	 *
	 * @return the authentication filter
	 * @throws Exception the exception
	 */
	@Bean
	public AuthenticationFilter authenticationFilter() {
		AuthenticationFilter filter = null;
		try {
			filter = new AuthenticationFilter(authenticationManager());
			filter.setAuthenticationSuccessHandler(authenticationSuccessHandler()); // 인증 성공 핸들러
			filter.setAuthenticationFailureHandler(authenticationFailureHandler()); // 인증 실패 핸들러
			filter.setAuthenticationManager(authenticationManagerBean());
		} catch (Exception exception) {

		}
		return filter;
	}

	/**
	 * 승인 성공 후 핸들러
	 *
	 * @return the authentication success handler
	 */
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new SimpleAuthenticationSuccessHandler(responseService, loginLogService);
	}

	/**
	 * 승인 실패 후 핸들러
	 *
	 * @return the authentication failure handler
	 */
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new SimpleAuthenticationFailureHandler(responseService);
	}

	/**
	 * 권한 실패 핸들러
	 *
	 * @return the AccessDenied handler
	 */
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new SimpleAccessDeniedHandler(responseService);
	}

}
