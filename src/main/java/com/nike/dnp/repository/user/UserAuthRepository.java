package com.nike.dnp.repository.user;

import com.nike.dnp.entity.user.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserAuthRepository
 *
 * @author [오지훈]
 * @Description UserAuth(유저 권한) Repository Interface 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

    /**
     * 유저 권한 삭제
     *
     * @param userSeq the user seq
     */
    void deleteByUserSeq(Long userSeq);

}
