package com.nike.dnp.repository.user;

import com.nike.dnp.dto.user.UserSearchDTO;
import com.nike.dnp.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * UserRepositoryCustom
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 3:22:56
 * @Description User(유저) Repository Custom Interface 작성
 */
public interface UserRepositoryCustom {

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
    Page<User> findPages(final UserSearchDTO userSearchDTO, final PageRequest pageRequest);

    /**
     * Count by pasword change period int.
     *
     * @param userSeq the user seq
     * @return the int
     * @author [오지훈]
     * @CreatedOn 2020. 7. 2. 오후 12:18:05
     * @Description 90일 지난 패스워드 체크
     */
    long countByPaswordChangePeriod(final Long userSeq);
}
