package com.nike.dnp.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ROLE Enum
 * 
 * @since 2020.05.21
 * @author [오지훈]
 * @Description ROLE Enum 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */

@Getter
@RequiredArgsConstructor
public enum Role {

	ADMIN("ROLE_ADMIN", "관리자"),
	USER("ROLE_USER","일반 사용자");

	private final String key;
	private final String title;
	
}