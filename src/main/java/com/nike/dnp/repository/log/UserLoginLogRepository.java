package com.nike.dnp.repository.log;

import com.nike.dnp.entity.log.UserLoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserLoginLogRepository
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:50:55
 * @implNote UserLoginLog(유저_로그인_로그) Repository 작성
 */
@Repository
public interface UserLoginLogRepository extends JpaRepository<UserLoginLog, Long> {

    /**
     * Find top 5 by user seq order by registration dt desc list.
     *
     * @param userSeq the user seq
     * @return the list
     * @author [오지훈]
     * @since 2020. 7. 6. 오후 2:55:54
     * @implNote 최근 5개 조회
     */
    List<UserLoginLog>findTop5ByUserSeqOrderByRegistrationDtDesc(Long userSeq);

}
