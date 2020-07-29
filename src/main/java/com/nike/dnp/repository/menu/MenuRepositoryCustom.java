package com.nike.dnp.repository.menu;

import com.nike.dnp.dto.menu.MenuReturnDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Interface Menu repository custom.
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:51:23
 * @implNote Menu(메뉴) Repository Custom Interface 작성
 */
@Repository
public interface MenuRepositoryCustom {

    /**
     * Gets menus.
     *
     * @param authSeq the auth seq
     * @return the menus
     * @author [오지훈]
     * @since 2020. 7. 8. 오후 6:18:06
     * @implNote 메뉴 목록(권한)
     */
    List<MenuReturnDTO> getMenus(final Long authSeq);



}
