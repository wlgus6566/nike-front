package com.nike.dnp.repository.wishlist;

import com.nike.dnp.dto.wishlist.WishListSearchDTO;
import com.nike.dnp.entity.wishlist.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepositoryCustom {

	Page<WishList> findPagesWishList(final WishListSearchDTO wishListSearchDTO,
									 final PageRequest wishListSeq);
}
