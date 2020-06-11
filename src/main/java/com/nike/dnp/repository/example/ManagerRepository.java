package com.nike.dnp.repository.example;

import com.nike.dnp.entity.example.Manager;
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

    /**
     * Find all by manager id like or manager name like page.
     *
     * @param pageable the pageable
     * @param keyword1 the keyword 1
     * @param keyword2 the keyword 2
     * @return the page
     */
    Page<Manager> findAllByManagerIdLikeOrManagerNameLike(Pageable pageable, String keyword1, String keyword2);

    /**
     * Find by manager seq optional.
     *
     * @param managerSeq the manager seq
     * @return the optional
     */
    Optional<Manager> findByManagerSeq(Long managerSeq);

    /**
     * Count by manager seq or manager seq long.
     *
     * @param managerSeq1 the manager seq 1
     * @param managerSeq2 the manager seq 2
     * @return the long
     */
    Long countByManagerSeqOrManagerSeq(Long managerSeq1, Long managerSeq2);

    /**
     * Count by manager seq and manager name long.
     *
     * @param managerSeq  the manager seq
     * @param managerName the manager name
     * @return the long
     */
    Long countByManagerSeqAndManagerName(Long managerSeq, String managerName);

    /**
     * Count by manager id and manager name long.
     *
     * @param managerId   the manager id
     * @param managerName the manager name
     * @return the long
     */
    Long countByManagerIdAndManagerName(String managerId, String managerName);

    /**
     * Find by manager id manager.
     *
     * @param managerId the manager id
     * @return the manager
     */
    Manager findByManagerId(String managerId);
}
