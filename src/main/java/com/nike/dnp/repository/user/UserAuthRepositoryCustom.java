package com.nike.dnp.repository.user;

import com.nike.dnp.dto.user.UserSearchDTO;
import com.nike.dnp.entity.user.UserAuth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * UserRepositoryCustom
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 3:22:56
 * @Description User(유저) Repository Custom Interface 작성
 */
@Repository
public interface UserAuthRepositoryCustom {

    /**
     * Find pages page.
     *
     * @param userSearchDTO the user search dto
     * @param pageRequest   the page request
     * @return the page
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 3:20:59
     * @Description 조회(페이징)
     */
    Page<UserAuth> findPages(final UserSearchDTO userSearchDTO, final PageRequest pageRequest);

}