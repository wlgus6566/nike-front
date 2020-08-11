package com.nike.dnp.repository.product;


import com.nike.dnp.entity.product.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * predicate helper.
 *
 * @author [윤태호]
 * @implNote ProductPredicate 작성
 * @history [윤태호] [2020.06.17] [최초 작성]
 * @since 2020. 6. 19. 오후 4:05:23
 */
@UtilityClass
public class ProductPredicateHelper {


	/**
	 * 대분류 코드 조회
	 *
	 * @param category2Code the category 2 code
	 * @return the predicate
	 * @author [윤태호]
	 * @implNote 대분류 코드 조회
	 * @since 2020. 6. 23. 오후 5:05:02
	 */
	public static Predicate eqCate2gory(final String category2Code) {
		final BooleanBuilder builder = new BooleanBuilder();
		if(!StringUtils.isEmpty(category2Code)){
			builder.and(QProduct.product.category2Code.eq(category2Code));
		}
		return builder;
	}

	/**
	 * 소분류 코드 조회
	 *
	 * @param category3Code the category 3 code
	 * @return the predicate
	 * @author [윤태호]
	 * @implNote 소분류 코드 조회
	 * @since 2020. 6. 23. 오후 5:05:02
	 */
	public static Predicate eqCate3gory(final String category3Code) {
		final BooleanBuilder builder = new BooleanBuilder();
		if(!StringUtils.isEmpty(category3Code)){
			builder.and(QProduct.product.category3Code.eq(category3Code));
		}
		return builder;
	}

	/**
	 * 상품 이름 like 검색
	 *
	 * @param keyword the keyword
	 * @return the predicate
	 * @author [윤태호]
	 * @implNote 상품 이름 like 검색
	 * @since 2020. 6. 23. 오후 5:05:02
	 */
	public static Predicate likeGoodName(final String keyword) {
		final BooleanBuilder builder = new BooleanBuilder();
		if(!StringUtils.isEmpty(keyword)){
			builder.and(QProduct.product.goodsName.contains(keyword));
		}
		return builder;
	}


	/**
	 * 에이젼시 시퀀스 조회
	 *
	 * @param agencySeq the agency seq
	 * @return the predicate
	 * @author [윤태호]
	 * @implNote 에이젼시 시퀀스 조회
	 * @since 2020. 6. 23. 오후 5:07:20
	 */
	public static Predicate eqAgentSeq(final Long agencySeq) {
		final BooleanBuilder builder = new BooleanBuilder();
		if(!ObjectUtils.isEmpty(agencySeq)){
			builder.and(QProduct.product.agencySeq.eq(agencySeq));
		}
		return builder;
	}

	/**
	 * 노출여부 조회
	 *
	 * @param exposureYn the exposure yn
	 * @return the predicate
	 * @author [윤태호]
	 * @implNote 노출여부 조회
	 * @since 2020. 6. 23. 오후 5:25:34
	 */
	public static Predicate eqExposureYn(final String exposureYn) {
		final BooleanBuilder builder = new BooleanBuilder();
		if(!StringUtils.isEmpty(exposureYn)){
			builder.and(QProduct.product.exposureYn.eq(exposureYn));
		}
		return builder;
	}
}