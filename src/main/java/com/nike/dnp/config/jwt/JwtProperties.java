package com.nike.dnp.config.jwt;

/**
 * The type Jwt properties.
 */
public class JwtProperties {

	/**
	 *
	 */
	public static final String SECRET = "NIKE_DNP";

	/**
	 *
	 */
	//public static final int EXPIRATION_TIME = 86400000; // 하루
	public static final Long EXPIRATION_TIME = 86400000* 365L;//365일
//	public static final int EXPIRATION_TIME = 3000; //3초

	/**
	 *
	 */
	public static final String TOKEN_PREFIX = "Bearer ";

	/**
	 *
	 */
	public static final String HEADER_STRING = "Authorization";


}
