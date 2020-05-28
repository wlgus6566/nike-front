package com.nike.dnp.controller.manager;

import com.nike.dnp.dto.manager.ManagerDTO;
import com.nike.dnp.entity.manager.Manager;
import com.nike.dnp.service.manager.ManagerService;
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
 * @Description 관리자 컨트롤러 작성
 * @history [오지훈] [2020.05.27] [최초 작성]
 *
 */

@Slf4j
@RestController
@RequestMapping("/manager")
public class ManagerController {

    /**
     * 관리자 서비스
     */
    private final ManagerService managerService;

    /**
     * Instantiates a new Manager controller.
     *
     * @param managerService the manager service
     */
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    /**
     * 관리자 전체목록 조회
     *
     * @return all managers
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Manager>> getAllManagers() {
        try {
            List<Manager> managers = managerService.findAll();
            return new ResponseEntity<>(managers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 관리자 상세정보 조회
     *
     * @param managerSeq the manager seq
     * @return the manager
     */
    @GetMapping(value = "/{managerSeq}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Manager> getManager(@PathVariable("managerSeq") Long managerSeq) {
        try {
            Optional<Manager> manager = managerService.findById(managerSeq);
            return new ResponseEntity<>(manager.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 관리자 삭제
     *
     * @param managerSeq the manager seq
     * @return the response entity
     */
    @DeleteMapping(value = "/{managerSeq}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> deleteManager(@PathVariable("managerSeq") Long managerSeq) {
        managerService.delete(managerSeq);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 관리자 수정
     *
     * @param managerSeq the manager seq
     * @param managerDTO the manager dto
     * @return the response entity
     */
    @PutMapping(value = "/{managerSeq}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Manager> updateManager(@PathVariable("managerSeq") Long managerSeq, ManagerDTO managerDTO) {
        managerService.update(managerSeq, managerDTO);
        return new ResponseEntity(managerSeq, HttpStatus.OK);
    }

    /**
     * 관리자 등록
     *
     * @param managerDTO the manager dto
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<Manager> save(ManagerDTO managerDTO) {
        return new ResponseEntity(managerService.save(managerDTO), HttpStatus.OK);
    }

}
