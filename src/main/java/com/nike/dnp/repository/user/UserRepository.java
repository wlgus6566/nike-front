package com.nike.dnp.repository.user;

import com.nike.dnp.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * UserRepository
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:53:42
 * @implNote User(유저) Repository Interface 작성
 */
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    /**
     * Find by user id optional.
     *
     * @param userId 유저 ID
     * @return the optional
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:53:42
     * @implNote 상세 조회
     */
    Optional<User> findByUserId(String userId);

    /**
     * Find all by user seq in list.
     *
     * @param userSeqArray the user seq array
     * @return the list
     * @author [오지훈]
     * @since 2020. 6. 23. 오후 6:15:21
     * @implNote 목록 조회(배열)
     */
    List<User> findAllByUserSeqIn(Long... userSeqArray);

    /**
     * Find by user status code list.
     *
     * @param userStatusCode the user status code
     * @return the list
     * @author [오지훈]
     * @implNote 정상 상태 유저 목록 조회
     * @since 2020. 9. 23. 오전 11:35:52
     */
    List<User> findByUserStatusCode(String userStatusCode);

    /**
     * Count by user id int.
     *
     * @param userId the user id
     * @return the int
     * @author [오지훈]
     * @since 2020. 7. 1. 오후 2:14:56
     * @implNote 유저 아이디 존재 유무
     */
    int countByUserId(String userId);

    /**
     * Count by user id and not userStatuscode 'DELETE' int.
     *
     * @param userId the user id
     * @return the int
     * @author [최미영]
     * @since 2021. 10. 12. 오전 11:33
     * @implNote 유저 상태값 'DELETE' 아닌 그리고 유저 존재 유무
     */
    int countByUserIdAndUserStatusCodeNot(String userId,String userStatusCode);


    /**
     * Find by user seq optional.
     *
     * @param userSeq the user seq
     * @return the optional
     * @author [오지훈]
     * @since 2020.09.07 오후 3:27:26
     * @implNote 유저 일련번호로 조회
     */
    Optional<User> findByUserSeq(Long userSeq);


    Optional<User> findByUserIdAndUserStatusCode(String userId,String userStatusCode);



}
