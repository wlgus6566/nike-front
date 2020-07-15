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
 * @CreatedOn 2020. 7. 13. 오후 1:50:23
 * @Description Menu(메뉴) Service 작성
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
     * @CreatedOn 2020. 7. 8. 오후 6:18:35
     * @Description 메뉴 목록(전체)
     */
    public List<Menu> findAll() {
        log.info("AuthService.findAll");
        return menuRepository.findAll();
    }

}
