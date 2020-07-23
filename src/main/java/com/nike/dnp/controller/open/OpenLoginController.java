package com.nike.dnp.controller.open;

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
 * The Class Login controller.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 4:41:44
 * @Description
 */
@Slf4j
@RestController
@Api(description = "로그인 관련", tags = "LOGIN")
@RequestMapping(value = "/api/open/login", name = "로그인 관련")
@RequiredArgsConstructor
public class OpenLoginController {

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
     * OPERATION_CHARACTER
     *
     * @author [오지훈]
     */
    private static final String OPERATION_CHARACTER
            = "## Request ##\n[하위 Parameters 참조]\n\n\n\n## Response ##\n[하위 Model 참조]\n\n\n\n";

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
            , notes = OPERATION_CHARACTER
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
                userMailService.sendMailForSetPassword(user)
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
            , notes = OPERATION_CHARACTER
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
            , notes = OPERATION_CHARACTER
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
