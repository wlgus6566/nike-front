package com.nike.dnp.dto.wishlist;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiParam;
import lombok.*;

/**
 * The Class Wish list search dto.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 3. 오후 3:44:44
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class WishListSearchDTO extends SearchDTO {

	/**
	 * The User seq
	 *
	 * @author [윤태호]
	 */
	@ApiParam(hidden = true)
	private Long userSeq;

}
