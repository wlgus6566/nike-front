package com.nike.dnp.config.jwt;

import lombok.experimental.UtilityClass;

/**
 * JWT 기본 프로퍼티 정보
 *
 * @author [오지훈]
 * @since 2020. 7. 28. 오후 4:38:52
 * @implNote
 */
@UtilityClass
public class JwtHelper {

	/**
	 * The Secret
	 *
	 * @author [오지훈]
	 */
	public final String SECRET = "NIKE_DNP";

	/**
	 * The constant EXPIRATION_TIME
	 *
	 * @author [오지훈]
	 */
//public static final int EXPIRATION_TIME = 86400000; // 하루
	public final Long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 365L;//365일

	/**
	 * The Token prefix
	 *
	 * @author [오지훈]
	 */
	public final String TOKEN_PREFIX = "Bearer ";

	/**
	 * The Header string
	 *
	 * @author [오지훈]
	 */
	public final String HEADER_STRING = "Authorization";


}
