package com.nike.dnp.repository.contents;


import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.entity.contents.QContents;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

/**
 * The Class Contents predicate helper.
 *
 * @author [이소정]
 * @implNote 콘텐츠 검색 조건
 * @since 2020. 7. 1. 오후 5:56:06
 */
@UtilityClass
public class ContentsPredicateHelper {

	/**
	 * Compare keyword predicate.
	 *
	 * @param contentsSearchDTO the contents search dto
	 * @return the predicate
	 * @author [이소정]
	 * @implNote 콘텐츠 폴더명 비교
	 * @since 2020. 7. 2. 오전 11:14:36
	 */
	public Predicate compareKeyword(final ContentsSearchDTO contentsSearchDTO) {
		final BooleanBuilder builder = new BooleanBuilder();
		final String keyword = contentsSearchDTO.getKeyword();

		if(!StringUtils.isEmpty(keyword.trim())) {
			builder.and(QContents.contents.folderName.contains(keyword));
		}

		return builder;
	}

	/**
	 * Eq menu code predicate.
	 *
	 * @param contentsSearchDTO the contents search dto
	 * @return the predicate
	 * @author [이소정]
	 * @implNote 콘텐츠 메뉴 코드 비교
	 * @since 2020. 7. 2. 오전 11:14:34
	 */
	public Predicate eqMenuCode(final ContentsSearchDTO contentsSearchDTO) {
		final BooleanBuilder builder = new BooleanBuilder();
		final String topMenuCode = contentsSearchDTO.getTopMenuCode();
		final String menuCode = contentsSearchDTO.getMenuCode();

		builder.and(QContents.contents.topMenuCode.eq(topMenuCode));

		if (!StringUtils.isEmpty(menuCode) && !"ALL".equals(menuCode)) {
			builder.and(QContents.contents.menuCode.eq(menuCode));
		}
		return builder;
	}

	/**
	 * Eq exposure yn predicate.
	 *
	 * @param exposureYn the exposure yn
	 * @return the predicate
	 * @author [이소정]
	 * @implNote 콘텐츠 노출 여부 조회
	 * @since 2020. 7. 1. 오후 5:57:47
	 */
	public static Predicate eqExposureYn(final String exposureYn) {
		final BooleanBuilder builder = new BooleanBuilder();
		if(!StringUtils.isEmpty(exposureYn)){
			builder.and(QContents.contents.exposureYn.eq(exposureYn));
		}
		return builder;
	}
}
