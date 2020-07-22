package com.nike.dnp.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.user.UserRepository;
import com.nike.dnp.service.RedisService;
import com.nike.dnp.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * jwt 필터
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 21. 오후 4:22:06
 * @Description
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	/**
	 * The constant SECRET_KEY
	 *
	 * @author [오지훈]
	 */
	private static final String SECRET_KEY = JwtHelper.SECRET;

	/**
	 * The User repository
	 *
	 * @author [오지훈]
	 */
	private transient final UserRepository userRepository;

	/**
	 * The Redis service
	 *
	 * @author [오지훈]
	 */
	private final RedisService redisService;

	/**
	 * Instantiates a new Jwt authorization filter.
	 *
	 * @param authManager    the auth manager
	 * @param userRepository the user repository
	 * @param redisService   the redis service
	 * @author [오지훈]
	 * @CreatedOn 2020. 7. 21. 오후 4:22:06
	 * @Description
	 */
	public JwtAuthorizationFilter(
			final AuthenticationManager authManager
			, final UserRepository userRepository
			, final RedisService redisService) {
		super(authManager);
		this.userRepository = userRepository;
		this.redisService = redisService;
	}

	/**
	 * Do filter internal.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param chain    the chain
	 * @throws IOException      the io exception
	 * @throws ServletException the servlet exception
	 * @author [오지훈]
	 * @CreatedOn 2020. 7. 21. 오후 4:22:06
	 * @Description
	 */
	@Override
	protected void doFilterInternal(final HttpServletRequest request,
									final HttpServletResponse response,
									final FilterChain chain) throws IOException, ServletException {
		final String header = request.getHeader(JwtHelper.HEADER_STRING);
		if(header == null  || !header.startsWith(JwtHelper.TOKEN_PREFIX)){
			chain.doFilter(request,response);
			return;
		}

		final Authentication authentication = getUsernamePasswordAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request,response);
	}

	/**
	 * Gets username password authentication.
	 *
	 * @param request the request
	 * @return the username password authentication
	 * @author [오지훈]
	 * @CreatedOn 2020. 7. 21. 오후 4:22:06
	 * @Description
	 */
	private Authentication getUsernamePasswordAuthentication(final HttpServletRequest request) {
		final String token = request.getHeader(JwtHelper.HEADER_STRING);
		Authentication authentication = null;
		if(token != null){
			try{
				DecodedJWT verify = JWT.require(Algorithm.HMAC512(SECRET_KEY.getBytes())).build().verify(token.replace(JwtHelper.TOKEN_PREFIX, ""));
				// 토큰 디코드
				final String username = verify.getSubject();
				final String redisKey = verify.getClaim("rds").asString();
				String redisToken  = (String)redisService.get(redisKey);
				log.debug("System.getProperty(spring.profiles.active) {}", System.getProperty("spring.profiles.active"));
				if(String.valueOf(System.getProperty("spring.profiles.active")).equalsIgnoreCase("local")
					|| String.valueOf(System.getProperty("spring.profiles.active")).equalsIgnoreCase("dev")){
					authentication = getAuthentication(authentication, username, redisKey, redisToken);
				}else{
					if(verify.getToken().equals(redisToken)){
						// 유저정보 시큐리티에 넣음
						authentication = getAuthentication(authentication, username, redisKey, redisToken);
					}else{
						throw new CodeMessageHandleException(ErrorEnumCode.LoginError.NOT_SESSION.toString(), ErrorEnumCode.LoginError.NOT_SESSION.getMessage());
					}

				}
			}catch(JWTDecodeException | IllegalArgumentException e){
				throw new CodeMessageHandleException(ErrorEnumCode.LoginError.WRONG_TOKEN.toString(), ErrorEnumCode.LoginError.WRONG_TOKEN.getMessage());
			}
		}else{
			throw new CodeMessageHandleException(ErrorEnumCode.LoginError.WRONG_TOKEN.toString(), ErrorEnumCode.LoginError.WRONG_TOKEN.getMessage());
		}
		return authentication;
	}

	/**
	 * Gets authentication.
	 *
	 * @param authentication the authentication
	 * @param username       the username
	 * @param redisKey       the redis key
	 * @param redisToken     the redis token
	 * @return the authentication
	 * @author [오지훈]
	 * @CreatedOn 2020. 7. 21. 오후 4:22:06
	 * @Description
	 */
	private Authentication getAuthentication(Authentication authentication, String username, String redisKey, String redisToken) {
		if(username != null){
			final Optional<User> user = userRepository.findByUserId(username);
			final AuthUserDTO authUserDTO = new AuthUserDTO(user.get());
			// 레디스 키 시간 초기화
			redisService.set(redisKey, redisToken, Integer.parseInt(String.valueOf(BeanUtil.getBean("userSessionTime"))));
			authentication = new UsernamePasswordAuthenticationToken(authUserDTO, null, authUserDTO.getAuthorities());
		}
		return authentication;
	}
}
