package com.nike.dnp.repository.calendar;

import com.nike.dnp.dto.calendar.CalendarCheckDTO;
import com.nike.dnp.entity.calendar.Calendar;
import com.nike.dnp.entity.calendar.QCalendar;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Class Calendar repository.
 *
 * @author [윤태호]
 * @since 2020. 8. 7. 오후 6:11:48
 */
@Slf4j
@Repository
public class CalendarRepositoryImpl extends QuerydslRepositorySupport implements CalendarRepositoryCustom {
	/**
	 * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
	 *
	 * @param domainClass must not be {@literal null}.
	 * @author [윤태호]
	 * @since 2020. 8. 7. 오후 5:24:26
	 */
	public CalendarRepositoryImpl() {
		super(Calendar.class);
	}

	/**
	 * Find day list count list.
	 *
	 * @param paramCalendar the param calendar
	 * @return the list
	 * @author [윤태호]
	 * @since 2020. 8. 7. 오후 6:11:48
	 */
	@Override
	public List<CalendarCheckDTO> findDayListCount(final Calendar paramCalendar) {

		final QCalendar calendar = QCalendar.calendar;
		final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

		return queryFactory.select(Projections.bean(
															CalendarCheckDTO.class
															, calendar.beginDt
															, calendar.beginDt.count().as("count")))
													   .from(calendar)
													   .where(calendar.beginDt.goe(paramCalendar.getBeginDt())
																			  .and(calendar.endDt.loe(paramCalendar.getEndDt())))
													   .groupBy(calendar.beginDt).fetch();
	}
}
