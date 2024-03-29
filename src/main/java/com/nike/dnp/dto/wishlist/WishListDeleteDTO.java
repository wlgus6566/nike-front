package com.nike.dnp.dto.wishlist;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The Class Wish list delete dto.
 *
 * @author [윤태호]
 * @since 2020. 7. 6. 오후 3:41:39
 * @implNote
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class WishListDeleteDTO {

	/**
	 * 위시 리스트 시퀀스 리스트
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "제품 시퀀스 배열", allowableValues = "1,2,3")
	@NotNull(message = "product.goodsSeqList")
	private List<Long> goodsSeqList;

}
