package com.nike.dnp.dto.goodsbasket;

import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

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
	 * 유저 seq
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(hidden = true)
	private Long userSeq;

	/**
	 * 상품 seq
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "상품 시퀀스", example = "33")
	@NotNull(message = "goodsBasket.goodsSeq")
	private Long goodsSeq;

	/**
	 * 상품 수량
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "상품 수량", example = "1")
	@NotNull(message = "goodsBasket.orderQuantity")
	private Long orderQuantity;

}
