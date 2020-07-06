package com.nike.dnp.repository.wishList;

import com.nike.dnp.dto.wishList.WishListSearchDTO;
import com.nike.dnp.entity.wishList.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepositoryCustom {

	Page<WishList> findPagesWishList(final WishListSearchDTO wishListSearchDTO,
									 final PageRequest wishListSeq);
}
