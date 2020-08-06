package com.nike.dnp.repository.order;

import com.nike.dnp.entity.order.QOrderEntity;
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
 * @implNote
 * @since 2020. 7. 7. 오후 12:13:36
 */
@UtilityClass
public class OrderPredicateHelper {

	/**
	 * where 시작 날짜 이후
	 *
	 * @param beginDt the begin dt
	 * @return the predicate
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 7. 오후 12:13:36
	 */
	public static Predicate afterStartDt(final String beginDt) {
		final BooleanBuilder builder = new BooleanBuilder();
		if(!ObjectUtils.isEmpty(beginDt)){
			builder.and(QOrderEntity.orderEntity.registrationDt.after(
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
	 * @implNote
	 * @since 2020. 7. 7. 오후 12:13:36
	 */
	public static Predicate beforeEndDt(final String endDt) {
		final BooleanBuilder builder = new BooleanBuilder();
		if(!ObjectUtils.isEmpty(endDt)){
			builder.and(QOrderEntity.orderEntity.registrationDt.before(
					LocalDateTime.of(
							LocalDate.parse(endDt, DateTimeFormatter.ISO_DATE),
							LocalTime.of(23, 59, 59))));
		}
		return builder;
	}

	/**
	 * eq 작성자
	 *
	 * @param userSeq the user seq
	 * @return the predicate
	 * @author [윤태호]
	 * @since 2020. 8. 5. 오후 3:48:37
	 */
	public static Predicate eqRegisterSeq(Long userSeq) {
		final BooleanBuilder builder = new BooleanBuilder();
		if(!ObjectUtils.isEmpty(userSeq)){
			builder.and(QOrderEntity.orderEntity.registerSeq.eq(userSeq));
		}
		return builder;
	}

	/**
	 * eq useYn
	 *
	 * @param useYn the use yn
	 * @return the predicate
	 * @author [윤태호]
	 * @since 2020. 8. 5. 오후 3:48:37
	 */
	public static Predicate eqUseYn(String useYn) {
		final BooleanBuilder builder = new BooleanBuilder();
		if(!ObjectUtils.isEmpty(useYn)){
			builder.and(QOrderEntity.orderEntity.useYn.eq(useYn));
		}
		return builder;
	}
}
