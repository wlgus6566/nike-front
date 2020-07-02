package com.nike.dnp.repository.log;

import com.nike.dnp.entity.log.UserActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserActionLogRepository
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 5:50:52
 * @Description UserActionLog(유저_활동_로그) Repository 작성
 */
@Repository
public interface UserActionLogRepository extends JpaRepository<UserActionLog, Long> {
}
