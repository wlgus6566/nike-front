package com.nike.dnp.config;

import com.nike.dnp.config.auth.*;
import com.nike.dnp.config.jwt.JwtAuthorizationFilter;
import com.nike.dnp.repository.user.UserRepository;
import com.nike.dnp.service.RedisService;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.auth.AuthService;
import com.nike.dnp.service.auth.SecurityFilterMataService;
import com.nike.dnp.service.log.UserLoginLogService;
import com.nike.dnp.service.user.UserMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
 * @author [오지훈]
 * @Description Security Config 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * @since 2020.05.21
 */
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * UserRepository
	 * @author [오지훈]
	 */
	private final UserRepository userRepository;

	/**
	 * ResponseService
	 * @author [오지훈]
	 */
	private final ResponseService responseService;

	/**
	 * UserLoginLogService
	 * @author [윤태호]
	 */
	private final UserLoginLogService loginLogService;

	/**
	 * SecurityFilterMataService
	 * 필터 정보 서비스
	 * @author [윤태호]
	 */
	private final SecurityFilterMataService filterMataService;

	/**
	 * The Redis service
	 *
	 * @author [오지훈]
	 */
	private final RedisService redisService;

	/**
	 * The User mail service
	 *
	 * @author [오지훈]
	 */
	private final UserMailService userMailService;

	/**
	 * The Auth service
	 *
	 * @author [윤태호]
	 */
	private final AuthService authService;

	/**
	 * Auth url string.
	 *
	 * @param authUrl the auth url
	 * @return the string
	 * @author [윤태호]
	 */
	@Bean(name = "authUrl")
	@Value("${security.auth.url:}")
	public String authUrl(final String authUrl) {
		return authUrl;
	}

	/**
	 * Auth ip String.
	 *
	 * @param authIp the auth ip
	 * @return the string
	 * @author [윤태호]
	 */
	@Bean(name = "authIp")
	@Value("${security.auth.ip:}")
	public String authIP(final String authIp) {
		return authIp;
	}


	/**
	 * User session time int.
	 *
	 * @param userSessionTime the user session time
	 * @return the int
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 20. 오후 4:07:10
	 * @Description
	 */
	@Bean(name = "userSessionTime")
	@Value("${spring.redis.userSessionTime:}")
	public int userSessionTime(final int userSessionTime) {
		return userSessionTime;
	}


	/**
	 * 암호화 모듈
	 *
	 * @return the password encoder
	 * @author [윤태호]
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		/*SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(100100);
		return new BCryptPasswordEncoder(10,secureRandom);*/
		return new BCryptPasswordEncoder();
	}

	/**
	 * 예외처리 목록 등록
	 * @param web
	 * @throws Exception
	 * @author [윤태호]
	 */
	@Override
	public void configure(final WebSecurity web) {
		final String[] staticPatterns = {
				"/resources/**", "/static/**", "/favicon/**", "/favicon.ico", "/fileUpload/**", // Static 요소
				"/css/**", "/font/**", "/js/**", "/images/**", // Static 요소
				"/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/**", // Swagger 관련
				"/api/download" // 임시
				,"/api/open/**"
		};
		web.ignoring().antMatchers(staticPatterns);
	}

	/**
	 * configure
	 * @param http
	 * @throws Exception
	 * @author [윤태호]
	 */
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests()
						.accessDecisionManager(accessDecisionManager())
						.antMatchers(HttpMethod.POST,"/api/login").permitAll()
						.antMatchers("/api/mypage/**", "/api/main/**").authenticated()
						.antMatchers("/api/mypage/**", "/api/main/**").authenticated()
						.anyRequest().authenticated();

		http.addFilter(authenticationFilter()) // 인증 필터
			.addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userRepository,this.redisService)) //jwt 토큰 인증 필터
			.exceptionHandling().accessDeniedHandler(accessDeniedHandler()) // 권한 체크 핸들러
			.and()
			.csrf().disable() // csrf 사용 안함
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 사용안함
	}

	/**
	 * Access Manager
	 *
	 * @return the affirmative based
	 * @author [윤태호]
	 */
	@Bean
	public AffirmativeBased accessDecisionManager(){
		final List<AccessDecisionVoter<?>> decisionVoters = Arrays.asList(new RoleVoter(), new AuthAccessDecisionVoter(filterMataService, authService));
		return new AffirmativeBased(decisionVoters);
	}

	/*
	// filter Meta Service 미사용
	@Bean
	public AccessDecisionManager accessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> decisionVoters = Arrays.asList(new AuthenticatedVoter(), new RoleVoter(), new WebExpressionVoter());
		return new UnanimousBased(decisionVoters);

	}

	*/

	/**
	 * 인증 필터
	 *
	 * @return the authentication filter
	 * @throws Exception the exception
	 * @author [윤태호]
	 */
	@Bean
	public AuthenticationFilter authenticationFilter() {

		AuthenticationFilter filter = null;
		try {
			filter = new AuthenticationFilter(authenticationManager());
			filter.setFilterProcessesUrl("/api/login");
			filter.setAuthenticationSuccessHandler(authenticationSuccessHandler()); // 인증 성공 핸들러
			filter.setAuthenticationFailureHandler(authenticationFailureHandler()); // 인증 실패 핸들러
			filter.setAuthenticationManager(authenticationManagerBean());
		} catch (Exception exception) {
			log.error("exception", exception);
		}
		return filter;
	}

	/**
	 * 승인 성공 후 핸들러
	 *
	 * @return the authentication success handler
	 * @author [윤태호]
	 */
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new SimpleAuthenticationSuccessHandler(
				responseService
				, loginLogService
				, redisService
				, userRepository
				, userMailService
		);
	}

	/**
	 * 승인 실패 후 핸들러
	 *
	 * @return the authentication failure handler
	 * @author [윤태호]
	 */
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new SimpleAuthenticationFailureHandler(responseService);
	}

	/**
	 * 권한 실패 핸들러
	 *
	 * @return the AccessDenied handler
	 * @author [윤태호]
	 */
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new SimpleAccessDeniedHandler(responseService);
	}


}
