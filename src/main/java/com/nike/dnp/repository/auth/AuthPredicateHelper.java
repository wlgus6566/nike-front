package com.nike.dnp.repository.auth;

import com.nike.dnp.entity.auth.QAuth;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;


/**
 *
 * @author [오지훈]
 * @since 2020. 6. 23. 오후 2:55:42
 * @implNote Auth(권한) 검색 조건
 */
@UtilityClass
@NoArgsConstructor
public class AuthPredicateHelper {

    /**
     * The constant SECOND_DEPTH
     *
     * @author [오지훈]
     */
    private final static Long SECOND_DEPTH = 2L;

    /**
     * The constant THIRD_DEPTH
     *
     * @author [오지훈]
     */
    private final static Long THIRD_DEPTH = 3L;

    /**
     * Compare depth predicate.
     *
     * @param authSeq   the auth seq
     * @param authDepth the auth depth
     * @return the predicate
     * @author [오지훈]
     * @since 2020. 7. 21. 오후 5:11:00
     * @implNote
     */
    public Predicate compareDepth(final Long authSeq, final Long authDepth) {
        final BooleanBuilder builder = new BooleanBuilder();
        final QAuth auth = new QAuth("auth");

        if (authDepth.equals(SECOND_DEPTH)) {
            builder
                .and(auth.authDepth.ne(1L))
                .and(auth.authSeq.eq(authSeq).or(auth.upperAuthSeq.eq(authSeq)));

        } else if (authDepth.equals(THIRD_DEPTH)) {
            builder
                .and(auth.authDepth.ne(1L))
                .and(auth.authSeq.eq(authSeq));

        }

        return builder;
    }

}
