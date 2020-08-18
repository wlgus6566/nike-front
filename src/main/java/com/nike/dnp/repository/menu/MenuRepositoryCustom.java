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

    /**
     * Gets menus.
     *
     * @param authSeq the auth seq
     * @return the menus
     * @author [오지훈]
     * @since 2020. 7. 8. 오후 6:18:06
     * @implNote 상위 메뉴 목록(GNB)
     */
    List<MenuReturnDTO> getUpperMenus(final Long authSeq);

    /**
     * Gets lower menus.
     *
     * @param authSeq   the auth seq
     * @param menuSeq   the menu seq
     * @param menuDepth the menu depth
     * @return the lower menus
     * @author [오지훈]
     * @implNote 권한 관리 중인 하위 메뉴 목록(GNB)
     * @since 2020. 8. 10. 오후 5:33:21
     */
    List<MenuReturnDTO> getLowerMenus(final Long authSeq, final Long menuSeq, final Long menuDepth);

    /**
     * Gets sub menus.
     *
     * @param menuSeq   the menu seq
     * @param menuDepth the menu depth
     * @return the sub menus
     * @author [오지훈]
     * @implNote 권한 관리 하지않는 하위 메뉴 목록(GNB)
     * @since 2020. 8. 10. 오후 5:33:22
     */
    List<MenuReturnDTO> getSubMenus(final Long menuSeq, final Long menuDepth);

}
