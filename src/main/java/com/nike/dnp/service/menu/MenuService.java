package com.nike.dnp.service.menu;

import com.nike.dnp.entity.menu.Menu;
import com.nike.dnp.repository.menu.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * The Class Menu service.
 *
 * @author [오지훈]
 * @since 2020. 7. 13. 오후 1:50:23
 * @implNote Menu(메뉴) Service 작성
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    /**
     * MenuRepository
     *
     * @author [오지훈]
     */
    private final MenuRepository menuRepository;

    /**
     * Gets menus.
     *
     * @return the menus
     * @author [오지훈]
     * @since 2020. 7. 8. 오후 6:18:35
     * @implNote 메뉴 목록(전체)
     */
    public List<Menu> findAll() {
        log.info("AuthService.findAll");
        return menuRepository.findAll();
    }

    /**
     * Find manage menus list.
     *
     * @return the list
     * @author [오지훈]
     * @implNote 권한 관리 대상 메뉴 목록 조회
     * @since 2020. 8. 10. 오후 3:54:16
     */
    public List<Menu> findManageMenus() {
        log.info("MenuService.findManageMenus");
        return menuRepository.findAllByUseYnAndManagementYnOrderByMenuDepthAscMenuOrderAsc("Y", "Y");
    }

}
