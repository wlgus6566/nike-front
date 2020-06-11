package com.nike.dnp.repository.log;

import com.nike.dnp.entity.log.UserLoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * UserLoginLogRepository
 *
 * @author [오지훈]
 * @Description UserLoginLog(유저_로그인_로그) Repository 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Repository
public interface UserLoginLogRepository extends JpaRepository<UserLoginLog, Long>, QuerydslPredicateExecutor<UserLoginLog> {

}
