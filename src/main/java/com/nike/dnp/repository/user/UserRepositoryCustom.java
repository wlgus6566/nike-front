package com.nike.dnp.repository.user;

import com.nike.dnp.dto.user.UserSearchDTO;
import com.nike.dnp.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * UserRepositoryCustom
 *
 * @author [오지훈]
 * @Description User(유저) Repository Custom Interface 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Repository
public interface UserRepositoryCustom {

    /**
     * 조회(페이징)
     *
     * @param userSearchDTO the user search dto
     * @param pageRequest   the page request
     * @return the page
     * @author [오지훈]
     */
    Page<User> findPages(final UserSearchDTO userSearchDTO, final PageRequest pageRequest);

}
