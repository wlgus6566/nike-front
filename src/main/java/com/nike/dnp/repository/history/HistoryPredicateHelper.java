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
 * @CreatedOn 2020. 7. 24. 오후 4:02:15
 * @Description
 */
@UtilityClass
public class HistoryPredicateHelper {

	/**
	 * Eq type cd predicate.
	 *
	 * @param historySearchDTO the history search dto
	 * @return the predicate
	 * @author [이소정]
	 * @CreatedOn 2020. 7. 24. 오후 4:03:57
	 * @Description
	 */
	public Predicate eqTypeCdViewHistory(final HistorySearchDTO historySearchDTO) {
		final BooleanBuilder builder = new BooleanBuilder();
		final String typeCd = historySearchDTO.getTypeCd();

		if(!StringUtils.isEmpty(typeCd.trim()) && !ServiceCode.HistoryTabEnumCode.ALL.toString().equals(typeCd.trim())) {
			builder.and(QHistory.history.typeCd.eq(typeCd));
		}

		return builder;
	}

	/**
	 * Eq type cd predicate.
	 *
	 * @param historySearchDTO the history search dto
	 * @return the predicate
	 * @author [이소정]
	 * @CreatedOn 2020. 7. 24. 오후 4:03:57
	 * @Description
	 */
	public Predicate eqTypeCdUploadHistory(final HistorySearchDTO historySearchDTO) {
		final BooleanBuilder builder = new BooleanBuilder();
		final String typeCd = historySearchDTO.getTypeCd();

		if(!StringUtils.isEmpty(typeCd.trim()) && !ServiceCode.HistoryTabEnumCode.ALL.toString().equals(typeCd.trim())) {
			builder.and(QRecentUpload.recentUpload.typeCd.eq(typeCd));
		}

		return builder;
	}
}
