package com.nike.dnp.dto.order;

import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsBasketSaveDTO extends BasicDTO {

	@ApiModelProperty(hidden = true)
	private Long userSeq;

	@ApiModelProperty(name = "상품 시퀀스", example = "33")
	private Long goodsSeq;

	@ApiModelProperty(name = "상품수량", example = "1")
	private Long orderQuantity;

}
