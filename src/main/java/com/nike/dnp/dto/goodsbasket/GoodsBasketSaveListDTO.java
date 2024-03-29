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
 * @since 2020. 7. 21. 오후 3:54:50
 * @implNote
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class GoodsBasketSaveListDTO extends BasicDTO {

	/**
	 * 유저 시퀀스
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(hidden = true)
	private Long userSeq;

	/**
	 * 상품 시퀀스 리스트
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "상품 시퀀스", allowableValues = "1,2,3")
	@NotNull(message = "goodsBasket.goodsSeq")
	private List<Long> goodsSeqList;

	/**
	 * 상품 수량 리스트
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "상품수량", allowableValues = "10,20,30")
	@NotNull(message = "goodsBasket.orderQuantity")
	private List<Long> orderQuantityList;

}
