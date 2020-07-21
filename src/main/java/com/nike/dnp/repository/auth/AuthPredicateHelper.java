package com.nike.dnp.repository.auth;

import com.nike.dnp.entity.auth.QAuth;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;


/**
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 23. 오후 2:55:42
 * @Description Auth(권한) 검색 조건
 */
@UtilityClass
public class AuthPredicateHelper {

    /**
     * Compare depth predicate.
     *
     * @param authSeq   the auth seq
     * @param authDepth the auth depth
     * @return the predicate
     * @author [오지훈]
     * @CreatedOn 2020. 7. 21. 오후 5:11:00
     * @Description
     */
    public Predicate compareDepth(final Long authSeq, final Long authDepth) {
        final BooleanBuilder builder = new BooleanBuilder();
        final QAuth auth = new QAuth("auth");

        if (authDepth == 2) {
            builder
                .and(auth.authDepth.ne(1L))
                .and(auth.authSeq.eq(authSeq).or(auth.upperAuthSeq.eq(authSeq)));

        } else if (authDepth == 3) {
            builder
                .and(auth.authDepth.ne(1L))
                .and(auth.authSeq.eq(authSeq));

        }

        return builder;
    }

}
