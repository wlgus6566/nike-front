package com.nike.dnp.repository.log;

import com.nike.dnp.entity.log.EmailSendingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * EmailSendingLogRepository
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:50:45
 * @implNote EmailSendingLog(메일_발송_로그) Repository 작성
 */
@Repository
public interface EmailSendingLogRepository extends JpaRepository<EmailSendingLog, Long> {
}
