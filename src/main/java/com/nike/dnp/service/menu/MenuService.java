package com.nike.dnp.service.menu;

import com.nike.dnp.dto.menu.MenuReturnDTO;
import com.nike.dnp.entity.menu.Menu;
import com.nike.dnp.repository.menu.MenuRepository;
import com.nike.dnp.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    /**
     * The Redis service
     *
     * @author [오지훈]
     */
    private final RedisService redisService;

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
    public List<MenuReturnDTO> getMenus() {
        log.info("AuthService.getMenus.all");
        return menuRepository.getMenus();
    }

    /**
     * Gets menus.
     *
     * @param authSeq the auth seq
     * @return the menus
     * @author [오지훈]
     * @CreatedOn 2020. 7. 8. 오후 6:18:34
     * @Description 메뉴 목록(권한)
     */
    public List<Menu> getMenus(final Long authSeq) {
        log.info("AuthService.getMenus.auth");
        return menuRepository.getMenus(authSeq);
    }


}
