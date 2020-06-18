package com.nike.dnp.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.repository.user.UserRepository;
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
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	/**
	 *
	 */
	private static final String SECRET_KEY = JwtHelper.SECRET;

	/**
	 *
	 */
	private transient final UserRepository userRepository;

	/**
	 * Instantiates a new Jwt authorization filter.
	 *
	 * @param authManager    the auth manager
	 * @param userRepository the user repository
	 */
	public JwtAuthorizationFilter(
			final AuthenticationManager authManager
			, final UserRepository userRepository) {
		super(authManager);
		this.userRepository = userRepository;
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

		final Authentication authentication = getUsernamePasswrodAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request,response);
	}

	private Authentication getUsernamePasswrodAuthentication(final HttpServletRequest request) {
		final String token = request.getHeader(JwtHelper.HEADER_STRING);
		Authentication authentication = null;
		if(token != null){
			// 토큰 디코드
			final String username = JWT.require(Algorithm.HMAC512(SECRET_KEY.getBytes())).build().verify(token.replace(JwtHelper.TOKEN_PREFIX, "")).getSubject();
			// username(managerId)로 유저정보 조회
			// 유저정보 시큐리티에 넣음
			if(username != null){
				final Optional<User> user = userRepository.findByUserId(username);
				final AuthUserDTO authUserDTO = new AuthUserDTO(user.get());
				authentication = new UsernamePasswordAuthenticationToken(authUserDTO, null, authUserDTO.getAuthorities());
			}
		}
		return authentication;
	}
}
