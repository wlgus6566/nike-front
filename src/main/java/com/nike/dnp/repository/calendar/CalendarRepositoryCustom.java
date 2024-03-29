package com.nike.dnp.repository.calendar;

import com.nike.dnp.dto.calendar.CalendarSearchDTO;
import com.nike.dnp.entity.calendar.Calendar;

import java.util.List;

/**
 * The Interface Calendar repository custom.
 *
 * @author [윤태호]
 * @since 2020. 8. 7. 오후 6:11:40
 */
public interface CalendarRepositoryCustom {

	/**
	 * 년월로 켈린더 조회
	 *
	 * @param calendarSearchDTO the calendar search dto
	 * @return the list
	 * @author [윤태호]
	 * @implNote
	 * @Description 년월로 켈린더 조회
	 * @since 2020. 8. 10. 오후 4:52:28
	 */
	List<Calendar> findByMonthSearch(CalendarSearchDTO calendarSearchDTO);

}
