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
public class ProductUserSearchDTO extends SearchDTO {

	/**
	 * 검색어
	 *
	 * @author [윤태호]
	 */
	@ApiParam(value = "검색어", name = "keyword", defaultValue = "")
	private String keyword = "";

	/**
	 * 카테고리 2
	 * @author [윤태호]
	 */
	@ApiParam(value = "카테고리 2 코드",name = "category2code", defaultValue = "",hidden = true)
	private String category2code;

	/**
	 * 노출 여부
	 */
	@ApiParam(value = "노출 여부", name = "exposureYn",allowableValues = "Y,N", defaultValue = "Y",hidden = true)
	private String exposureYn;

}
