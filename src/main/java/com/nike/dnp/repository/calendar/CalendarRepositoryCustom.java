package com.nike.dnp.repository.calendar;

import com.nike.dnp.dto.calendar.CalendarCheckDTO;
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
	 * Find day list count list.
	 *
	 * @param calendar the calendar
	 * @return the list
	 * @author [윤태호]
	 * @since 2020. 8. 7. 오후 6:11:38
	 */
	List<CalendarCheckDTO> findDayListCount(final Calendar calendar);
}
