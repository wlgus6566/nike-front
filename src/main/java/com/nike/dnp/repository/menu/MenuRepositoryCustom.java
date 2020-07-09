package com.nike.dnp.repository.menu;

import com.nike.dnp.dto.menu.MenuReturnDTO;
import com.nike.dnp.entity.menu.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CodeRepositoryCustom
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 5:51:23
 * @Description Code(공통 코드) Repository Custom Interface 작성
 */
@Repository
public interface MenuRepositoryCustom {

    /**
     * Gets menus.
     *
     * @return the menus
     * @author [오지훈]
     * @CreatedOn 2020. 7. 8. 오후 6:15:51
     * @Description 메뉴 목록(전체)
     */
    List<MenuReturnDTO> getMenus();

    /**
     * Gets menus.
     *
     * @param authSeq the auth seq
     * @return the menus
     * @author [오지훈]
     * @CreatedOn 2020. 7. 8. 오후 6:18:06
     * @Description 메뉴 목록(권한)
     */
    List<Menu> getMenus(final Long authSeq);

}
