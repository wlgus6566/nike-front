package com.nike.dnp.dto.example.manager;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class SecurityFilterMataDTO {

	/**
	 * 아이디(시퀀스)
	 */
	private Long id;

	/**
	 * 경로
	 */
	private String antPattern;

	/**
	 * 롤
	 */
	private String expression;

	/**
	 * http request method
	 */
	private String httpMethod;

	/**
	 * 정렬
	 */
	private long sort;
}
