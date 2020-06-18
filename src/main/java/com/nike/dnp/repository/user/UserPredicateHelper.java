package com.nike.dnp.repository.user;

import com.nike.dnp.dto.user.UserSearchDTO;
import com.nike.dnp.entity.user.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;

/**
 * UserPredicateHelper
 *
 * @author [오지훈]
 * @Description User(유저) Predicate Helper 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@UtilityClass
public class UserPredicateHelper {

    /**
     * 검색 조건
     *
     * @param userSearchDTO the user search dto
     * @return the predicate
     * @author [오지훈]
     */
    public static Predicate search(final UserSearchDTO userSearchDTO) {
        final BooleanBuilder builder = new BooleanBuilder();
        final String keyword = userSearchDTO.getKeyword();

        if (!keyword.isEmpty()) {
            builder
                .or(QUser.user.userId.eq(keyword))
                .or(QUser.user.nickname.eq(keyword));
        }

        return builder;
    }

}
