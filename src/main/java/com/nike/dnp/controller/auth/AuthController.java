package com.nike.dnp.controller.auth;

import com.nike.dnp.dto.auth.AuthSaveDTO;
import com.nike.dnp.dto.auth.AuthUpdateDTO;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.auth.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
     * @param authUserDTO the auth user dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 3:37:10
     * @Description 그룹(권한) 목록 조회
     */
    @ApiOperation(
            value = "그룹(권한) 목록 조회"
            , notes = BASIC_OPERATION
    )
    @GetMapping(name = "그룹(권한) 목록 조회"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<Auth>> findAll(
            final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        log.info("AuthController.findAll");
        return responseService.getSingleResult(authService.findAll());
    }

    /**
     * Find auth single result.
     *
     * @param authSeq     the auth seq
     * @param authUserDTO the auth user dto
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
            @PathVariable Long authSeq
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        log.info("AuthController.findAuth");
        return responseService.getSingleResult(authService.findById(authSeq));
    }

    /**
     * Save single result.
     *
     * @param authSaveDTO the auth save dto
     * @param authUserDTO the auth user dto
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
            final AuthSaveDTO authSaveDTO
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        log.info("AuthController.save");

        //TODO[ojh] 메뉴별 권한 설정 추가 예정

        return responseService.getSingleResult(authService.save(authSaveDTO, authUserDTO));
    }

    /**
     * Update single result.
     *
     * @param authSeq       the auth seq
     * @param authUpdateDTO the auth update dto
     * @param authUserDTO   the auth user dto
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
            @PathVariable final Long authSeq
            , final AuthUpdateDTO authUpdateDTO
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        log.info("AuthController.update");

        //TODO[ojh] 메뉴별 권한 설정 추가 예정

        return responseService.getSingleResult(authService.update(authSeq, authUpdateDTO, authUserDTO));
    }

    /**
     * Delete single result.
     *
     * @param authSeq     the auth seq
     * @param authUserDTO the auth user dto
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
            @PathVariable final Long authSeq
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        log.info("AuthController.delete");
        return responseService.getSingleResult(authService.delete(authSeq, authUserDTO));
    }

}
