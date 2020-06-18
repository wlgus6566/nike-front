package com.nike.dnp.repository.auth;

import com.nike.dnp.entity.auth.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
