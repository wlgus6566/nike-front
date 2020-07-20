package com.nike.dnp.config.jwt;

import lombok.experimental.UtilityClass;

/**
 * JWT 기본 프로퍼티 정보
 */
@UtilityClass
public class JwtHelper {

	/**
	 *
	 */
	public static final String SECRET = "NIKE_DNP";

	/**
	 *
	 */
	//public static final int EXPIRATION_TIME = 86400000; // 하루
	public static final Long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 365L;//365일

	/**
	 *
	 */
	public static final String TOKEN_PREFIX = "Bearer ";

	/**
	 *
	 */
	public static final String HEADER_STRING = "Authorization";


}
