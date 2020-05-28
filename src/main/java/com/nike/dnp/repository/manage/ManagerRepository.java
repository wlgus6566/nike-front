package com.nike.dnp.repository.manage;

import com.nike.dnp.entity.manage.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ManagerRepository
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description 관리자(유저) Repository 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findByManagerSeq(Long managerSeq);

    Long countByManagerSeqOrManagerSeq(Long id1, Long id2);

    Long countByManagerSeqAndManagerName(Long id, String managerName);

    Long countByManagerIdAndManagerName(String managerId, String managerName);

}
