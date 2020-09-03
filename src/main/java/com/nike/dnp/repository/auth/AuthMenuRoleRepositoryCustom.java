package com.nike.dnp.repository.auth;

import com.nike.dnp.entity.menu.MenuRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AuthMenuRoleRepositoryCustom
 *
 * @author [오지훈]
 * @since 2020. 7. 7. 오후 5:02:48
 * @implNote Auth(권한) Repository Custom Interface 작성
 */
@Repository
public interface AuthMenuRoleRepositoryCustom {

    /**
     * Find by auth menu role join menu role list.
     *
     * @param authSeq the auth seq
     * @param menuSeq the menu seq
     * @return the list
     * @author [오지훈]
     * @implNote 특정 메뉴에 본인 그룹이 부여받은 권한 조회
     * @since 2020. 9. 3. 오후 4:39:08
     */
    List<MenuRole> findByAuthMenuRoleJoinMenuRole(final Long authSeq, final Long menuSeq);

}
