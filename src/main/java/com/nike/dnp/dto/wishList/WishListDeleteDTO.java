package com.nike.dnp.dto.wishList;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * The Class Wish list delete dto.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 6. 오후 3:41:39
 * @Description
 */
@Getter
@Setter
public class WishListDeleteDTO {

	/**
	 * The Wish list seq list
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "위시리스트 시퀀스 배열", allowableValues = "1,2,3")
	private List<Long> wishListSeqList;

}
