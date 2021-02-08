package com.nike.dnp.controller.user;

import com.nike.dnp.common.aspect.ValidField;
import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.dto.user.UserAuthSearchDTO;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.auth.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * UserContentsController
 *
 * @author [오지훈]
 * @since 2020. 7. 6. 오후 2:56:56
 * @apiNote 유저 컨텐츠 권한 Controller
 */
@Slf4j
@RestController
@Api(description = "유저 컨텐츠 권한", tags = "USER_CONTENTS")
@RequestMapping(value = "/api/user/contents", name = "유저 컨텐츠 권한")
@RequiredArgsConstructor
public class UserContentsController {

    /**
     * The Response service
     *
     * @author [오지훈]
     */
    private final ResponseService responseService;

    /**
     * AuthService
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
     * List single result.
     *
     * @param userAuthSearchDTO the user auth search dto
     * @param result            the result
     * @return the single result
     * @author [오지훈]
     * @implNote
     * @apiNote 유저 컨텐츠 그룹 권한 목록
     * @since 2020. 7. 20. 오후 4:25:57
     */
    @ApiOperation(value = "유저 컨텐츠 권한 목록1"
            , notes = OPERATION_CHARACTER)
    @PostMapping(name = "유저 컨텐츠 그룹 권한 목록1", value = "/list1"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<List<AuthReturnDTO>> list1 (
            @ApiParam(value = "유저 컨텐츠 권한 검색 DTO", required = true) @Valid @RequestBody final UserAuthSearchDTO userAuthSearchDTO
            , @ApiIgnore final BindingResult result) {
        log.info("UserContentsController.list");
        return responseService.getSingleResult(authService.getAuthList(userAuthSearchDTO));
    }

    @ApiOperation(value = "유저 컨텐츠 권한 목록2"
            , notes = OPERATION_CHARACTER)
    @PostMapping(name = "유저 컨텐츠 그룹 권한 목록2", value = "/list2"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<List<AuthReturnDTO>> list2 (
            @ApiParam(value = "유저 컨텐츠 권한 검색 DTO", required = true) @Valid @RequestBody final UserAuthSearchDTO userAuthSearchDTO
            , @ApiIgnore final BindingResult result) {
        log.info("UserContentsController.list");
        return responseService.getSingleResult(authService.getAuthListWithoutN(userAuthSearchDTO, "Y"));
    }

    @ApiOperation(value = "유저 컨텐츠 권한 목록3"
            , notes = OPERATION_CHARACTER)
    @PostMapping(name = "유저 컨텐츠 그룹 권한 목록3", value = "/list3"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<AuthReturnDTO>> list3 () {
        log.info("UserContentsController.list");
        return responseService.getSingleResult(authService.getAuthList3());
    }

//    /**
//     * Save single result.
//     *
//     * @param userContentsSaveDTO the user contents save dto
//     * @return the single result
//     * @author [오지훈]
//     * @since 2020. 7. 20. 오후 2:38:11
//     * @apiNote 유저 컨텐츠 권한 등록/수정
//     */
//    @ApiOperation(value = "유저 컨텐츠 권한 등록/수정"
//            , notes = OPERATION_CHARACTER)
//    @PostMapping(name = "유저 컨텐츠 권한 등록/수정", value = "/save/{contentsSeq}"
//            , consumes = {MediaType.APPLICATION_JSON_VALUE}
//            , produces = {MediaType.APPLICATION_JSON_VALUE})
//    @ValidField
//    public SingleResult<List<UserContents>> save (
//            @ApiParam(value = "컨텐츠 시퀀스", required = true) @PathVariable final Long contentsSeq
//            , @ApiParam(value = "유저 컨텐츠 권한 저장 DTO", required = true) @Valid @RequestBody final UserContentsSaveDTO userContentsSaveDTO
//            , @ApiIgnore final BindingResult result) {
//        log.info("UserContentsController.save");
//        return responseService.getSingleResult(
//                userContentsService.save(contentsSeq, userContentsSaveDTO)
//                , ServiceCode.ReturnTypeEnumCode.CREATE.toString()
//                , ServiceCode.ReturnTypeEnumCode.CREATE.getMessage()
//                , true
//        );
//    }
}
