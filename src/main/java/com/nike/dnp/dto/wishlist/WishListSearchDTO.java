package com.nike.dnp.dto.wishlist;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiParam;
import lombok.*;

/**
 * The Class Wish list search dto.
 *
 * @author [윤태호]
 * @since 2020. 7. 3. 오후 3:44:44
 * @implNote
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class WishListSearchDTO extends SearchDTO {

	/**
	 * 유저 시퀀스
	 *
	 * @author [윤태호]
	 */
	@ApiParam(hidden = true)
	private Long userSeq;

}
