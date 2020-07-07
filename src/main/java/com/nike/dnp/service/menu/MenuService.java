package com.nike.dnp.service.menu;

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
     * Find all list.
     *
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 7. 7. 오후 3:37:06
     * @Description 그룹(권한) 목록 조회
     */
    public List<Menu> findAll() {
        log.info("AuthService.findAll");
        return menuRepository.findByUseYn("Y");
    }
}
