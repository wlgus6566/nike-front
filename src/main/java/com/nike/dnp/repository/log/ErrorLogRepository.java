package com.nike.dnp.repository.log;

import com.nike.dnp.entity.log.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ErrorLogRepository
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:50:48
 * @implNote ErrorLog(오류 로그) Repository 작성
 */
@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {
}
