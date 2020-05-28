package com.nike.dnp.controller.manage;

import com.nike.dnp.dto.manage.ManagerAuthDTO;
import com.nike.dnp.entity.manage.Manager;
import com.nike.dnp.entity.manage.ManagerAuth;
import com.nike.dnp.service.manage.ManagerAuthService;
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
 * @Description 관리권한 컨트롤러 작성
 * @history [오지훈] [2020.05.27] [최초 작성]
 *
 */

@Slf4j
@RestController
@RequestMapping("/manage/auth")
public class ManagerAuthController {

    /**
     * 관리권한 서비스
     */
    private final ManagerAuthService managerAuthService;

    /**
     * Instantiates a new Manager controller.
     *
     * @param managerAuthService the manager auth service
     */
    public ManagerAuthController(ManagerAuthService managerAuthService) {
        this.managerAuthService = managerAuthService;
    }

    /**
     * 관리권한 전체목록 조회
     *
     * @return all managers
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ManagerAuth>> getAllManagerAuths() {
        try {
            List<ManagerAuth> managerAuths = managerAuthService.findAll();
            return new ResponseEntity<>(managerAuths, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 관리권한 상세정보 조회
     *
     * @param authSeq the auth seq
     * @return the manager
     */
    @GetMapping(value = "/{authSeq}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ManagerAuth> getManagerAuth(@PathVariable("authSeq") Long authSeq) {
        try {
            Optional<ManagerAuth> managerAuth = managerAuthService.findById(authSeq);
            return new ResponseEntity<>(managerAuth.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 관리자 삭제
     *
     * @param authSeq the auth seq
     * @return the response entity
     */
    @DeleteMapping(value = "/{authSeq}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> deleteManager(@PathVariable("authSeq") Long authSeq) {
        managerAuthService.delete(authSeq);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 관리자 수정
     *
     * @param authSeq the auth seq
     * @param authDTO the auth dto
     * @return the response entity
     */
    @PutMapping(value = "/{authSeq}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Manager> updateManager(@PathVariable("authSeq") Long authSeq, ManagerAuthDTO authDTO) {
        managerAuthService.update(authSeq, authDTO);
        return new ResponseEntity(authSeq, HttpStatus.OK);
    }

    /**
     * 관리자 등록
     *
     * @param authDTO the auth dto
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<Manager> insertManagerAuth(ManagerAuthDTO authDTO) {
        return new ResponseEntity(managerAuthService.save(authDTO), HttpStatus.OK);
    }

}
