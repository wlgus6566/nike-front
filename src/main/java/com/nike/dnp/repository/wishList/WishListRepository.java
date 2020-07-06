package com.nike.dnp.repository.wishList;

import com.nike.dnp.entity.wishList.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface WishListRepository extends JpaRepository<WishList, Long> ,WishListRepositoryCustom {


	Optional<WishList> findByWishListSeqAndUserSeq(Long wishListSeq,Long userSeq);
}
