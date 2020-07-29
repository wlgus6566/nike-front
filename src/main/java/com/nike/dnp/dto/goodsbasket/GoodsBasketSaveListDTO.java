package com.nike.dnp.dto.goodsbasket;

import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The Class Goods basket save list dto.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 21. 오후 3:54:50
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class GoodsBasketSaveListDTO extends BasicDTO {

	/**
	 * The User seq
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(hidden = true)
	private Long userSeq;

	/**
	 * The Goods seq list
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "상품 시퀀스", allowableValues = "1,2,3")
	@NotNull(message = "goodsBasket.goodsSeq")
	private List<Long> goodsSeqList;

	/**
	 * The Order quantity list
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "상품수량", allowableValues = "10,20,30")
	@NotNull(message = "goodsBasket.orderQuantity")
	private List<Long> orderQuantityList;

}
