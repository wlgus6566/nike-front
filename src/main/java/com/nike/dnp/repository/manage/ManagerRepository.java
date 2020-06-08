package com.nike.dnp.repository.manage;

import com.nike.dnp.entity.manage.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
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
public interface ManagerRepository extends JpaRepository<Manager, Long>, QuerydslPredicateExecutor<Manager> {

    Page<Manager> findAllByManagerIdLikeOrManagerNameLike(Pageable pageable, String keyword1, String keyword2);

    Optional<Manager> findByManagerSeq(Long managerSeq);

    Long countByManagerSeqOrManagerSeq(Long managerSeq1, Long managerSeq2);

    Long countByManagerSeqAndManagerName(Long managerSeq, String managerName);

    Long countByManagerIdAndManagerName(String managerId, String managerName);

    Manager findByManagerId(String managerId);
}
