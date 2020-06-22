package com.nike.dnp.config.auth;

import com.nike.dnp.entity.auth.SecurityIpFilterMata;
import com.nike.dnp.entity.auth.SecurityUrlFilterMata;
import com.nike.dnp.service.auth.SecurityFilterMataService;
import com.nike.dnp.util.BeanUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * The type Auth access decision voter.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthAccessDecisionVoter implements AccessDecisionVoter<Object> {

	/**
	 *
	 */
	private final SecurityFilterMataService filterMataService;

	/**
	 *
	 */
	private String permitAll = "PERMITALL";

	@Override
	public boolean supports(final ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return true;
	}

	@Override
	public int vote(final Authentication authentication,
					final Object object,
					final Collection<ConfigAttribute> attributes) {

		final boolean authUrl = Boolean.valueOf(String.valueOf(BeanUtil.getBean("authUrl")));
		final boolean authIp = Boolean.valueOf(String.valueOf(BeanUtil.getBean("authIp")));
		int result = ACCESS_GRANTED;

		if(authIp){
			result = ipExpression(object);
		}

		if(result == ACCESS_GRANTED && authUrl){
			result = urlExpression(authentication,object);
		}

		return result;
	}

	/**
	 * ip 권한 확인
	 * @param object
	 * @return
	 */
	private int ipExpression(final Object object) {

		final List<SecurityIpFilterMata> ipFilterList = filterMataService.ipFindAll();
		final String remoteAddr = ((FilterInvocation) object).getRequest().getRemoteAddr();
		int result = ACCESS_DENIED;
		for(final SecurityIpFilterMata ipFilter : ipFilterList){
			final String ipFilterIp = ipFilter.getIp();
			if(ipFilterIp.equals(String.valueOf(remoteAddr))){
				result = ACCESS_GRANTED;
				break;
			}
		}
		return result;
	}

	/**
	 * url 권한 확인
	 * @param authentication
	 * @param object
	 * @return
	 */
	private int urlExpression(final Authentication authentication,final Object object) {

		// 입력 받은 url
		final String method = ((FilterInvocation) object).getRequest().getMethod();
		final String url = ((FilterInvocation) object).getRequestUrl();
		// DB url 조회
		final List<SecurityUrlFilterMata> securityMetaList = filterMataService.urlFindAll();

		//url 체크
		final AntPathMatcher antPathMatcher = new AntPathMatcher();
		int result = ACCESS_ABSTAIN;
		for(final SecurityUrlFilterMata urlFilterMata : securityMetaList){
			//url 매칭 되는것이 있는지 체크
			if(antPathMatcher.match(urlFilterMata.getAntPattern(),url)){
				// http 메소드 확인
				if(String.valueOf(urlFilterMata.getHttpMethod()).isEmpty() || urlFilterMata.getHttpMethod().equalsIgnoreCase(method)){
					final String[] roleArray = urlFilterMata.getExpression().split(",");
					for(final String role : roleArray){
						if(role.equalsIgnoreCase(permitAll)){
							result = ACCESS_GRANTED;
						}else{
							for(final GrantedAuthority authority : authentication.getAuthorities()){
								if(authority.getAuthority().equalsIgnoreCase(role)){
									result = ACCESS_GRANTED;
									break;
								}
							}
						}
					}
				}else{
					result = ACCESS_DENIED;
				}
				break;
			}
		}
		return result;
	}


}
