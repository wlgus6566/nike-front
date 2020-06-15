package com.nike.dnp.config.auth;

import com.nike.dnp.dto.example.manager.SecurityFilterMataDTO;
import com.nike.dnp.entity.example.SecurityFilterMata;
import com.nike.dnp.service.example.SecurityFillterMataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Slf4j
public class AuthAccessDecisionVoter implements AccessDecisionVoter<Object> {

	private SecurityFillterMataService securityFillterMataService;

	public AuthAccessDecisionVoter(SecurityFillterMataService securityFillterMataService) {
		 this.securityFillterMataService = securityFillterMataService;
	}

	private String _PASSMEHOD = "ALL";
	private String _PERMITALL = "PERMITALL";


	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public int vote(Authentication authentication,
					Object object,
					Collection<ConfigAttribute> attributes) {

		List<SecurityFilterMata> securityMetaList = securityFillterMataService.findAll();


		String url = ((FilterInvocation) object).getRequestUrl();
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		boolean check = false;


		SecurityFilterMataDTO securityFilterMataDTO = new SecurityFilterMataDTO();
		for(SecurityFilterMata securityFilterMata : securityMetaList){
			if(antPathMatcher.match(securityFilterMata.getAntPattern(),url)){
				check = true;
				securityFilterMataDTO.setAntPattern(securityFilterMata.getAntPattern());
				if(String.valueOf(securityFilterMata.getHttpMethod()).isEmpty()){
					securityFilterMataDTO.setHttpMethod(_PASSMEHOD);
				}else{
					securityFilterMataDTO.setHttpMethod(securityFilterMata.getHttpMethod());
				}
				securityFilterMataDTO.setExpression(securityFilterMata.getExpression());
				break;
			}
		}
		int result = ACCESS_DENIED;
		if(check && securityFilterMataDTO != null){
			result = urlExpressionCheck(authentication,object,securityFilterMataDTO);
		}

		return result;
	}

	private int urlExpressionCheck(Authentication authentication,Object object,
									   SecurityFilterMataDTO securityFilterMataDTO) {

		String method = ((FilterInvocation) object).getRequest().getMethod();
		String url = ((FilterInvocation) object).getRequestUrl();
		//url 체크
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		int result = ACCESS_DENIED;
		if(antPathMatcher.match(securityFilterMataDTO.getAntPattern(),url) &&
				(securityFilterMataDTO.getHttpMethod().equals(_PASSMEHOD) || securityFilterMataDTO.getHttpMethod().toUpperCase().equals(method.toUpperCase()))){
			// 롤 체크
			String [] roleArray = securityFilterMataDTO.getExpression().split(",");
			for(String role : roleArray){
				if(role.toUpperCase().equals(_PERMITALL)){
					result = ACCESS_GRANTED;
				}else{
					for(GrantedAuthority authority : authentication.getAuthorities()){
						if(authority.getAuthority().toUpperCase().equals(role.toUpperCase())){
							result = ACCESS_GRANTED;
							break;
						}
					}
				}
			}
		}else{
			result = ACCESS_ABSTAIN;
		}
		return result;
	}
}
