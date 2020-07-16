package com.nike.dnp.controller.user;

import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.common.variable.SuccessEnumCode;
import com.nike.dnp.dto.user.UserCertDTO;
import com.nike.dnp.dto.user.UserIdDTO;
import com.nike.dnp.dto.user.UserReturnDTO;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.user.UserMailService;
import com.nike.dnp.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * The Class User controller.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 4:41:44
 * @Description
 */
@Slf4j
@RestController
@Api(description = "유저(권한없음)", tags = "OPEN_USER")
@RequestMapping(value = "/api/open/user", name = "유저")
@RequiredArgsConstructor
public class UserOpenController {

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
     * The User mail service
     *
     * @author [오지훈]
     */
    private final UserMailService userMailService;

    /**
     * REQUEST_CHARACTER
     *
     * @author [오지훈]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n필드명|설명|필수여부|데이터 타입(길이)\n" + "-|-|-|-\n";

    /**
     * Send cert single result.
     *
     * @param userIdDTO the user id dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 7. 2. 오전 11:30:10
     * @Description 인증코드 생성 및 이메일 발송
     */
    @ApiOperation(
            value = "ID 확인, 인증코드 생성 및 이메일 발송"
            , notes = "## Reqeust ##\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @GetMapping(value = "/send/cert", name = "ID 확인, 인증코드 생성 및 비밀번호 설정 이메일 발송")
    public SingleResult<String> sendCert(final UserIdDTO userIdDTO) {
        log.info("UserController.sendCert");
        final User user = userService.findByUserIdReturnOptional(userIdDTO.getUserId()).orElseThrow(
                () -> new CodeMessageHandleException(
                        ErrorEnumCode.UserError.RETRY_CONFIRM_EMAIL.toString()
                        ,ErrorEnumCode.UserError.RETRY_CONFIRM_EMAIL.getMessage()
                )
        );
        return responseService.getSingleResult(
                userMailService.sendMailForCreateUser(user)
                , SuccessEnumCode.LoginSuccess.SEND_EMAIL.toString()
                , SuccessEnumCode.LoginSuccess.SEND_EMAIL.getMessage()
                , true
        );
    }

    /**
     * Change password single result.
     *
     * @param userCertDTO the user cert dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 4:41:44
     * @Description 인증코드 검증 및 비밀번호 설정
     */
    @ApiOperation(
            value = "인증코드 검증 및 비밀번호 설정"
            , notes = "## Reqeust ##\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @PutMapping(value = "/set/password", name = "인증코드 검증 및 비밀번호 설정"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<UserReturnDTO> setPassword(
            @ApiParam("유저 인증코드 DTO") @RequestBody final UserCertDTO userCertDTO) {
        log.info("UserOpenController.setPassword");
        return responseService.getSingleResult(
                userService.confirmPassword(userCertDTO)
                , SuccessEnumCode.LoginSuccess.CHANGE_PASSWORD.toString()
                , SuccessEnumCode.LoginSuccess.CHANGE_PASSWORD.getMessage()
                , true
        );
    }

    /**
     * Change password single result.
     *
     * @param userCertDTO the user cert dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 7. 2. 오후 4:08:39
     * @Description 인증코드 검증 및 비밀번호 변경
     */
    @ApiOperation(
            value = "인증코드 검증 및 비밀번호 변경"
            , notes = "## Reqeust ##\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @PutMapping(value = "/change/password", name = "인증코드 검증 및 비밀번호 변경"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<UserReturnDTO> changePassword(
            @ApiParam("유저 인증코드 DTO") @RequestBody final UserCertDTO userCertDTO) {
        log.info("UserOpenController.changePassword");

        if (ObjectUtils.isEmpty(userCertDTO.getPassword())) {
            throw new CodeMessageHandleException(
                    ErrorEnumCode.LoginError.NULL_PASSWORD.toString()
                    , ErrorEnumCode.LoginError.NULL_PASSWORD.getMessage()
            );
        }
        return responseService.getSingleResult(
                userService.confirmPassword(userCertDTO)
                , SuccessEnumCode.LoginSuccess.CHANGE_PASSWORD.toString()
                , SuccessEnumCode.LoginSuccess.CHANGE_PASSWORD.getMessage()
                , true
        );
    }

}
