package com.nike.dnp.repository.user;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.user.UserDTO;
import com.nike.dnp.dto.user.UserListDTO;
import com.nike.dnp.dto.user.UserSearchDTO;
import com.nike.dnp.entity.auth.QAuth;
import com.nike.dnp.entity.user.QUser;
import com.nike.dnp.entity.user.QUserAuth;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.util.CustomExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * UserRepositoryImpl
 *
 * @author [오지훈]
 * @since 2020. 6. 22. 오전 11:47:54
 * @implNote User(유저) Repository interface 작성
 */
@Slf4j
@Repository
public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {

    /**
     * Instantiates a new User repository.
     *
     * @author [오지훈]
     * @since 2020. 6. 22. 오전 11:47:54
     * @implNote
     */
    public UserRepositoryImpl() {
        super(User.class);
    }

    /**
     * Find pages page.
     *
     * @param userSearchDTO the user search dto
     * @param pageRequest   the page request
     * @return the page
     * @author [오지훈]
     * @since 2020. 6. 22. 오전 11:47:54
     * @implNote 조회(페이징)
     */
    @Override
    public Page<UserListDTO> findPages(final UserSearchDTO userSearchDTO, final PageRequest pageRequest) {
        log.info("UserRepositoryImpl.findPages");
        final QUser qUser = QUser.user;
        final QUserAuth qUserAuth = QUserAuth.userAuth;
        final QAuth qAuth = QAuth.auth;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final JPAQuery<UserListDTO> jpaQuery = queryFactory
                .select(Projections.bean(UserListDTO.class
                        , qUser.userSeq
                        , qUser.nickname
                        , qUser.userId
                        , qUser.userStatusCode
                        , qUser.loginDt
                        , qAuth.authName
                        )
                )
                .from(qUser)
                .innerJoin(qUserAuth).on(qUser.userSeq.eq(qUserAuth.userSeq))
                .innerJoin(qAuth).on(qUserAuth.authSeq.eq(qAuth.authSeq))
                .where(UserPredicateHelper.compareKeyword(userSearchDTO)
                    , UserPredicateHelper.compareDate(userSearchDTO)
                    , UserPredicateHelper.compareStatus(userSearchDTO)
                    , UserPredicateHelper.compareAuth(userSearchDTO)
                    , qUser.userStatusCode.ne(ServiceCode.UserStatusEnumCode.DELETE.toString())
                );
        final List<UserListDTO> users = Objects.requireNonNull(getQuerydsl()).applyPagination(pageRequest, jpaQuery).fetch();
        return new PageImpl<>(users, pageRequest, jpaQuery.fetchCount());
    }

    /**
     * Count by pasword change period int.
     *
     * @param userSeq the user seq
     * @return the int
     * @author [오지훈]
     * @since 2020. 7. 2. 오후 12:18:05
     * @implNote 90일 지난 패스워드 체크
     */
    @Override
    public long countByPasswordChangePeriod(final Long userSeq) {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        return queryFactory
                .selectFrom(QUser.user)
                .where(QUser.user.userSeq.eq(userSeq)
                        ,UserPredicateHelper.comparePasswordChangePeriod())
                .fetchCount();
    }

    /**
     * Find by password change configure list.
     *
     * @param days the days
     * @return the list
     * @author [오지훈]
     * @implNote 패스워드 변경일 90일 [days]일 전 유저 목록
     * @since 2020. 10. 5. 오후 12:11:39
     */
    @Override
    public List<User> findByPasswordChangeConfigure(final int days) {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        return queryFactory
                .selectFrom(QUser.user)
                .where(
                        UserPredicateHelper.comparePasswordChangePeriodAddDays(days)
                        ,QUser.user.userStatusCode.eq(ServiceCode.UserStatusEnumCode.NORMAL.name())
                )
                .fetch();
    }

    /**
     * Find by dormant prev configure list.
     *
     * @param days the days
     * @return the list
     * @author [오지훈]
     * @implNote 휴면회원 예정 [days]일 전 유저 목록
     * @since 2020. 10. 13. 오전 11:38:20
     */
    @Override
    public List<User> findByDormantPrevConfigure(final int days) {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        return queryFactory
                .selectFrom(QUser.user)
                .where(
                        QUser.user.userStatusCode.eq(ServiceCode.UserStatusEnumCode.NORMAL.name())
                        , QUser.user.loginDt.isNotNull()
                        , UserPredicateHelper.compareDormantPrevPeriodAddDays(days)
                )
                .fetch();
    }

    /**
     * Find by dormant delete list.
     *
     * @return the list
     * @author [오지훈]
     * @implNote 휴면회원 삭제 예정 목록
     * @since 2020. 10. 13. 오후 1:29:50
     */
    @Override
    public List<User> findByDormantDelete() {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        return queryFactory
                .selectFrom(QUser.user)
                .where(
                        QUser.user.userStatusCode.eq(ServiceCode.UserStatusEnumCode.DORMANT.name())
                        , CustomExpression.dateDiff(QUser.user.updateDt).gt(90)
                )
                .fetch();
    }

    @Override
    public List<UserDTO> findAllByAuthSeq(Long authSeq) {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final QUserAuth qUserAuth = QUserAuth.userAuth;
        final QUser qUser = QUser.user;

        final JPAQuery<UserDTO> jpaQuery = queryFactory
                .select(Projections.bean(UserDTO.class
                        , qUser.userSeq
                        , qUser.nickname
                        , qUser.userId
                        )
                )
                .from(qUser)
                .innerJoin(qUserAuth).on(qUser.userSeq.eq(qUserAuth.userSeq))
                .where(qUserAuth.authSeq.eq(authSeq)
                        , qUser.userStatusCode.eq("NORMAL"))
                ;
        return jpaQuery.fetch();
    }
}
