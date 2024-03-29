package com.nike.dnp.repository.user;

import com.nike.dnp.dto.user.UserSearchDTO;
import com.nike.dnp.entity.user.QUser;
import com.nike.dnp.entity.user.QUserAuth;
import com.nike.dnp.util.CustomExpression;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;


/**
 * The Class User predicate helper.
 *
 * @author [오지훈]
 * @implNote User(유저) 검색 조건
 * @since 2020. 6. 23. 오후 2:55:42
 */
@UtilityClass
public class UserPredicateHelper {

    /**
     * Compare keyword predicate.
     *
     * @param userSearchDTO the user search dto
     * @return the predicate
     * @author [오지훈]
     * @implNote 검색어 비교
     * @since 2020. 6. 23. 오후 2:55:42
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
     * Compare status predicate.
     *
     * @param userSearchDTO the user search dto
     * @return the predicate
     * @author [오지훈]
     * @implNote 상태값 비교
     * @since 2020. 7. 13. 오후 5:03:47
     */
    public Predicate compareStatus(final UserSearchDTO userSearchDTO) {
        final BooleanBuilder builder = new BooleanBuilder();
        final String status = userSearchDTO.getStatus();

        if (!status.isEmpty()) {
            builder.and(QUser.user.userStatusCode.eq(status));
        }

        return builder;
    }

    /**
     * Compare date predicate.
     *
     * @param userSearchDTO the user search dto
     * @return the predicate
     * @author [오지훈]
     * @implNote 로그인 일자 비교
     * @since 2020. 6. 23. 오후 2:55:42
     */
    public Predicate compareDate(final UserSearchDTO userSearchDTO) {
        final BooleanBuilder builder = new BooleanBuilder();
        final String beginDt = StringUtils.defaultString(userSearchDTO.getBeginDt());
        final String endDt = StringUtils.defaultString(userSearchDTO.getEndDt());

        if (!beginDt.isEmpty()) {
            builder.and(QUser.user.loginDt.after(CustomExpression.formatDate(beginDt+" 00:00:00", "%Y-%m-%d %H:%M:%s")));
        }

        if (!endDt.isEmpty()) {
            builder.and(QUser.user.loginDt.before(CustomExpression.formatDate(endDt + " 23:59:59", "%Y-%m-%d %H:%M:%s")));
        }

        /*if (!beginDt.isEmpty() && !endDt.isEmpty()) {
            final DateTemplate<LocalDateTime> beginDtExpression = CustomExpression.formatDate(beginDt+" 00:00:00", "%Y-%m-%d %H:%M:%s");
            final DateTemplate<LocalDateTime> endDtExpression = CustomExpression.formatDate(endDt+" 23:59:59", "%Y-%m-%d %H:%M:%s");
            builder.and(QUser.user.loginDt.between(beginDtExpression, endDtExpression));

        } else if (!beginDt.isEmpty()) {
            builder.and(QUser.user.loginDt.after(CustomExpression.formatDate(beginDt+" 00:00:00", "%Y-%m-%d %H:%M:%s")));

        } else if (!endDt.isEmpty()) {
            builder.and(QUser.user.loginDt.before(CustomExpression.formatDate(endDt + " 23:59:59", "%Y-%m-%d %H:%M:%s")));

        }*/
        return builder;
    }

    /**
     * Compare auth predicate.
     *
     * @param userSearchDTO the user search dto
     * @return the predicate
     * @author [오지훈]
     * @implNote 그룹 검색
     * @since 2020. 6. 23. 오후 3:35:09
     */
    public Predicate compareAuth(final UserSearchDTO userSearchDTO) {
        final BooleanBuilder builder = new BooleanBuilder();
        final Long authSeq = userSearchDTO.getAuthSeq();
        if (authSeq > 0) {
            builder.and(QUserAuth.userAuth.auth.authSeq.eq(authSeq));
        }
        return builder;
    }

    /**
     * Compare password change period predicate.
     *
     * @return the predicate
     * @author [오지훈]
     * @implNote 패스워드 변경일이 90일 이상된 경우
     * @since 2020. 7. 31. 오후 4:12:30
     */
    public Predicate comparePasswordChangePeriod() {
        return new BooleanBuilder().and(
                CustomExpression.dateDiff(QUser.user.passwordLastUpdateDt).gt(90));
    }

    /**
     * Compare password change period add days predicate.
     *
     * @param days the days
     * @return the predicate
     * @author [오지훈]
     * @implNote 패스워드 변경일이 90일 이상된 경우(1,3,7일 전)
     * @since 2020. 10. 5. 오후 12:16:17
     */
    public Predicate comparePasswordChangePeriodAddDays(final int days) {
        return new BooleanBuilder(CustomExpression.dateDiffAndAdd(QUser.user.passwordLastUpdateDt, 90).eq(days));
    }

    /**
     * Compare dormant prev period add days predicate.
     *
     * @param days the days
     * @return the predicate
     * @author [오지훈]
     * @implNote 휴면 회원 전환 예정 [days]전
     * @since 2020. 10. 13. 오전 11:54:02
     */
    public Predicate compareDormantPrevPeriodAddDays(final int days) {
        return new BooleanBuilder(CustomExpression.dateDiffAndAdd(QUser.user.loginDt, 365).eq(days));
    }
}
