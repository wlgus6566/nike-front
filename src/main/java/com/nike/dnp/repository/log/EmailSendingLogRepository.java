package com.nike.dnp.repository.log;

import com.nike.dnp.entity.log.EmailSendingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * EmailSendingLogRepository
 *
 * @author [오지훈]
 * @Description EmailSendingLog(메일_발송_로그) Repository 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Repository
public interface EmailSendingLogRepository extends JpaRepository<EmailSendingLog, Long>, QuerydslPredicateExecutor<EmailSendingLog> {

}
