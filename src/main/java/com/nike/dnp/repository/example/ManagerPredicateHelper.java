package com.nike.dnp.repository.example;

import com.nike.dnp.dto.example.manager.ManagerSearchDTO;
import com.nike.dnp.entity.example.QManager;
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
public class ManagerPredicateHelper {

    /**
     * 검색 조건
     *
     * @param managerSearchDTO the manager search dto
     * @return the predicate
     */
    public static Predicate search(final ManagerSearchDTO managerSearchDTO) {
        final BooleanBuilder builder = new BooleanBuilder();
        final String keyword = managerSearchDTO.getKeyword();

        if (!keyword.isEmpty()) {
            builder
                .or(QManager.manager.managerId.eq(keyword))
                .or(QManager.manager.managerName.eq(keyword));
        }

        return builder;
    }

}
