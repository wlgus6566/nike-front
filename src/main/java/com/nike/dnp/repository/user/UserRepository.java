package com.nike.dnp.repository.user;

import com.nike.dnp.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository
 *
 * @author [오지훈]
 * @Description User(유저) Repository Interface 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    /**
     * 상세 조회
     *
     * @param userId 유저 ID
     * @return the optional
     * @author [오지훈]
     */
    Optional<User> findByUserId(String userId);

    /**
     * 상세 조회
     * TODO[ojh] 로그인 시 어떻게 DB 조회 하는지 확인 필요
     * @param userId   the user id
     * @param password the password
     * @return optional
     * @author [오지훈]
     */
    Optional<User> findByUserIdAndPassword(String userId, String password);

}
