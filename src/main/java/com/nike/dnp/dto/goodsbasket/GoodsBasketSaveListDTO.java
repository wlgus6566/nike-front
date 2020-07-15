package com.nike.dnp.dto.goodsbasket;

import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoodsBasketSaveListDTO extends BasicDTO {

	@ApiModelProperty(hidden = true)
	private Long userSeq;

	@ApiModelProperty(name = "상품 시퀀스", allowableValues = "1,2,3")
	private List<Long> goodsSeqList;

	@ApiModelProperty(name = "상품수량", allowableValues = "10,20,30")
	private List<Long> orderQuantityList;

}
