package com.nike.dnp.repository.example;

import com.nike.dnp.dto.example.manager.ManagerSearchDTO;
import com.nike.dnp.entity.example.Manager;
import com.nike.dnp.entity.example.QManager;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

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
public class ManagerRepositorySupport extends QuerydslRepositorySupport {

    /**
     * Instantiates a new Manager repository support.
     */
    public ManagerRepositorySupport() {
        super(Manager.class);
    }

    /**
     * Gets manager.
     *
     * @param managerSeq the manager seq
     * @return the manager
     */
    public Manager findByManagerSeq(final long managerSeq) {
        final QManager qManager = QManager.manager;
        return from(qManager)
                .where(qManager.managerSeq.eq(managerSeq))
                .fetchOne();
    }

    /**
     * Find alls list.
     *
     * @param managerSearchDTO the manager search dto
     * @return the list
     */
    public Page<Manager> findAlls(
            final ManagerSearchDTO managerSearchDTO
            , final PageRequest pageRequest
            ) {
        final String keyword = managerSearchDTO.getKeyword();
        final QManager qManager = QManager.manager;
        final JPQLQuery<Manager> query;

        if (String.valueOf(keyword).isEmpty()) {
            query = from(qManager)
                    .orderBy(qManager.managerSeq.desc())
                    .offset(managerSearchDTO.getPage())
                    .limit(managerSearchDTO.getSize())
                    .fetchAll();
        } else {
            query = from(qManager)
                    .where(qManager.managerName.eq(keyword)
                            .or(qManager.managerId.eq(keyword)))
                    .orderBy(qManager.managerSeq.desc())
                    .offset(managerSearchDTO.getPage())
                    .limit(managerSearchDTO.getSize())
                    .fetchAll();
        }


        final List<Manager> managers = getQuerydsl().applyPagination(pageRequest, query).fetch();
        return new PageImpl<>(managers, pageRequest, query.fetchCount());
    }
}
