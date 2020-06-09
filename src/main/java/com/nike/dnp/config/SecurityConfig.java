package com.nike.dnp.config;

import com.nike.dnp.config.auth.AuthenticationFilter;
import com.nike.dnp.config.auth.SimpleAccessDeniedHandler;
import com.nike.dnp.config.auth.SimpleAuthenticationFailureHandler;
import com.nike.dnp.config.auth.SimpleAuthenticationSuccessHandler;
import com.nike.dnp.config.jwt.JwtAuthorizationFilter;
import com.nike.dnp.repository.manage.ManagerRepository;
import com.nike.dnp.service.ResponseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private final ManagerRepository managerRepository;

	@Autowired
	private final ResponseService responseService;

	/**
	 * ignore
	 */
	private static final String[] PUBLIC = {
		"/error", "/login", "/logout", "/api/**", "/h2-console", "/h2-console/**"
		,"/api/**"
	};

	/**
	 * Password encoder password encoder.
	 *
	 * @return the password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(final WebSecurity web) {
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
				.antMatchers(PUBLIC).permitAll()
//				.antMatchers(HttpMethod.POST,"/login").permitAll()
//				.antMatchers(HttpMethod.GET,"/api/manage/user").hasRole("ADMIN")
//				.antMatchers(HttpMethod.GET,"/api/manage/user/**").hasRole("MANAGER")
				.anyRequest().authenticated()
			.and()
				.addFilter(authenticationFilter())
				.addFilter(new JwtAuthorizationFilter(authenticationManager(), this.managerRepository))
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler()) // 권한 체크 핸들러
			.and()
				.csrf().disable() // csrf 사용 안함
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 사용안함
	}

	@Bean
	public AuthenticationFilter authenticationFilter() throws Exception{
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager());
		authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
		authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
		authenticationFilter.setAuthenticationManager(authenticationManagerBean());
		return authenticationFilter;
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new SimpleAuthenticationSuccessHandler();
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new SimpleAuthenticationFailureHandler();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {return new SimpleAccessDeniedHandler(responseService);}

}
