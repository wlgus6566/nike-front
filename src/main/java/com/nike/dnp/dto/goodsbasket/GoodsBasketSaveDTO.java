package com.nike.dnp.dto.goodsbasket;

import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class Goods basket save dto.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 21. 오후 3:54:41
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class GoodsBasketSaveDTO extends BasicDTO {

	/**
	 * The User seq
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(hidden = true)
	private Long userSeq;

	/**
	 * The Goods seq
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "상품 시퀀스", example = "33")
	private Long goodsSeq;

	/**
	 * The Order quantity
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "상품수량", example = "1")
	private Long orderQuantity;

}
