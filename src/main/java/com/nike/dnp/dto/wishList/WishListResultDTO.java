package com.nike.dnp.dto.wishList;

import com.nike.dnp.entity.product.Product;
import com.nike.dnp.entity.wishList.WishList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishListResultDTO {


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


	public static WishListResultDTO ofSave(WishList wishList) {
		WishListResultDTO result = new WishListResultDTO();
		result.setGoodsSeq(wishList.getGoodsSeq());
		result.setWishListSeq(wishList.getWishListSeq());
		result.setUserSeq(wishList.getUserSeq());
		return result;
	}
}
