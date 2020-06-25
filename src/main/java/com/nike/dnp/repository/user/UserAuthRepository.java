package com.nike.dnp.repository.user;

import com.nike.dnp.entity.user.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserAuthRepository
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 3:17:57
 * @Description UserAuth(유저 권한) Repository Interface 작성
 */
@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

    /**
     * Delete by user seq.
     *
     * @param userSeq the user seq
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 3:17:57
     * @Description 유저 권한 삭제
     */
    void deleteByUserSeq(Long userSeq);

}
