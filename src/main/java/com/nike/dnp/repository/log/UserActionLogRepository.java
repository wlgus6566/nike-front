package com.nike.dnp.repository.log;

import com.nike.dnp.entity.log.UserActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserActionLogRepository
 *
 * @author [오지훈]
 * @Description UserActionLog(유저_활동_로그) Repository 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Repository
public interface UserActionLogRepository extends JpaRepository<UserActionLog, Long> {
}
