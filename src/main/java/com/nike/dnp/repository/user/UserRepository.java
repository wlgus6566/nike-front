package com.nike.dnp.repository.user;

import com.nike.dnp.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * UserRepository
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 5:53:42
 * @Description User(유저) Repository Interface 작성
 */
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    /**
     * Find by user id optional.
     *
     * @param userId 유저 ID
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:53:42
     * @Description 상세 조회
     */
    Optional<User> findByUserId(String userId);

    /**
     * Find all by user seq in list.
     *
     * @param userSeqArray the user seq array
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 6:15:21
     * @Description 목록 조회(배열)
     */
    List<User> findAllByUserSeqIn(Long[] userSeqArray);

}
