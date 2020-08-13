package com.nike.dnp.dto.product;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiParam;
import lombok.*;


/**
 * Product search dto.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ProductSearchDTO extends SearchDTO {

	/**
	 * 검색어
	 *
	 * @author [윤태호]
	 */
	@ApiParam(value = "검색어", name = "keyword", defaultValue = "")
	private String keyword = "";

	/**
	 * 대분류
	 * @author [윤태호]
	 */
	@ApiParam(value = "대분류 코드",name = "category2Code", defaultValue = "")
	private String category2Code;

	/**
	 * 소분류
	 * @author [윤태호]
	 */
	@ApiParam(value = "소분류 코드", name = "category3Code", defaultValue = "")
	private String category3Code;

	/**
	 * 에이전시 시퀀스
	 */
	@ApiParam(value = "에이젼시 시퀀스",name = "agencySeq", defaultValue = "")
	private Long agencySeq;

	/**
	 * 노출 여부
	 */
	@ApiParam(value = "노출 여부", name = "exposureYn",allowableValues = "Y,N", defaultValue = "Y")
	private String exposureYn;

}
