package com.nike.dnp.controller.user;

import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.common.variable.SuccessEnumCode;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.user.UserCertDTO;
import com.nike.dnp.dto.user.UserReturnDTO;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * The Class User my page controller.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 6. 오후 2:56:56
 * @Description 마이페이지 User Controller
 */
@Slf4j
@RestController
@Api(description = "마이페이지", tags = "MYPAGE")
@RequestMapping(value = "/api/mypage", name = "마이페이지")
@RequiredArgsConstructor
public class UserMyPageController {

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
     * REQUEST_CHARACTER
     *
     * @author [오지훈]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n필드명|설명|필수여부|데이터 타입(길이)\n" + "-|-|-|-\n";

    /**
     * Find user single result.
     *
     * @param authUserDTO the auth user dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 5:19:29
     * @Description 유저 상세 조회
     */
    @ApiOperation(
            value = "마이페이지 상세 조회"
            , notes = REQUEST_CHARACTER
            + "userSeq|사용자시퀀스|true|Long\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @GetMapping(name = "마이페이지 상세 조회"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<UserReturnDTO> getUser(
            final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO) {
        log.info("UserMyPageController.getUser");
        return responseService.getSingleResult(userService.getMyPage(authUserDTO.getUserSeq()));
    }

    /**
     * Change password single result.
     *
     * @param userCertDTO the user cert dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 7. 2. 오후 4:08:39
     * @Description 마이페이지 비밀번호 변경
     */
    @ApiOperation(
            value = "마이페이지 비밀번호 변경"
            , notes = "## Reqeust ##\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @PutMapping(value = "/change/password", name = "마이페이지 비밀번호 변경"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<UserReturnDTO> changePassword(
            final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
            ,@ApiParam("유저 인증코드 DTO") @RequestBody final UserCertDTO userCertDTO) {
        log.info("UserMyPageController.changePassword");
        userCertDTO.setCertCode("MYPAGE");

        if (ObjectUtils.isEmpty(userCertDTO.getPassword())) {
            throw new CodeMessageHandleException(
                    ErrorEnumCode.LoginError.NULL_PASSWORD.toString()
                    , ErrorEnumCode.LoginError.NULL_PASSWORD.getMessage()
            );
        }

        return responseService.getSingleResult(
                userService.confirmPassword(authUserDTO.getUserId(), userCertDTO)
                , SuccessEnumCode.LoginSuccess.CHANGE_PASSWORD.toString()
                , SuccessEnumCode.LoginSuccess.CHANGE_PASSWORD.getMessage()
                , true
        );
    }
}
