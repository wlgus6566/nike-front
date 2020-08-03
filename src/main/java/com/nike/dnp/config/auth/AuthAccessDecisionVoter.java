package com.nike.dnp.config.auth;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.menu.MenuRoleResourceReturnDTO;
import com.nike.dnp.entity.auth.SecurityIpFilterMata;
import com.nike.dnp.service.auth.AuthService;
import com.nike.dnp.service.auth.SecurityFilterMataService;
import com.nike.dnp.util.BeanUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 시큐리티 권한 체크
 *
 * @author [윤태호]
 * @since 2020. 7. 14. 오후 5:54:43
 * @implNote
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthAccessDecisionVoter implements AccessDecisionVoter<Object> {

	/**
	 * The Filter mata service
	 *
	 * @author [윤태호]
	 */
	private final SecurityFilterMataService filterMataService;

	/**
	 * The Auth service
	 *
	 * @author [윤태호]
	 */
	private final AuthService authService;
	/**
	 * The Permit all
	 *
	 * @author [윤태호]
	 */
	private String permitAll = "PERMITALL";

	/**
	 * Supports boolean.
	 *
	 * @param attribute the attribute
	 * @return the boolean
	 * @author [윤태호]
	 * @since 2020. 7. 14. 오후 5:54:43
	 * @implNote
	 */
	@Override
	public boolean supports(final ConfigAttribute attribute) {
		return true;
	}

	/**
	 * Supports boolean.
	 *
	 * @param clazz the clazz
	 * @return the boolean
	 * @author [윤태호]
	 * @since 2020. 7. 14. 오후 5:54:43
	 * @implNote
	 */
	@Override
	public boolean supports(final Class<?> clazz) {
		return true;
	}

	/**
	 * Vote int.
	 *
	 * @param authentication the authentication
	 * @param object         the object
	 * @param attributes     the attributes
	 * @return the int
	 * @author [윤태호]
	 * @since 2020. 7. 14. 오후 5:54:44
	 * @implNote
	 */
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
	 *
	 * @param object the object
	 * @return int
	 * @author [윤태호]
	 * @since 2020. 7. 14. 오후 5:54:44
	 * @implNote
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
	 *
	 * @param authentication the authentication
	 * @param object         the object
	 * @return int
	 * @author [윤태호]
	 * @since 2020. 7. 14. 오후 5:54:44
	 * @implNote
	 */
	private int urlExpression(final Authentication authentication,final Object object) {

		// 입력 받은 url
		final String method = ((FilterInvocation) object).getRequest().getMethod();
		final String url = ((FilterInvocation) object).getRequestUrl();

		final AuthUserDTO authUserDTO = (AuthUserDTO) authentication.getPrincipal();
		final List<MenuRoleResourceReturnDTO> authsResourcesByRoleType = authService.getAuthsResourcesByRoleType(authUserDTO.getRole());

		//url 체크
		final AntPathMatcher antPathMatcher = new AntPathMatcher();

		int result = ACCESS_DENIED;
		for(final MenuRoleResourceReturnDTO menuRoleResourceReturnDTO : authsResourcesByRoleType){
			StringBuilder resourceUrl = new StringBuilder(menuRoleResourceReturnDTO.getResourceUrl());
			final String resourceMethod = menuRoleResourceReturnDTO.getResourceMethod();
			if(resourceUrl.toString().contains("{")){
				resourceUrl= new StringBuilder(resourceUrl.toString().substring(0, resourceUrl.toString().indexOf("{")));
				resourceUrl.append('*');
			}
			if(method.equalsIgnoreCase(HttpMethod.GET.name())){
				resourceUrl.append('*');
			}
			//url 매칭 되는것이 있는지 체크
			if(antPathMatcher.match(resourceUrl.toString(),url) &&
					(String.valueOf(resourceMethod).isEmpty() || resourceMethod.contains(method))){
				result = ACCESS_GRANTED;
				break;

				/* role 이 여러개일 경우 */
				/*final String[] roleArray = urlFilterMata.getExpression().split(",");
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
				}*/
			}
		}
		return result;
	}
}
