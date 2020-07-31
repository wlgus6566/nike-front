package com.nike.dnp.dto.auth;

import lombok.*;

/**
 * The Class Security filter mata dto.
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 6:07:59
 * @implNote
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class SecurityFilterMataDTO {

	/**
	 * 아이디(시퀀스)
	 *
	 * @author [오지훈]
	 */
	private Long id;

	/**
	 * 경로
	 *
	 * @author [오지훈]
	 */
	private String antPattern;

	/**
	 * 롤
	 *
	 * @author [오지훈]
	 */
	private String expression;

	/**
	 * http request method
	 *
	 * @author [오지훈]
	 */
	private String httpMethod;

	/**
	 * 정렬
	 *
	 * @author [오지훈]
	 */
	private long sort;
}
