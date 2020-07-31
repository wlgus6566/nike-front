package com.nike.dnp.repository.user;

import com.nike.dnp.entity.user.UserAuth;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * UserRepositoryImpl
 *
 * @author [오지훈]
 * @since 2020. 6. 22. 오전 11:47:54
 * @implNote User(유저) Repository interface 작성
 */
@Repository
public class UserAuthRepositoryImpl extends QuerydslRepositorySupport implements UserAuthRepositoryCustom {

    /**
     * Instantiates a new User repository.
     *
     * @author [오지훈]
     * @since 2020. 6. 22. 오전 11:47:54
     * @implNote
     */
    public UserAuthRepositoryImpl() {
        super(UserAuth.class);
    }

}
