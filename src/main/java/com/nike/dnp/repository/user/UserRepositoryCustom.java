package com.nike.dnp.repository.user;

import com.nike.dnp.dto.user.UserDTO;
import com.nike.dnp.dto.user.UserListDTO;
import com.nike.dnp.dto.user.UserSearchDTO;
import com.nike.dnp.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * UserRepositoryCustom
 *
 * @author [오지훈]
 * @implNote User(유저) Repository Custom Interface 작성
 * @since 2020. 6. 22. 오후 3:22:56
 */
public interface UserRepositoryCustom {

    /**
     * Find pages page.
     *
     * @param userSearchDTO the user search dto
     * @param pageRequest   the page request
     * @return the page
     * @author [오지훈]
     * @implNote 조회(페이징) 설명]
     * @since 2020. 6. 22. 오후 3:20:59
     */
    Page<UserListDTO> findPages(final UserSearchDTO userSearchDTO, final PageRequest pageRequest);

    /**
     * Count by pasword change period int.
     *
     * @param userSeq the user seq
     * @return the int
     * @author [오지훈]
     * @implNote 90일 지난 패스워드 체크
     * @since 2020. 7. 2. 오후 12:18:05
     */
    long countByPasswordChangePeriod(final Long userSeq);

    /**
     * Find by password change configure list.
     *
     * @param days the days
     * @return the list
     * @author [오지훈]
     * @implNote 패스워드 변경일 90일 [days]일 전 유저 목록
     * @since 2020. 10. 5. 오후 12:11:39
     */
    List<User> findByPasswordChangeConfigure(final int days);

    /**
     * Find by dormant prev configure list.
     *
     * @param days the days
     * @return the list
     * @author [오지훈]
     * @implNote 휴면회원 예정 [days]일 전 유저 목록
     * @since 2020. 10. 13. 오전 11:38:20
     */
    List<User> findByDormantPrevConfigure(final int days);

    /**
     * Find by dormant delete list.
     *
     * @return the list
     * @author [오지훈]
     * @implNote 휴면회원 삭제 예정 목록
     * @since 2020. 10. 13. 오후 1:29:50
     */
    List<User> findByDormantDelete();

    /**
     * Find all by auth seq list.
     *
     * @param authSeq the auth seq
     * @return the list
     * @author [이소정]
     * @implNote authSeq 에 맞는 사용자 목록 조회
     * @since 2021. 1. 5. 오후 4:37:37
     */
    List<UserDTO> findAllByAuthSeq(Long authSeq);

}
