package com.nike.dnp.dto.manage.manager;

import com.nike.dnp.entity.manage.QManager;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;

/**
 * ManagerPredicate
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description ManagerPredicate 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@UtilityClass
public class ManagerPredicate {

    /**
     * Search predicate.
     *
     * @param managerSearchDTO the manager search dto
     * @return the predicate
     */
    public static Predicate search(final ManagerSearchDTO managerSearchDTO) {
        final QManager qManager = QManager.manager;
        final BooleanBuilder builder = new BooleanBuilder();
        final String keyword = managerSearchDTO.getKeyword();

        if (!keyword.isEmpty()) {
            builder
                .or(qManager.managerId.eq(keyword))
                .or(qManager.managerName.eq(keyword));
        }

        return builder;
    }

}
