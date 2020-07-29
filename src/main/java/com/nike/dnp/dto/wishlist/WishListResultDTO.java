package com.nike.dnp.dto.wishlist;

import com.nike.dnp.entity.product.Product;
import com.nike.dnp.entity.wishlist.WishList;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class Wish list result dto.
 *
 * @author [윤태호]
 * @since 2020. 7. 6. 오후 3:40:56
 * @implNote
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class WishListResultDTO {

	/**
	 * 위시 리스트 시퀀스
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "wishListSeq", value = "위시 리스트 시퀀스")
	private Long wishListSeq;

	/**
	 * 유저 시퀀스
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "userSeq", value = "유저 시퀀스")
	private Long userSeq;

	/**
	 * 상품 시퀀스
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "goodsSeq", value = "상품 시퀀스")
	private Long goodsSeq;

	/**
	 * 상품 정보
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "product", value = "상품정보")
	private Product product;


	/**
	 * 위시리스트 엔티티를 dto로 변경
	 *
	 * @param wishList the wish list
	 * @return the wish list result dto
	 * @author [윤태호]
	 * @since 2020. 7. 6. 오후 3:40:56
	 * @implNote
	 */
	public static WishListResultDTO ofSave(final WishList wishList) {
		final WishListResultDTO result = new WishListResultDTO();
		result.setGoodsSeq(wishList.getGoodsSeq());
		result.setWishListSeq(wishList.getWishListSeq());
		result.setUserSeq(wishList.getUserSeq());
		return result;
	}
}
