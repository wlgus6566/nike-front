package com.nike.dnp.repository.wishlist;

import com.nike.dnp.dto.wishlist.WishListSearchDTO;
import com.nike.dnp.entity.wishlist.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * The Interface Wish list repository custom.
 *
 * @author [윤태호]
 * @since 2020. 7. 14. 오후 3:38:14
 * @implNote
 */
@Repository
public interface WishListRepositoryCustom {

	/**
	 * 위시 리스트 조회
	 *
	 * @param wishListSearchDTO the wish list search dto
	 * @param wishListSeq       the wish list seq
	 * @return the page
	 * @author [윤태호]
	 * @since 2020. 7. 14. 오후 3:38:14
	 * @implNote
	 */
	Page<WishList> findPagesWishList(final WishListSearchDTO wishListSearchDTO,
									 final PageRequest wishListSeq);
}