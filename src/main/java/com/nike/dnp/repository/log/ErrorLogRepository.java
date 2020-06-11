package com.nike.dnp.repository.log;

import com.nike.dnp.entity.log.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * ErrorLogRepository
 *
 * @author [오지훈]
 * @Description ErrorLog(오류 로그) Repository 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long>, QuerydslPredicateExecutor<ErrorLog> {

}
