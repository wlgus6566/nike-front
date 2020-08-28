package com.nike.dnp.dto.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * The Class Product view list dto.
 *
 * @author [윤태호]
 * @since 2020. 7. 24. 오전 9:58:57
 * @implNote
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ProductViewListDTO {

	/**
	 * 상품 시퀀스 리스트
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "goodsSeqList", value = "상품 시퀀스[배열]", example = "4",required = true)
	@NotNull(message = "product.goodsSeqList")
	private Long[] goodsSeqList;
}
