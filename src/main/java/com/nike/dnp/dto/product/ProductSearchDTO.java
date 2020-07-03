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
	@ApiParam(value = "검색어", name = "keyword", defaultValue = "옷")
	private String keyword = "";

	/**
	 * 카테고리 2
	 * @author [윤태호]
	 */
	@ApiParam(value = "카테고리 2 코드",name = "category2code", defaultValue = "2")
	private String category2code;

	/**
	 * 카테고리 3
	 * @author [윤태호]
	 */
	@ApiParam(value = "카테고리 3 코드", name = "category2code", defaultValue = "3")
	private String category3code;

	/**
	 * 에이전시 시퀀스
	 */
	@ApiParam(value = "에이젼시 시퀀스",name = "agencySeq", defaultValue = "1")
	private Long agencySeq;

	/**
	 * 노출 여부
	 */
	@ApiParam(value = "노출 여부", name = "exposureYn",allowableValues = "Y,N", defaultValue = "Y")
	private String exposureYn;

}
