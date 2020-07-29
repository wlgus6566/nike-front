package com.nike.dnp.repository.wishlist;

import com.nike.dnp.entity.wishlist.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
 * The Interface Wish list repository.
 *
 * @author [윤태호]
 * @since 2020. 7. 6. 오후 3:45:22
 * @implNote
 */
public interface WishListRepository extends JpaRepository<WishList, Long> ,WishListRepositoryCustom {


	/**
	 * 위시 리스트 시퀀스 와 유저 시퀀스 로 위시 리스트 조회
	 *
	 * @param wishListSeq the wish list seq
	 * @param userSeq     the user seq
	 * @return the optional
	 * @author [윤태호]
	 * @since 2020. 7. 6. 오후 3:45:22
	 * @implNote
	 */
	Optional<WishList> findByWishListSeqAndUserSeq(Long wishListSeq,Long userSeq);


	/**
	 * 검색일 전 조회
	 *
	 * @param searchDt the search dt
	 * @return the list
	 * @author [윤태호]
	 * @since 2020. 7. 14. 오후 3:38:11
	 * @implNote
	 */
	List<WishList> findByRegistrationDtBefore(LocalDateTime searchDt);
}
