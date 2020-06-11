package com.nike.dnp.repository.example;

import com.nike.dnp.entity.example.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ManagerRepository
 *
 * @author [오지훈]
 * @Description 관리자(유저) Repository 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long>, ManagerRepositoryCustom {

    /**
     *
     * @param managerId
     * @return
     */
    Manager findByManagerId(String managerId);

}
