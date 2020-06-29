package com.nike.dnp.repository.user;

import com.nike.dnp.dto.user.UserSearchDTO;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.entity.auth.QAuth;
import com.nike.dnp.entity.user.QUser;
import com.nike.dnp.entity.user.QUserAuth;
import com.nike.dnp.entity.user.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
 * @CreatedOn 2020. 6. 22. 오전 11:47:54
 * @Description User(유저) Repository interface 작성
 */
@Repository
public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {

    /**
     * Instantiates a new User repository.
     *
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오전 11:47:54
     * @Description
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
     * @CreatedOn 2020. 6. 22. 오전 11:47:54
     * @Description 조회(페이징)
     */
    @Override
    public Page<User> findPages(final UserSearchDTO userSearchDTO, final PageRequest pageRequest) {
        final QUser qUser = QUser.user;
        final QUserAuth qUserAuth = QUserAuth.userAuth;
        final QAuth qAuth = QAuth.auth;

        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final JPAQuery<User> jpaUsers = queryFactory
                .select(Projections.bean(User.class, qUser.userId, qUser.nickname)
                    ,Projections.bean(Auth.class, qAuth.authName)
                )
                //.selectFrom(qUser)
                //.select(Projections.fields(User.class, qUser))
                //.select(Projections.array(User.class, Auth.class))
                .select(qUser)
                .from(qUser)
                .innerJoin(qUserAuth).on(qUser.userSeq.eq(qUserAuth.userSeq))
                .innerJoin(qAuth).on(qUserAuth.authSeq.eq(qAuth.authSeq))
                .where(UserPredicateHelper.compareKeyword(userSearchDTO)
                    , UserPredicateHelper.compareDate(userSearchDTO)
                    , UserPredicateHelper.compareAuth(userSearchDTO)
                );
        final List<User> users = Objects.requireNonNull(getQuerydsl()).applyPagination(pageRequest, jpaUsers).fetch();
        return new PageImpl<>(users, pageRequest, jpaUsers.fetchCount());
    }

}
