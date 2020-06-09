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

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private ManagerRepository managerRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager,ManagerRepository managerRepository) {
		super(authenticationManager);
		this.managerRepository = managerRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain chain) throws IOException, ServletException {
		String header = request.getHeader(JwtProperties.HEADER_STRING);
		if(header == null  || !header.startsWith(JwtProperties.TOKEN_PREFIX)){
			chain.doFilter(request,response);
			return;
		}

		Authentication authentication = getUsernamePasswrodAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request,response);
	}

	private Authentication getUsernamePasswrodAuthentication(HttpServletRequest request) {

		String token = request.getHeader(JwtProperties.HEADER_STRING);
		if(token != null){
			// 토큰 디코드
			String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes())).build().verify(token.replace(JwtProperties.TOKEN_PREFIX, "")).getSubject();
			// username(managerId)로 유저정보 조회
			// 유저정보 시큐리티에 넣음
			if(username != null){
				Manager manager = managerRepository.findByManagerId(username);
				AuthUserDTO authUserDTO = new AuthUserDTO(manager);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(authUserDTO, null, authUserDTO.getAuthorities());
				return auth;
			}
			return null;
		}
		return null;
	}
}
