package com.nike.dnp.config.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component()
public class AuthFilterInvoCationServiceSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		log.debug("getAttributes >> ", "getAttributes");
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		log.debug("getAllConfigAttributes >> ", "getAllConfigAttributes");
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		log.debug("getAllConfigAttributes >> ", "getAllConfigAttributes");
		return FilterInvocation.class.isAssignableFrom(clazz);
	}


}
