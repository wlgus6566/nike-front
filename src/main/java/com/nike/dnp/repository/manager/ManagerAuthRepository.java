package com.nike.dnp.repository.manager;

import com.nike.dnp.entity.manager.ManagerAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ManagerAuthRepository
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description 관리권한 Repository 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Repository
public interface ManagerAuthRepository extends JpaRepository<ManagerAuth, Long> {
}
