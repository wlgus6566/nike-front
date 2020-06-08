package com.nike.dnp.config;

import com.nike.dnp.config.auth.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

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

	/**
	 * ignore
	 */
	private static final String[] PUBLIC = new String[] {
		"/error", "/login", "/logout", "/h2-console", "/h2-console/**"
		,"/api/authentications"
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
	public void configure(WebSecurity web) {
		// 예외처리 목록 등록
		String[] staticResourcePatterns = {
				"/resources/**", "/static/**", "/favicon/**", "/favicon.ico", "/fileUpload/**", // Static 요소
				"/css/**", "/font/**", "/js/**", "/images/**", // Static 요소
				"/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/**" // Swagger 관련

		};
		web.ignoring().antMatchers(staticResourcePatterns);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(PUBLIC).permitAll()
				//.anyRequest().hasRole("ADMIN")
				.anyRequest().authenticated()
			.and()
				.addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.formLogin()
					.loginPage("/login")
					.and()
					.logout()
						.logoutUrl("/logout")
						.logoutSuccessHandler(logoutSuccessHandler())
			.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler())
			.and()
				.csrf().disable().headers().frameOptions().disable();
	}

	@Bean
	public AuthenticationFilter authenticationFilter() throws Exception{
		AuthenticationFilter authenticationFilter = new AuthenticationFilter();
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
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new SimpleLogoutSuccessHandler();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {return new SimpleAccessDeniedHandler();}

}
