package com.nike.dnp.repository.calendar;

import com.nike.dnp.entity.calendar.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The Interface Calendar repository.
 *
 * @author [김형욱]
 */
@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long>{

    List<Calendar> findByBeginDtGreaterThanEqualAndEndDtLessThanEqual(LocalDateTime beginDt, LocalDateTime endDt);


    List<Calendar> findAllByBeginDtBeforeAndEndDtAfter(LocalDateTime beginDt,LocalDateTime endDt);
}
