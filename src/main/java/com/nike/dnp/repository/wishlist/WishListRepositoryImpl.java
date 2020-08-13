package com.nike.dnp.repository.wishlist;

import com.nike.dnp.dto.wishlist.WishListSearchDTO;
import com.nike.dnp.entity.wishlist.QWishList;
import com.nike.dnp.entity.wishlist.WishList;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Class Wish list repository.
 *
 * @author [윤태호]
 * @since 2020. 7. 3. 오후 3:55:37
 * @implNote
 */
@Slf4j
@Repository
public class WishListRepositoryImpl  extends QuerydslRepositorySupport implements WishListRepositoryCustom{

	/**
	 * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
	 *
	 * @author [윤태호]
	 * @since 2020. 7. 3. 오후 3:55:37
	 * @implNote
	 */
	public WishListRepositoryImpl() {
		super(WishList.class);
	}

	/**
	 * 위시리스트 페이징
	 *
	 * @param wishListSearchDTO the wish list search dto
	 * @param pageRequest       the page request
	 * @return the page
	 * @author [윤태호]
	 * @since 2020. 7. 3. 오후 3:55:37
	 * @implNote 위시리스트 페이징
	 */
	@Override
	public Page<WishList> findPagesWishList(final WishListSearchDTO wishListSearchDTO,
						 					final PageRequest pageRequest) {
		log.info("WishListRepositoryImpl.findPagesWishList");
		final QWishList wishList = QWishList.wishList;
		final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

		final JPAQuery<WishList> query= queryFactory.selectFrom(wishList)
				.where(
						WishListPredicateHelper.eqUserSeq(wishListSearchDTO.getUserSeq())
				);

		final List<WishList> resultList = getQuerydsl().applyPagination(pageRequest,query).fetch();
		return new PageImpl<>(resultList,pageRequest,query.fetchCount());
	}
}