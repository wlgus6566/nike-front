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
 * @CreatedOn 2020. 7. 6. 오후 3:45:22
 * @Description
 */
public interface WishListRepository extends JpaRepository<WishList, Long> ,WishListRepositoryCustom {


	/**
	 * Find by wish list seq and user seq optional.
	 *
	 * @param wishListSeq the wish list seq
	 * @param userSeq     the user seq
	 * @return the optional
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 6. 오후 3:45:22
	 * @Description
	 */
	Optional<WishList> findByWishListSeqAndUserSeq(Long wishListSeq,Long userSeq);


	/**
	 * Find by registration dt before list.
	 *
	 * @param searchDt the search dt
	 * @return the list
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 14. 오후 3:38:11
	 * @Description
	 */
	List<WishList> findByRegistrationDtBefore(LocalDateTime searchDt);
}
