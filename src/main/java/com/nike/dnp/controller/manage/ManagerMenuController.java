package com.nike.dnp.controller.manage;

import com.nike.dnp.dto.manage.ManagerMenuDTO;
import com.nike.dnp.entity.manage.Manager;
import com.nike.dnp.entity.manage.ManagerMenu;
import com.nike.dnp.service.manage.ManagerMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * ManagerController
 *
 * @since 2020.05.27
 * @author [오지훈]
 * @Description 관리메뉴 컨트롤러 작성
 * @history [오지훈] [2020.05.27] [최초 작성]
 *
 */

@Slf4j
@RestController
@RequestMapping("/manage/menu")
public class ManagerMenuController {

    /**
     * 관리메뉴 서비스
     */
    private final ManagerMenuService managerMenuService;

    /**
     * Instantiates a new Manager controller.
     *
     * @param managerMenuService the manager menu service
     */
    public ManagerMenuController(ManagerMenuService managerMenuService) {
        this.managerMenuService = managerMenuService;
    }

    /**
     * 관리메뉴 전체목록 조회
     *
     * @return all managers
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ManagerMenu>> getAllManagerMenus() {
        try {
            List<ManagerMenu> managerMenus = managerMenuService.findAll();
            return new ResponseEntity<>(managerMenus, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 관리메뉴 상세정보 조회
     *
     * @param menuSeq the manager seq
     * @return the manager
     */
    @GetMapping(value = "/{menuSeq}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ManagerMenu> getManagerMenu(@PathVariable("menuSeq") Long menuSeq) {
        try {
            Optional<ManagerMenu> managerMenu = managerMenuService.findById(menuSeq);
            return new ResponseEntity<>(managerMenu.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 관리메뉴 삭제
     *
     * @param menuSeq the manager seq
     * @return the response entity
     */
    @DeleteMapping(value = "/{menuSeq}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> deleteManagerMenu(@PathVariable("menuSeq") Long menuSeq) {
        managerMenuService.delete(menuSeq);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 관리메뉴 수정
     *
     * @param menuSeq        the manager seq
     * @param managerMenuDTO the manager menu dto
     * @return the response entity
     */
    @PutMapping(value = "/{menuSeq}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Manager> updateManagerMenu(@PathVariable("menuSeq") Long menuSeq, ManagerMenuDTO managerMenuDTO) {
        managerMenuService.update(menuSeq, managerMenuDTO);
        return new ResponseEntity(menuSeq, HttpStatus.OK);
    }

    /**
     * 관리메뉴 등록
     *
     * @param managerMenuDTO the manager menu dto
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<Manager> insertManagerMenu(ManagerMenuDTO managerMenuDTO) {
        Long menuSeq = managerMenuService.save(managerMenuDTO);

        return new ResponseEntity(menuSeq, HttpStatus.OK);
    }

}
