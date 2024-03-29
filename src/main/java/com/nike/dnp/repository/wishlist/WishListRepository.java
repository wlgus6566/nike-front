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

	/**
	 * 제품 시퀀스로 위시 리스트 삭제
	 *
	 * @param goodsSeq the goods seq
	 * @author [윤태호]
	 * @since 2020. 7. 30. 오후 12:00:20
	 */
	void deleteByGoodsSeq(Long goodsSeq);


	/**
	 * 제품 시퀀스로 위시 리스트 삭제
	 * @param goodsSeq
	 * @param userSeq
	 * @return
	 */
	List<WishList> findAllByGoodsSeqInAndRegisterSeq(List<Long> goodsSeq,Long userSeq);

	/**
	 * 위시리스트 등록 여부 조회
	 *
	 * @return the wish list
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 8. 24. 오후 2:28:13
	 */
	WishList findByGoodsSeqAndRegisterSeq(Long goodSeq,Long userSeq);
}