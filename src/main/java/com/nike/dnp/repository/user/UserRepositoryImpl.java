package com.nike.dnp.repository.user;

import com.nike.dnp.dto.user.UserSearchDTO;
import com.nike.dnp.entity.user.QUser;
import com.nike.dnp.entity.user.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

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
     * 조회(페이징)
     *
     * @param userSearchDTO the user search dto
     * @param pageRequest   the page request
     * @return the page
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오전 11:47:54
     * @Description
     */
    @Override
    public Page<User> findPages(UserSearchDTO userSearchDTO, PageRequest pageRequest) {
        final String keyword = userSearchDTO.getKeyword();
        final QUser qUser = QUser.user;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        /*
        final JPQLQuery<User> query = from(qUser)
                .where(eqUserId(keyword), eqNickname(keyword))
                .fetchAll();

        final List<User> users = getQuerydsl().applyPagination(pageRequest, query).fetch();
        */

        final List<User> users = queryFactory.selectFrom(qUser)
                .where(eqUserId(keyword), eqNickname(keyword))
                .fetch();

        return new PageImpl<>(users, pageRequest, users.size());
    }


    /**
     * 유저 ID 검색
     *
     * @param keyword the keyword
     * @return the boolean expression
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오전 11:47:51
     * @Description
     */
    private BooleanExpression eqUserId(final String keyword) {
        return keyword.isEmpty() ? null : QUser.user.userId.eq(keyword);
    }


    /**
     * 유저 닉네임 검색
     *
     * @param keyword the keyword
     * @return the boolean expression
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오전 11:47:54
     * @Description
     */
    private BooleanExpression eqNickname(final String keyword) {
        return keyword.isEmpty() ? null : QUser.user.nickname.eq(keyword);
    }
}
