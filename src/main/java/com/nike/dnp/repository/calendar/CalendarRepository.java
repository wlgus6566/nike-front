package com.nike.dnp.repository.calendar;

import com.nike.dnp.entity.calendar.Calendar;
import com.nike.dnp.entity.code.Code;
import com.nike.dnp.entity.contents.ContentsFile;
import com.nike.dnp.repository.code.CodeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The Interface Calendar repository.
 *
 * @author [김형욱]
 */
@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long>{

    List<Calendar> findByBeginDtGreaterThanEqualAndEndDtLessThanEqual(LocalDateTime beginDt, LocalDateTime endDt);

}
