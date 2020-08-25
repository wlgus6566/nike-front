package com.nike.dnp.repository.history;


import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.history.HistorySearchDTO;
import com.nike.dnp.entity.contents.QRecentUpload;
import com.nike.dnp.entity.history.QHistory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

/**
 * The Class History predicate helper.
 *
 * @author [이소정]
 * @since 2020. 7. 24. 오후 4:02:15
 * @implNote
 */
@UtilityClass
public class HistoryPredicateHelper {

	/**
	 * Eq type cd predicate.
	 *
	 * @param historySearchDTO the history search dto
	 * @return the predicate
	 * @author [이소정]
	 * @since 2020. 7. 24. 오후 4:03:57
	 * @implNote
	 */
	public Predicate eqTypeCdViewHistory(final HistorySearchDTO historySearchDTO) {
		final BooleanBuilder builder = new BooleanBuilder();
		final String typeCd = historySearchDTO.getTypeCd();
		final String mobileYn = historySearchDTO.getMobileYn();

		final boolean isMobile = !StringUtils.isEmpty(mobileYn.trim()) && "Y".equals(mobileYn.trim());

		// mobile이고 타입코드가 없거나 All 인 경우
		if (isMobile && (StringUtils.isEmpty(typeCd.trim()) || ServiceCode.HistoryTabEnumCode.ALL.toString().equals(typeCd.trim()))) {
			builder.and(QHistory.history.typeCd.eq(ServiceCode.HistoryTabEnumCode.FOUNDATION.toString()));
			builder.and(QHistory.history.typeCd.eq(ServiceCode.HistoryTabEnumCode.TOOLKIT.toString()));
			builder.and(QHistory.history.typeCd.eq(ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString()));
		// 타입코드가 없지 않고 ALL이 아닌 경우
		} else if (!StringUtils.isEmpty(typeCd.trim()) && !ServiceCode.HistoryTabEnumCode.ALL.toString().equals(typeCd.trim())) {
			builder.and(QHistory.history.typeCd.eq(typeCd));
		}

		return builder;
	}

//	public Predicate mobileYnHistory(final HistorySearchDTO historySearchDTO) {
//		final BooleanBuilder builder = new BooleanBuilder();
//		final String mobileYn = historySearchDTO.getMobileYn();
//
//		if(!StringUtils.isEmpty(mobileYn.trim()) && !"Y".equals(mobileYn.trim())) {
//			builder.and(QHistory.history.typeCd.eq(ServiceCode.HistoryTabEnumCode..toString()));
//		}
//
//		return builder;
//	}

	/**
	 * Eq type cd predicate.
	 *
	 * @param historySearchDTO the history search dto
	 * @return the predicate
	 * @author [이소정]
	 * @since 2020. 7. 24. 오후 4:03:57
	 * @implNote
	 */
	public Predicate eqTypeCdUploadHistory(final HistorySearchDTO historySearchDTO) {
		final BooleanBuilder builder = new BooleanBuilder();
		final String typeCd = historySearchDTO.getTypeCd();
		final String mobileYn = historySearchDTO.getMobileYn();

		final boolean isMobile = !StringUtils.isEmpty(mobileYn.trim()) && "Y".equals(mobileYn.trim());

		// mobile이고 타입코드가 없거나 All 인 경우
		if (isMobile && (StringUtils.isEmpty(typeCd.trim()) || ServiceCode.HistoryTabEnumCode.ALL.toString().equals(typeCd.trim()))) {
			builder.and(QRecentUpload.recentUpload.typeCd.eq(ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString()));
			builder.or(QRecentUpload.recentUpload.typeCd.eq(ServiceCode.HistoryTabEnumCode.FOUNDATION.toString()));
			builder.or(QRecentUpload.recentUpload.typeCd.eq(ServiceCode.HistoryTabEnumCode.TOOLKIT.toString()));
			// 타입코드가 없지 않고 ALL이 아닌 경우
		} else if (!StringUtils.isEmpty(typeCd.trim()) && !ServiceCode.HistoryTabEnumCode.ALL.toString().equals(typeCd.trim())) {
			builder.and(QRecentUpload.recentUpload.typeCd.eq(typeCd));
		}

		return builder;
	}
}
