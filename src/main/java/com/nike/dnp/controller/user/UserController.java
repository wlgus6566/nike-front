package com.nike.dnp.controller.user;

import com.nike.dnp.common.aspect.ValidField;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.common.variable.SuccessCode;
import com.nike.dnp.dto.user.*;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.user.UserService;
import com.nike.dnp.util.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * The Class User controller.
 *
 * @author [오지훈]
 * @since 2020. 6. 22. 오후 4:41:44
 * @apiNote
 */
@Slf4j
@RestController
@Api(description = "유저", tags = "USER")
@RequestMapping(value = "/api/user", name = "유저")
@RequiredArgsConstructor
public class UserController {

    /**
     * The Response service
     *
     * @author [오지훈]
     */
    private final ResponseService responseService;

    /**
     * The User service
     *
     * @author [오지훈]
     */
    private final UserService userService;

    /**
     * OPERATION_CHARACTER
     *
     * @author [오지훈]
     */
    private static final String OPERATION_CHARACTER
            = "## Request ##\n[하위 Parameters 참조]\n\n\n\n## Response ##\n[하위 Model 참조]\n\n\n\n";

    /**
     * Find pages single result.
     *
     * @param userSearchDTO the user search dto
     * @return the single result
     * @author [오지훈]
     * @since 2020. 6. 23. 오후 3:37:10
     * @apiNote 유저 목록 조회
     */
    @ApiOperation(value = "유저 목록 조회"
            , notes = "## Reqeust ##\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Public/Paging Response ## \n"
            + "필드명||필드설명|데이터 타입(길이)\n" + "-|-|-|-\n"
            + "content||본문내용|Array\n"
            + "totalPages||총페이지수|Integer\n"
            + "totalElements||총데이터수|Integer\n"
            + "first||첫페이지여부|Boolean\n"
            + "last||마지막페이지여부|Boolean\n"
            + "empty||빈값여부|Boolean\n"
            + "number||현재페이지|Integer\n"
            + "size||노출갯수|Integer\n\n\n\n")
    @GetMapping(name = "유저 목록 조회"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Page<UserResultDTO>> getUsers (final UserSearchDTO userSearchDTO) {
        log.info("UserController.getUsers");
        return responseService.getSingleResult(userService.findPages(userSearchDTO));
    }

    /**
     * Find user single result.
     *
     * @param userSeq the user seq
     * @return the single result
     * @author [오지훈]
     * @since 2020. 6. 23. 오후 5:19:29
     * @apiNote 유저 상세 조회
     */
    @ApiOperation(value = "유저 상세 조회"
            , notes = OPERATION_CHARACTER)
    @GetMapping(name = "유저 상세 조회", value = "/{userSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<UserResultDTO> getUser (
            @ApiParam(value = "유저 시퀀스", required = true) @PathVariable final Long userSeq) {
        log.info("UserController.getUser");
        return responseService.getSingleResult(userService.getUser(userSeq));
    }

    /**
     * Save single result.
     *
     * @param userSaveDTO the user save dto
     * @return the single result
     * @author [오지훈]
     * @since 2020. 6. 23. 오후 5:33:44
     * @apiNote 유저 등록
     */
    @ApiOperation(value = "유저 등록"
            , notes = OPERATION_CHARACTER)
    @PostMapping(name = "유저 등록"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<UserResultDTO> save (
            @ApiParam(value = "유저 저장 DTO", required = true) @Valid @RequestBody final UserSaveDTO userSaveDTO
            , @ApiIgnore final BindingResult result) {
        log.info("UserController.save");
        return responseService.getSingleResult(userService.save(userSaveDTO)
                , ServiceCode.ReturnTypeEnumCode.CREATE.name()
                , MessageUtil.getMessage(ServiceCode.ReturnTypeEnumCode.CREATE.name())
                , true
        );
    }

    /**
     * Update single result.
     *
     * @param userSeq       the user seq
     * @param userUpdateDTO the user update dto
     * @return the single result
     * @author [오지훈]
     * @since 2020. 6. 23. 오후 5:31:41
     * @apiNote 유저 상세 수정
     */
    @ApiOperation(value = "유저 상세 수정" + "\n"
            , notes = OPERATION_CHARACTER)
    @PutMapping(name = "유저 상세 수정", value = "/{userSeq}"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<UserResultDTO> update (
            @ApiParam(value = "유저 시퀀스", required = true) @PathVariable final Long userSeq
            , @ApiParam(value = "유저 수정 DTO", required = true) @Valid @RequestBody final UserUpdateDTO userUpdateDTO
            , @ApiIgnore final BindingResult result) {
        log.info("UserController.update");
        return responseService.getSingleResult(userService.update(userSeq, userUpdateDTO)
                , ServiceCode.ReturnTypeEnumCode.UPDATE.name()
                , MessageUtil.getMessage(ServiceCode.ReturnTypeEnumCode.UPDATE.name())
                , true
        );
    }

    /**
     * Delete single result.
     *
     * @param userSeq the user seq
     * @return the single result
     * @author [오지훈]
     * @since 2020. 6. 23. 오후 5:47:53
     * @apiNote 유저 단건 삭제
     */
    @ApiOperation(value = "유저 단건 삭제" + "\n"
            , notes = OPERATION_CHARACTER)
    @DeleteMapping(name = "유저 단건 삭제", value = "/{userSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<UserResultDTO> deleteOne (
            @ApiParam(value = "유저 시퀀스", required = true) @PathVariable final Long userSeq) {
        log.info("UserController.deleteOne");
        return responseService.getSingleResult(userService.deleteOne(userSeq)
                , ServiceCode.ReturnTypeEnumCode.DELETE.toString()
                , ServiceCode.ReturnTypeEnumCode.DELETE.getMessage()
                , true);
    }

    /**
     * Delete array single result.
     *
     * @param userDeleteDTO the user delete dto
     * @return the single result
     * @author [오지훈]
     * @since 2020. 6. 23. 오후 6:01:16
     * @apiNote 유저 배열 삭제
     */
    @ApiOperation(value = "유저 배열 삭제"
            , notes = OPERATION_CHARACTER)
    @DeleteMapping(name = "유저 배열 삭제"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<List<Long>> deleteArray (
            @ApiParam(value = "유저 삭제 DTO", required = true) @Valid @RequestBody final UserDeleteDTO userDeleteDTO) {
        log.info("UserController.deleteArray");
        return responseService.getSingleResult(userService.deleteArray(userDeleteDTO)
                , ServiceCode.ReturnTypeEnumCode.DELETE.toString()
                , ServiceCode.ReturnTypeEnumCode.DELETE.getMessage()
                , true);
    }

    /**
     * Check id single result.
     *
     * @param userIdDTO the user id dto
     * @return the single result
     * @author [오지훈]
     * @since 2020. 7. 1. 오후 2:13:24
     * @apiNote ID 중복 체크
     */
    @ApiOperation(value = "ID 중복 체크"
            , notes = OPERATION_CHARACTER)
    @GetMapping(name = "ID 중복 체크", value = "/duplicate"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<Integer> checkId (
            @ModelAttribute @Valid final UserIdDTO userIdDTO
            , @ApiIgnore final BindingResult result) {
        log.info("UserController.checkId");
        return responseService.getSingleResult(userService.checkId(userIdDTO.getUserId())
                , SuccessCode.ConfigureSuccess.NOT_DUPLICATE.name()
                , MessageUtil.getMessage(SuccessCode.ConfigureSuccess.NOT_DUPLICATE.name())
                , true
        );
    }

}
