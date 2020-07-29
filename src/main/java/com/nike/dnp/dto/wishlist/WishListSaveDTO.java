package com.nike.dnp.dto.wishlist;

import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * The Class Wish list save dto.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 24. 오전 9:59:03
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class WishListSaveDTO {

	/**
	 * 상품 시퀀스
	 *
	 * @author [윤태호]
	 */
	@ApiParam(name = "goodsSeq", value = "제품 시퀀스", defaultValue = "4", required = true)
	@NotNull(message = "wishList.goodsSeq")
	private Long goodsSeq;
}
