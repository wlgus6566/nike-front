package com.nike.dnp.repository.auth;

import com.nike.dnp.entity.auth.QAuth;
import com.nike.dnp.entity.menu.QMenu;
import com.nike.dnp.entity.menu.QMenuRole;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;


/**
 *
 * @AUTHOR[오지훈]
*@SINCE2020.6.23.오후2:55:42
*@IMPL NOTE  AUTH(권한)검색조건
*/
@UtilityClass
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

    /**
     * Eq menu code predicate.
     *
     * @param menuCode the menu code
     * @return the predicate
     * @author [오지훈]
     * @implNote skillCode 비교
     * @since 2020. 8. 25. 오후 5:31:01
     */
    public Predicate eqMenuCode(final String menuCode) {
        final BooleanBuilder builder = new BooleanBuilder();
        final QMenu menu = new QMenu("menu");

        if (!ObjectUtils.isEmpty(menuCode)) {
            builder.and(menu.menuCode.eq(menuCode));
        }

        return builder;
    }

    /**
     * Eq skill code predicate.
     *
     * @param skillCode the skill code
     * @return the predicate
     * @author [오지훈]
     * @implNote menuCode 비교
     * @since 2020. 8. 25. 오후 5:31:02
     */
    public Predicate eqSkillCode(final String skillCode) {
        final BooleanBuilder builder = new BooleanBuilder();
        final QMenuRole menuRole = new QMenuRole("menuRole");

        if (!ObjectUtils.isEmpty(skillCode)) {
            builder.and(menuRole.menuSkillCode.eq(skillCode));
        }

        return builder;
    }

}
