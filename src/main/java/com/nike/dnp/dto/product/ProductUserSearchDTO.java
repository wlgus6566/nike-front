package com.nike.dnp.dto.product;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiParam;
import lombok.*;


/**
 * Product search dto.
 *
 * @author [오지훈]
 * @since 2020. 7. 21. 오후 3:55:34
 * @implNote
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ProductUserSearchDTO extends SearchDTO {

	/**
	 * 검색어
	 *
	 * @author [윤태호]
	 */
	@ApiParam(value = "검색어", name = "keyword", defaultValue = "")
	private String keyword = "";

	/**
	 * 대분류 코드
	 *
	 * @author [윤태호]
	 */
	@ApiParam(value = "대분류 코드",name = "category2Code", defaultValue = "",hidden = true)
	private String category2Code;

	/**
	 * 소분류 코드
	 *
	 * @author [오지훈]
	 */
	@ApiParam(value = "소분류 코드", name = "category3Code", allowableValues = "SUBSIDIARY21,SUBSIDIARY22,SUBSIDIARY23,SUBSIDIARY24,SUBSIDIARY25,SUBSIDIARY26",required = true)
	private String category3Code;
	/**
	 * 노출 여부
	 *
	 * @author [오지훈]
	 */
	@ApiParam(value = "노출 여부", name = "exposureYn",allowableValues = "Y,N", defaultValue = "Y",hidden = true)
	private String exposureYn;

}
