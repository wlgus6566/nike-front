package com.nike.dnp.repository.calendar;

import com.nike.dnp.dto.calendar.CalendarSearchDTO;
import com.nike.dnp.entity.calendar.Calendar;
import com.nike.dnp.entity.calendar.QCalendar;
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
	 * @implNote 생성자 주입
	 * @since 2020. 8. 7. 오후 5:24:26
	 */
	public CalendarRepositoryImpl() {
		super(Calendar.class);
	}

	/**
	 * 년월로 일정 조회
	 *
	 * @param calendarSearchDTO the calendar search dto
	 * @return the list
	 * @author [윤태호]
	 * @implNote
	 * @Description 년월로 일정 조회
	 * @since 2020. 8. 10. 오후 5:07:47
	 */
	@Override
	public List<Calendar> findByMonthSearch(final CalendarSearchDTO calendarSearchDTO) {
		final QCalendar calendar = QCalendar.calendar;
		final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
		final int searchYearMonth =Integer.parseInt(calendarSearchDTO.getYyyyMm());
		return queryFactory.selectFrom(calendar)
					.where(calendar.beginDt.yearMonth().eq(searchYearMonth)
										   .or(calendar.endDt.yearMonth().eq(searchYearMonth))).fetch();
	}

}
