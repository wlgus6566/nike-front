package com.nike.dnp.repository.user;

import com.nike.dnp.entity.user.User;
import com.nike.dnp.entity.user.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserAuthRepository
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 3:17:57
 * @Description UserAuth(유저 권한) Repository Interface 작성
 */
@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long>, UserAuthRepositoryCustom {

    /**
     * Find by user optional.
     *
     * @param user the user
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 7. 14. 오전 10:09:26
     * @Description 상세 조회
     */
    Optional<UserAuth> findByUser(User user);

}
