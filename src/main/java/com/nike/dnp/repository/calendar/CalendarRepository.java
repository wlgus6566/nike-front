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
 * @since 2020. 7. 29. 오후 3:27:03
 */
@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long>,CalendarRepositoryCustom{


    /**
     * 시작날짜 종료 날짜로 캘린더 조회
     *
     * @param beginDt the begin dt
     * @param endDt   the end dt
     * @return the list
     * @author [윤태호]
     * @implNote 시작날짜 종료 날짜로 캘린더 조회
     * @since 2020. 7. 29. 오후 3:27:03
     */
    List<Calendar> findAllByBeginDtBeforeAndEndDtAfterOrderByRegistrationDtDesc(LocalDateTime beginDt, LocalDateTime endDt);


    /**
     * 날짜별 등록된 컨텐츠 수 조회
     *
     * @param beginDt the begin dt
     * @param endDt   the end dt
     * @return the long
     * @author [윤태호]
     * @implNote
     * @since 2020. 8. 27. 오후 7:12:08
     */
    long countByBeginDtBeforeAndEndDtAfter(LocalDateTime beginDt, LocalDateTime endDt);
}
