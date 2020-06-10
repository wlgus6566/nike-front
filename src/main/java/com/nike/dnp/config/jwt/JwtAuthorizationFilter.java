package com.nike.dnp.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nike.dnp.dto.manage.auth.AuthUserDTO;
import com.nike.dnp.entity.manage.Manager;
import com.nike.dnp.repository.manage.ManagerRepository;
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

/**
 * The type Jwt authorization filter.
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private final String SECRET = JwtHelper.SECRET;

	/**
	 *
	 */
	private transient final ManagerRepository managerRepository;

	/**
	 * Instantiates a new Jwt authorization filter.
	 *
	 * @param authManager       the auth manager
	 * @param managerRepository the manager repository
	 */
	public JwtAuthorizationFilter(
			final AuthenticationManager authManager
			, final ManagerRepository managerRepository) {
		super(authManager);
		this.managerRepository = managerRepository;
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
		if(token != null){
			// 토큰 디코드
			final String username = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build().verify(token.replace(JwtHelper.TOKEN_PREFIX, "")).getSubject();
			// username(managerId)로 유저정보 조회
			// 유저정보 시큐리티에 넣음
			if(username != null){
				final Manager manager = managerRepository.findByManagerId(username);
				final AuthUserDTO authUserDTO = new AuthUserDTO(manager);
				return new UsernamePasswordAuthenticationToken(authUserDTO, null, authUserDTO.getAuthorities());
			}
			return null;
		}
		return null;
	}
}
