package com.nike.dnp.repository.wishlist;

import com.nike.dnp.entity.wishlist.QWishList;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;

/**
 * WishListPredicateHelper
 *
 * @author [윤태호]
 * @since 2020. 7. 3. 오후 3:54:34
 * @implNote
 */
@UtilityClass
public class WishListPredicateHelper {


	/**
	 * 유저 시퀀스 조건
	 *
	 * @param userSeq the user seq
	 * @return the predicate
	 * @author [윤태호]
	 * @since 2020. 7. 3. 오후 3:54:34
	 * @implNote 유저 시퀀스 조건
	 */
	public static Predicate eqUserSeq(final Long userSeq) {
		final BooleanBuilder builder = new BooleanBuilder();
		if(!ObjectUtils.isEmpty(userSeq)){
			builder.and(QWishList.wishList.userSeq.eq(userSeq));
		}
		return builder;
	}
}