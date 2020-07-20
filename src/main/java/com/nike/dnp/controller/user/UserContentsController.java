package com.nike.dnp.controller.user;

import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.dto.user.UserContentsSaveDTO;
import com.nike.dnp.dto.user.UserContentsSearchDTO;
import com.nike.dnp.entity.user.UserContents;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.user.UserContentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * UserContentsController
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 6. 오후 2:56:56
 * @Description 유저 컨텐츠 권한 Controller
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
     * UserContentsService
     *
     * @author [오지훈]
     */
    private final UserContentsService userContentsService;

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
     * @param userContentsSearchDTO the user contents search dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 7. 20. 오후 4:25:57
     * @Description 유저 컨텐츠 그룹 권한 목록
     */
    @ApiOperation(
            value = "유저 컨텐츠 권한 목록"
            , notes = OPERATION_CHARACTER
    )
    @PostMapping(name = "유저 컨텐츠 그룹 권한 목록"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<AuthReturnDTO>> list (
            @ApiParam(value = "유저 컨텐츠 권한 검색 DTO", required = true) @RequestBody final UserContentsSearchDTO userContentsSearchDTO
    ) {
        log.info("UserContentsController.list");
        return responseService.getSingleResult(userContentsService.getAuthList(userContentsSearchDTO));
    }

    /**
     * Save single result.
     *
     * @param userContentsSaveDTO the user contents save dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 7. 20. 오후 2:38:11
     * @Description 유저 컨텐츠 권한 등록/수정
     */
    @ApiOperation(
            value = "유저 컨텐츠 권한 등록/수정"
            , notes = OPERATION_CHARACTER
    )
    @PostMapping(name = "유저 컨텐츠 권한 등록/수정", value = "/save"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<UserContents>> save (
            @ApiParam(value = "유저 컨텐츠 권한 저장 DTO", required = true) @RequestBody final UserContentsSaveDTO userContentsSaveDTO
    ) {
        log.info("UserContentsController.save");
        return responseService.getSingleResult(
                userContentsService.save(userContentsSaveDTO)
                , ServiceEnumCode.ReturnTypeEnumCode.CREATE.toString()
                , ServiceEnumCode.ReturnTypeEnumCode.CREATE.getMessage()
                , true
        );
    }
}
