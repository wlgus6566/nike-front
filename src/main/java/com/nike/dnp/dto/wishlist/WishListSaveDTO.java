package com.nike.dnp.dto.wishlist;

import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class WishListSaveDTO {

	@ApiParam(name = "goodsSeq", value = "제품 시퀀스", defaultValue = "4", required = true)
	@NotNull(message = "wishList.goodsSeq")
	private Long goodsSeq;
}
