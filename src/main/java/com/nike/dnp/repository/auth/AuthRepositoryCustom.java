package com.nike.dnp.repository.auth;

import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.dto.user.UserAuthSearchDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Interface Auth repository custom.
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:51:23
 * @implNote Auth(권한) Repository Custom Interface 작성
 */
@Repository
public interface AuthRepositoryCustom {

    /**
     * Find by auth seq array list.
     *
     * @param authSeqArray the auth seq array
     * @return the list
     * @author [오지훈]
     * @since 2020. 7. 20. 오후 2:50:03
     * @implNote 권한 시퀀스 배열로 검색
     */
    List<AuthReturnDTO> findByAuthSeqArray(final Long... authSeqArray);

    /**
     * Find by config list.
     *
     * @param userAuthSearchDTO the user auth search dto
     * @return the list
     * @author [오지훈]
     * @implNote 조건 검색
     * @since 2020. 7. 20. 오후 4:26:39
     */
    List<AuthReturnDTO> findByConfig(final UserAuthSearchDTO userAuthSearchDTO);

    /**
     * Find by auth depth list.
     *
     * @param authSeq   the auth seq
     * @param authDepth the auth depth
     * @param menuCode  the menu code
     * @param skillCode the skill code
     * @return the list
     * @author [오지훈]
     * @since 2020. 7. 21. 오후 5:04:25
     * @implNote
     */
    List<AuthReturnDTO> findByAuthDepth(
            final Long authSeq
            , final Long authDepth
            , final String menuCode
            , final String skillCode);

    /**
     * Find by config for asset all list.
     *
     * @param userAuthSearchDTO the user auth search dto
     * @return the list
     * @author [이소정]
     * @implNote
     * @since 2020. 8. 31. 오후 9:51:17
     */
    List<AuthReturnDTO> findByConfigForAssetAll(final UserAuthSearchDTO userAuthSearchDTO);

}
