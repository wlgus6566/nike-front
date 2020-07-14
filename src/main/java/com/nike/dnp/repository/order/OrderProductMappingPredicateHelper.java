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
 * @CreatedOn 2020. 7. 7. 오후 12:13:36
 * @Description
 */
@UtilityClass
public class OrderProductMappingPredicateHelper {
	/**
	 * After start dt predicate.
	 *
	 * @param beginDt the begin dt
	 * @return the predicate
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 7. 오후 12:13:36
	 * @Description
	 */
	public Predicate afterStartDt(final String beginDt) {
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
	 * Before end dt predicate.
	 *
	 * @param endDt the end dt
	 * @return the predicate
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 7. 오후 12:13:36
	 * @Description
	 */
	public Predicate beforeEndDt(final String endDt) {
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
