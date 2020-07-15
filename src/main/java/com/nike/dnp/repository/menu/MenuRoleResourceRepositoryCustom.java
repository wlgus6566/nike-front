package com.nike.dnp.repository.menu;

import com.nike.dnp.dto.menu.MenuRoleResourceReturnDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MenuRoleResourceRepositoryCustom
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 5:51:23
 * @Description 메뉴 역할 리소스 Repository Custom Interface 작성
 */
@Repository
public interface MenuRoleResourceRepositoryCustom {

    /**
     * Menu role resources list.
     *
     * @param authSeq the auth seq
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 7. 13. 오전 11:19:07
     * @Description 권한별 리소스 목록
     */
    List<MenuRoleResourceReturnDTO> getResources(final Long authSeq);

}
