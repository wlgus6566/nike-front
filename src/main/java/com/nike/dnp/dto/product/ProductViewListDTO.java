package com.nike.dnp.dto.product;

import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The Class Product view list dto.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 24. 오전 9:58:57
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ProductViewListDTO {

	/**
	 * The Goods seq list
	 *
	 * @author [윤태호]
	 */
	@ApiParam(name = "goodsSeqList", value = "상품 시퀀스[배열]", defaultValue = "29,30,31",required = true)
	@NotNull(message = "product.goodsSeqList")
	private List<Long> goodsSeqList;
}
