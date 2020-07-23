package com.nike.dnp.dto.product;

import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ProductViewListDTO {

	@ApiParam(name = "goodsSeqList", value = "상품 시퀀스[배열]", defaultValue = "29,30,31",required = true)
	@NotNull(message = "product.goodsSeqList")
	List<Long> goodsSeqList;
}
