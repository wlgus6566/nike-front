package com.nike.dnp.controller.auth;

import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.auth.AuthSaveDTO;
import com.nike.dnp.dto.auth.AuthUpdateDTO;
import com.nike.dnp.entity.auth.Auth;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * The Class Auth controller.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 23. 오후 5:22:14
 * @Description
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
     * The Basic operation
     *
     * @author [오지훈]
     */
    private final String BASIC_OPERATION = "## Reqeust ##\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n";

    /**
     * Find all single result.
     *
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 3:37:10
     * @Description 그룹(권한) 목록 조회(캐시)
     */
    @ApiOperation(
            value = "그룹(권한) 목록 조회(캐시)"
            , notes = BASIC_OPERATION
    )
    @GetMapping(name = "그룹(권한) 목록 조회(캐시)", value = "/list"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<JSONArray> findAllByCache() {
        log.info("AuthController.findAllByCache");
        return responseService.getSingleResult(authService.findAllByCache());
    }

    /**
     * Find all single result.
     *
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 7. 7. 오후 3:37:35
     * @Description 그룹(권한) 관리 목록 조회
     */
    @ApiOperation(
            value = "그룹(권한) 관리 목록 조회"
            , notes = BASIC_OPERATION
    )
    @GetMapping(name = "그룹(권한) 목록 조회"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<Auth>> findAll() {
        log.info("AuthController.findAll");
        return responseService.getSingleResult(authService.findAll());
    }

    /**
     * Find auth single result.
     *
     * @param authSeq the auth seq
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:45:54
     * @Description 그룹(권한) 상세 조회
     */
    @ApiOperation(
            value = "그룹(권한) 상세 조회"
            , notes = BASIC_OPERATION
    )
    @GetMapping(name = "그룹(권한) 상세 조회"
            , value = "/{authSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Optional<Auth>> findAuth(
            @ApiParam(value = "권한(그룹) 시퀀스", required = true)
            @PathVariable final Long authSeq
    ) {
        log.info("AuthController.findAuth");
        return responseService.getSingleResult(authService.findById(authSeq));
    }

    /**
     * Save single result.
     *
     * @param authSaveDTO the auth save dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:36:41
     * @Description 그룹(권한) 정보 등록
     */
    @ApiOperation(
            value = "그룹(권한) 정보 등록"
            , notes = BASIC_OPERATION
    )
    @PostMapping(name = "그룹(권한) 정보 등록"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Auth> save(
            @ApiParam(value = "권한(그룹) 등록 DTO", required = true)
            @RequestBody final AuthSaveDTO authSaveDTO
    ) {
        log.info("AuthController.save");

        //TODO[ojh] 메뉴별 권한 설정 추가 예정
        Auth auth = authService.save(authSaveDTO);

        return responseService.getSingleResult(
                auth
                , ServiceEnumCode.ReturnTypeEnumCode.CREATE.toString()
                , ServiceEnumCode.ReturnTypeEnumCode.CREATE.getMessage()
                , true
        );
    }

    /**
     * Update single result.
     *
     * @param authSeq       the auth seq
     * @param authUpdateDTO the auth update dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:30:14
     * @Description 그룹(권한) 정보 수정
     */
    @ApiOperation(
            value = "그룹(권한) 정보 수정"
            , notes = BASIC_OPERATION
    )
    @PutMapping(name = "그룹(권한) 정보 수정"
            , value = "/{authSeq}"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Optional<Auth>> update(
            @ApiParam(value = "권한(그룹) 시퀀스", required = true)
            @PathVariable final Long authSeq
            , @ApiParam(value = "권한(그룹) 수정 DTO", required = true)
            @RequestBody final AuthUpdateDTO authUpdateDTO
    ) {
        log.info("AuthController.update");

        //TODO[ojh] 메뉴별 권한 설정 추가 예정

        return responseService.getSingleResult(
                authService.update(authSeq, authUpdateDTO)
                , ServiceEnumCode.ReturnTypeEnumCode.UPDATE.toString()
                , ServiceEnumCode.ReturnTypeEnumCode.UPDATE.getMessage()
                , true
        );
    }

    /**
     * Delete single result.
     *
     * @param authSeq the auth seq
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:38:30
     * @Description 그룹(권한) 정보 삭제
     */
    @ApiOperation(
            value = "그룹(권한) 정보 삭제"
            , notes = BASIC_OPERATION
    )
    @DeleteMapping(name = "그룹(권한) 정보 삭제"
            , value = "/{authSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Optional<Auth>> delete(
            @ApiParam(value = "권한(그룹) 시퀀스", required = true)
            @PathVariable final Long authSeq
    ) {
        log.info("AuthController.delete");
        return responseService.getSingleResult(
                authService.delete(authSeq)
                , ServiceEnumCode.ReturnTypeEnumCode.DELETE.toString()
                , ServiceEnumCode.ReturnTypeEnumCode.DELETE.getMessage()
                , true
        );
    }

}
