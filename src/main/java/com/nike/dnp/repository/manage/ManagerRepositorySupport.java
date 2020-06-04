package com.nike.dnp.repository.manage;

import com.nike.dnp.dto.manage.manager.ManagerSearchDTO;
import com.nike.dnp.entity.manage.Manager;
import com.nike.dnp.entity.manage.QManager;
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

    public ManagerRepositorySupport() {
        super(Manager.class);
    }

    public Manager getManager(long managerSeq) {
        QManager qManager = QManager.manager;
        return from(qManager)
                .where(qManager.managerSeq.eq(1L))
                .fetchOne();
    }

    public List<Manager> findAlls(ManagerSearchDTO managerSearchDTO) {
        String keyword = managerSearchDTO.getKeyword();
        QManager qManager = QManager.manager;
        return from(qManager)
                .where(qManager.managerName.eq(keyword)
                        .or(qManager.managerId.eq(keyword)))
                .orderBy(qManager.managerSeq.desc())
                .offset(managerSearchDTO.getPage())
                .limit(managerSearchDTO.getSize())
                .fetch();
    }


    /*queryFactory
            .selectFrom(qManager)
            .where(qManager.managerName.eq("master"), qManager.managerId.like("master"))
            .fetch();*/
}
