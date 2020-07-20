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
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	/**
	 *
	 */
	private static final String SECRET_KEY = JwtHelper.SECRET;

	/**
	 *
	 */
	private transient final UserRepository userRepository;

	private final RedisService redisService;

	/**
	 * Instantiates a new Jwt authorization filter.
	 *
	 * @param authManager    the auth manager
	 * @param userRepository the user repository
	 */
	public JwtAuthorizationFilter(
			final AuthenticationManager authManager
			, final UserRepository userRepository
			, final RedisService redisService) {
		super(authManager);
		this.userRepository = userRepository;
		this.redisService = redisService;
	}

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
				if(verify.getToken().equals(redisToken)){

					// 유저정보 시큐리티에 넣음
					if(username != null){
						final Optional<User> user = userRepository.findByUserId(username);
						final AuthUserDTO authUserDTO = new AuthUserDTO(user.get());
						// 레디스 키 시간 초기화
						redisService.set(redisKey, redisToken, Integer.parseInt(String.valueOf(BeanUtil.getBean("userSessionTime"))));
						authentication = new UsernamePasswordAuthenticationToken(authUserDTO, null, authUserDTO.getAuthorities());
					}
				}else{
					throw new CodeMessageHandleException(ErrorEnumCode.LoginError.NOT_SESSION.toString(), ErrorEnumCode.LoginError.NOT_SESSION.getMessage());
				}
			}catch(JWTDecodeException | IllegalArgumentException e){
				throw new CodeMessageHandleException(ErrorEnumCode.LoginError.WRONG_TOKEN.toString(), ErrorEnumCode.LoginError.WRONG_TOKEN.getMessage());
			}
		}else{
			throw new CodeMessageHandleException(ErrorEnumCode.LoginError.WRONG_TOKEN.toString(), ErrorEnumCode.LoginError.WRONG_TOKEN.getMessage());
		}
		return authentication;
	}
}
