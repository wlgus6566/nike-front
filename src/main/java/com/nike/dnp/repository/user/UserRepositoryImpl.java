package com.nike.dnp.repository.user;

import com.nike.dnp.dto.user.UserSearchDTO;
import com.nike.dnp.entity.user.QUser;
import com.nike.dnp.entity.user.User;
import com.querydsl.jpa.JPQLQuery;
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
 * @Description User(유저) Repository interface 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Repository
public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {

    /**
     * Instantiates a new User repository.
     */
    public UserRepositoryImpl() {
        super(User.class);
    }

    /**
     * Find alls page.
     *
     * @param userSearchDTO the user search dto
     * @param pageRequest   the page request
     * @return the page
     */
    @Override
    public Page<User> findAlls(UserSearchDTO userSearchDTO, PageRequest pageRequest) {
        final QUser qUser = QUser.user;
        final JPQLQuery<User> query = from(qUser)
                .where(UserPredicateHelper.search(userSearchDTO))
                .fetchAll();

        final List<User> users = getQuerydsl().applyPagination(pageRequest, query).fetch();
        return new PageImpl<>(users, pageRequest, query.fetchCount());
    }
}
