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
public class ManagerRepositoryImpl extends QuerydslRepositorySupport implements ManagerRepositoryCustom {

    /**
     * 생성자 주입
     * Instantiates a new Manager repository.
     */
    public ManagerRepositoryImpl() {
        super(Manager.class);
    }

    @Override
    public Page<Manager> findAlls(ManagerSearchDTO managerSearchDTO, PageRequest pageRequest) {
        final QManager qManager = QManager.manager;
        final JPQLQuery<Manager> query;

        query = from(qManager)
                .where(ManagerPredicateHelper.search(managerSearchDTO))
                .fetchAll();

        final List<Manager> managers = getQuerydsl().applyPagination(pageRequest, query).fetch();
        return new PageImpl<>(managers, pageRequest, query.fetchCount());
    }
}
