package com.nike.dnp.dto.order;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiModelProperty;
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
	@ApiModelProperty(value = "검색어", name = "keyword")
	private String keyword = "";

	/**
	 * 카테고리 1
	 * @author [윤태호]
	 */
	@ApiModelProperty(value = "카테고리 1 코드",hidden = true)
	private String category1code;

	/**
	 * 카테고리 2
	 * @author [윤태호]
	 */
	@ApiModelProperty(value="카테고리 2 코드",name="category2code")
	private String category2code;

	/**
	 * 카테고리 3
	 * @author [윤태호]
	 */
	@ApiModelProperty(value = "카테고리 3 코드", name = "category2code")
	private String category3code;

}
