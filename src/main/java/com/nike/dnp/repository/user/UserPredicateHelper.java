package com.nike.dnp.repository.user;

import com.nike.dnp.dto.user.UserSearchDTO;
import com.nike.dnp.entity.auth.QAuth;
import com.nike.dnp.entity.user.QUser;
import com.nike.dnp.util.DateUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DateTemplate;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDateTime;


/**
 * The Class User predicate helper.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 23. 오후 2:55:42
 * @Description User(유저) 검색 조건
 */
@UtilityClass
public class UserPredicateHelper {

    /**
     * Compare keyword predicate.
     *
     * @param userSearchDTO the user search dto
     * @return the predicate
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 2:55:42
     * @Description 검색어 비교
     */
    public Predicate compareKeyword(final UserSearchDTO userSearchDTO) {
        final BooleanBuilder builder = new BooleanBuilder();
        final String keyword = userSearchDTO.getKeyword();

        if (!keyword.isEmpty()) {
            builder
                .or(QUser.user.userId.contains(keyword))
                .or(QUser.user.nickname.contains(keyword));
        }

        return builder;
    }

    /**
     * Compare date predicate.
     *
     * @param userSearchDTO the user search dto
     * @return the predicate
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 2:55:42
     * @Description 로그인 일자 비교
     */
    public Predicate compareDate(final UserSearchDTO userSearchDTO) {
        final BooleanBuilder builder = new BooleanBuilder();
        final String beginDt = StringUtils.defaultString(userSearchDTO.getBeginDt());
        final String endDt = StringUtils.defaultString(userSearchDTO.getEndDt());

        if (!beginDt.isEmpty() && !endDt.isEmpty()) {
            final DateTemplate<LocalDateTime> beginDtExpression = DateUtil.formatDate(beginDt+" 00:00:00", "%Y-%m-%d %H:%M:%s");
            final DateTemplate<LocalDateTime> endDtExpression = DateUtil.formatDate(endDt+" 23:59:59", "%Y-%m-%d %H:%M:%s");
            builder.and(QUser.user.loginDt.between(beginDtExpression, endDtExpression));

        } else if (!beginDt.isEmpty()) {
            builder.and(QUser.user.loginDt.after(DateUtil.formatDate(beginDt+" 00:00:00", "%Y-%m-%d %H:%M:%s")));

        } else if (!endDt.isEmpty()) {
            builder.and(QUser.user.loginDt.before(DateUtil.formatDate(endDt + " 23:59:59", "%Y-%m-%d %H:%M:%s")));

        }
        return builder;
    }

    /**
     * Compare auth predicate.
     *
     * @param userSearchDTO the user search dto
     * @return the predicate
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 3:35:09
     * @Description 그룹 검색
     */
    public Predicate compareAuth(final UserSearchDTO userSearchDTO) {
        final BooleanBuilder builder = new BooleanBuilder();
        final Long authSeq = userSearchDTO.getAuthSeq();

        if (authSeq > 0) {
            builder.and(QAuth.auth.authSeq.eq(authSeq));
        }

        return builder;
    }

}
