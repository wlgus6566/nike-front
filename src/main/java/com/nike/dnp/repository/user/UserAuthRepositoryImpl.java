package com.nike.dnp.repository.user;

import com.nike.dnp.dto.user.UserSearchDTO;
import com.nike.dnp.entity.auth.QAuth;
import com.nike.dnp.entity.user.QUser;
import com.nike.dnp.entity.user.QUserAuth;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.entity.user.UserAuth;
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
public class UserAuthRepositoryImpl extends QuerydslRepositorySupport implements UserAuthRepositoryCustom {

    /**
     * Instantiates a new User repository.
     *
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오전 11:47:54
     * @Description
     */
    public UserAuthRepositoryImpl() {
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
    public Page<UserAuth> findPages(final UserSearchDTO userSearchDTO, final PageRequest pageRequest) {
        final QUser qUser = QUser.user;
        final QUserAuth qUserAuth = QUserAuth.userAuth;
        final QAuth qAuth = QAuth.auth;

        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final JPAQuery<UserAuth> jpaQuery = queryFactory
                .selectFrom(qUserAuth)
                .from(qUserAuth)
                .where(UserPredicateHelper.compareKeyword(userSearchDTO)
                    , UserPredicateHelper.compareDate(userSearchDTO)
                    , UserPredicateHelper.compareAuth(userSearchDTO)
                );
        final List<UserAuth> userAuths = Objects.requireNonNull(getQuerydsl()).applyPagination(pageRequest, jpaQuery).fetch();
        return new PageImpl<>(userAuths, pageRequest, jpaQuery.fetchCount());
    }

}
