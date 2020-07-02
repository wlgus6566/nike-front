package com.nike.dnp.repository.log;

import com.nike.dnp.entity.log.UserLoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserLoginLogRepository
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 5:50:55
 * @Description UserLoginLog(유저_로그인_로그) Repository 작성
 */
@Repository
public interface UserLoginLogRepository extends JpaRepository<UserLoginLog, Long> {
}
