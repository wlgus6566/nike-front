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
 * @CreatedOn 2020. 7. 14. 오후 3:38:14
 * @Description
 */
@Repository
public interface WishListRepositoryCustom {

	/**
	 * Find pages wish list page.
	 *
	 * @param wishListSearchDTO the wish list search dto
	 * @param wishListSeq       the wish list seq
	 * @return the page
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 14. 오후 3:38:14
	 * @Description
	 */
	Page<WishList> findPagesWishList(final WishListSearchDTO wishListSearchDTO,
									 final PageRequest wishListSeq);
}
