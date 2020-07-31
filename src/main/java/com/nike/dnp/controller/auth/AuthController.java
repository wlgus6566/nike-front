package com.nike.dnp.controller.auth;

import com.nike.dnp.common.aspect.ValidField;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.auth.AuthSaveDTO;
import com.nike.dnp.dto.auth.AuthUpdateDTO;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.entity.auth.AuthMenuRole;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.auth.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;


/**
 * The Class Auth controller.
 *
 * @author [오지훈]
 * @since 2020. 6. 23. 오후 5:22:14
 * @apiNote
 */
@Slf4j
@RestController
@Api(description = "그룹(권한)", tags = "AUTH")
@RequestMapping(value = "/api/auth", name = "그룹(권한)")
@RequiredArgsConstructor
public class AuthController {

    /**
     * The Response service
     *
     * @author [오지훈]
     */
    private final ResponseService responseService;

    /**
     * The Auth service
     *
     * @author [오지훈]
     */
    private final AuthService authService;

    /**
     * OPERATION_CHARACTER
     *
     * @author [오지훈]
     */
    private static final String OPERATION_CHARACTER
            = "## Request ##\n[하위 Parameters 참조]\n\n\n\n## Response ##\n[하위 Model 참조]\n\n\n\n";

    /**
     * Find all single result.
     *
     * @return the single result
     * @author [오지훈]
     * @since 2020. 6. 23. 오후 3:37:10
     * @apiNote 그룹(권한) 목록 조회(캐시)
     */
    @ApiOperation(value = "그룹(권한) 목록 조회(캐시)"
            , notes = OPERATION_CHARACTER)
    @GetMapping(name = "그룹(권한) 목록 조회(캐시)", value = "/list"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<JSONArray> findAllByCache () {
        log.info("AuthController.findAllByCache");
        return responseService.getSingleResult(authService.findAllByCache());
    }

    /**
     * Find all single result.
     *
     * @return the single result
     * @author [오지훈]
     * @since 2020. 7. 7. 오후 3:37:35
     * @apiNote 그룹(권한) 관리 목록 조회
     */
    @ApiOperation(value = "그룹(권한) 관리 목록 조회"
            , notes = OPERATION_CHARACTER)
    @GetMapping(name = "그룹(권한) 목록 조회"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<Auth>> findAll () {
        log.info("AuthController.findAll");
        return responseService.getSingleResult(authService.findAll());
    }

    /**
     * Find auth menu role single result.
     *
     * @param authSeq the auth seq
     * @return the single result
     * @author [오지훈]
     * @since 2020. 7. 13. 오전 10:06:37
     * @apiNote 그룹(그룹 및 메뉴권한) 체크 목록
     */
    @ApiOperation(value = "그룹(그룹 및 메뉴권한) 체크 목록"
            , notes = OPERATION_CHARACTER)
    @GetMapping(name = "그룹(그룹 및 메뉴권한) 체크 목록", value = "/role/{authSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<AuthMenuRole>> findAuthMenuRole (
            @ApiParam(value = "권한(그룹) 시퀀스", required = true) @PathVariable final Long authSeq) {
        log.info("AuthController.findAuthMenuRole");
        return responseService.getSingleResult(authService.findAuthMenuRole(authSeq));
    }

    /**
     * Find auth single result.
     *
     * @param authSeq the auth seq
     * @return the single result
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:45:54
     * @apiNote 그룹(권한) 상세 조회
     */
    @ApiOperation(value = "그룹(권한) 상세 조회"
            , notes = OPERATION_CHARACTER)
    @GetMapping(name = "그룹(권한) 상세 조회"
            , value = "/{authSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Auth> findAuth (
            @ApiParam(value = "권한(그룹) 시퀀스", required = true) @PathVariable final Long authSeq) {
        log.info("AuthController.findAuth");
        return responseService.getSingleResult(authService.getById(authSeq));
    }

    /**
     * Save single result.
     *
     * @param authSaveDTO the auth save dto
     * @return the single result
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:36:41
     * @apiNote 그룹(권한) 정보 등록
     */
    @ApiOperation(value = "그룹(권한) 정보 등록"
            , notes = OPERATION_CHARACTER)
    @PostMapping(name = "그룹(권한) 정보 등록"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<Auth> save(
            @ApiParam(value = "권한(그룹) 등록 DTO", name = "authSaveDTO") @Valid @RequestBody final AuthSaveDTO authSaveDTO
            , @ApiIgnore final BindingResult result) {
        log.info("AuthController.save");
        return responseService.getSingleResult(
                authService.save(authSaveDTO)
                , ServiceCode.ReturnTypeEnumCode.CREATE.toString()
                , ServiceCode.ReturnTypeEnumCode.CREATE.getMessage()
                , true
        );
    }

    /**
     * Update single result.
     *
     * @param authSeq       the auth seq
     * @param authUpdateDTO the auth update dto
     * @param result        the result
     * @return the single result
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:30:14
     * @apiNote 그룹(권한) 정보 수정
     */
    @ApiOperation(value = "그룹(권한) 정보 수정"
            , notes = OPERATION_CHARACTER)
    @PutMapping(name = "그룹(권한) 정보 수정", value = "/{authSeq}"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<Auth> update (
            @ApiParam(value = "권한(그룹) 시퀀스", name = "authSeq") @PathVariable final Long authSeq
            , @ApiParam(value = "권한(그룹) 수정 DTO", name = "authUpdateDTO") @Valid @RequestBody final AuthUpdateDTO authUpdateDTO
            , @ApiIgnore final BindingResult result) {
        log.info("AuthController.update");
        return responseService.getSingleResult(
                authService.update(authSeq, authUpdateDTO)
                , ServiceCode.ReturnTypeEnumCode.UPDATE.toString()
                , ServiceCode.ReturnTypeEnumCode.UPDATE.getMessage()
                , true
        );
    }

    /**
     * Delete single result.
     *
     * @param authSeq the auth seq
     * @return the single result
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:38:30
     * @apiNote 그룹(권한) 정보 삭제
     */
    @ApiOperation(value = "그룹(권한) 정보 삭제"
            , notes = OPERATION_CHARACTER)
    @DeleteMapping(name = "그룹(권한) 정보 삭제"
            , value = "/{authSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Auth> delete (
            @ApiParam(value = "권한(그룹) 시퀀스", required = true) @PathVariable final Long authSeq) {
        log.info("AuthController.delete");
        return responseService.getSingleResult(
                authService.delete(authSeq)
                , ServiceCode.ReturnTypeEnumCode.DELETE.toString()
                , ServiceCode.ReturnTypeEnumCode.DELETE.getMessage()
                , true
        );
    }

}
