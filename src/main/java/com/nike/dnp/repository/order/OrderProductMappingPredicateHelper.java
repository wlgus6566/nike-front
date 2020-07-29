package com.nike.dnp.repository.order;

import com.nike.dnp.entity.order.QOrderProductMapping;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The Class Order product mapping predicate helper.
 *
 * @author [윤태호]
 * @since 2020. 7. 7. 오후 12:13:36
 * @implNote
 */
@UtilityClass
public class OrderProductMappingPredicateHelper {

	/**
	 * where 시작 날짜 이후
	 *
	 * @param beginDt the begin dt
	 * @return the predicate
	 * @author [윤태호]
	 * @since 2020. 7. 7. 오후 12:13:36
	 * @implNote
	 */
	public static Predicate afterStartDt(final String beginDt) {
		final BooleanBuilder builder = new BooleanBuilder();
		if(!ObjectUtils.isEmpty(beginDt)){
			builder.and(QOrderProductMapping.orderProductMapping.registrationDt.after(
					LocalDateTime.of(
							LocalDate.parse(beginDt, DateTimeFormatter.ISO_DATE),
							LocalTime.of(0,0,0))
			));
		}
		return builder;
	}

	/**
	 * 종료 날짜 이전
	 *
	 * @param endDt the end dt
	 * @return the predicate
	 * @author [윤태호]
	 * @since 2020. 7. 7. 오후 12:13:36
	 * @implNote
	 */
	public static Predicate beforeEndDt(final String endDt) {
		final BooleanBuilder builder = new BooleanBuilder();
		if(!ObjectUtils.isEmpty(endDt)){
			builder.and(QOrderProductMapping.orderProductMapping.registrationDt.before(
					LocalDateTime.of(
							LocalDate.parse(endDt, DateTimeFormatter.ISO_DATE),
							LocalTime.of(0, 0, 0))));
		}
		return builder;
	}
}
