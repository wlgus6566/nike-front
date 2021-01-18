package com.nike.dnp.repository.user;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.entity.order.OrderEntity;
import com.nike.dnp.entity.user.QUser;
import com.nike.dnp.entity.user.QUserAuth;
import com.nike.dnp.entity.user.UserAuth;
import com.nike.dnp.repository.order.OrderPredicateHelper;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserRepositoryImpl
 *
 * @author [오지훈]
 * @implNote User(유저) Repository interface 작성
 * @since 2020. 6. 22. 오전 11:47:54
 */
@Repository
public class UserAuthRepositoryImpl extends QuerydslRepositorySupport implements UserAuthRepositoryCustom {

    /**
     * Instantiates a new User repository.
     *
     * @author [오지훈]
     * @implNote 주입
     * @since 2020. 6. 22. 오전 11:47:54
     */
    public UserAuthRepositoryImpl() {
        super(UserAuth.class);
    }

    /**
     * Find all by auth seq normal list.
     *
     * @param authSeq the auth seq
     * @return the list
     * @author [이소정]
     * @implNote 권한SEQ 일치 하는 Normal 상세 사용자 목록
     * @since 2021. 1. 19. 오전 12:33:28
     */
    @Override
    public List<UserAuth> findAllByAuthSeqNormal(Long authSeq) {
        final QUser qUser = QUser.user;
        final QUserAuth qUserAuth = QUserAuth.userAuth;

        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        return queryFactory.selectFrom(qUserAuth)
                .innerJoin(qUser).on(qUserAuth.userSeq.eq(qUser.userSeq))
                .where(qUser.userStatusCode.ne(ServiceCode.UserStatusEnumCode.DELETE.toString()),
                        qUserAuth.authSeq.eq(authSeq))
                .fetch();
    }
}
