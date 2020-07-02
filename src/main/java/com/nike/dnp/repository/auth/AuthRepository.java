package com.nike.dnp.repository.auth;

import com.nike.dnp.entity.auth.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AuthRepository
 *
 * @author [오지훈]
 * @Description Auth(권한) Repository Interface 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {

    /**
     * Find all by use yn list.
     *
     * @param useYn the use yn
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 3:41:00
     * @Description (사용가능)권한 전체 목록
     */
    List<Auth> findAllByUseYn(String useYn);

}
