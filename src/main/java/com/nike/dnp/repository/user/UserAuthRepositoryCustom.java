package com.nike.dnp.repository.user;

import com.nike.dnp.entity.user.UserAuth;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserRepositoryCustom
 *
 * @author [오지훈]
 * @since 2020. 6. 22. 오후 3:22:56
 * @implNote User(유저) Repository Custom Interface 작성
 */
@Repository
public interface UserAuthRepositoryCustom {

    List<UserAuth> findAllByAuthSeqNormal(Long authSeq);

}
