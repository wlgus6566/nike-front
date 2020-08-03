package com.nike.dnp.controller.open;

import com.nike.dnp.common.aspect.ValidField;
import com.nike.dnp.common.validation.ValidationGroups;
import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.common.variable.SuccessCode;
import com.nike.dnp.dto.user.UserCertDTO;
import com.nike.dnp.dto.user.UserIdDTO;
import com.nike.dnp.dto.user.UserResultDTO;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.user.UserMailService;
import com.nike.dnp.service.user.UserService;
import com.nike.dnp.util.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * The Class Login controller.
 *
 * @author [오지훈]
 * @since 2020. 6. 22. 오후 4:41:44
 * @apiNote
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
     * @since 2020. 7. 2. 오전 11:30:10
     * @apiNote 인증코드 생성 및 이메일 발송
     */
    @ApiOperation(value = "ID 확인, 인증코드 생성 및 이메일 발송", notes = OPERATION_CHARACTER)
    @GetMapping(name = "ID 확인, 인증코드 생성 및 비밀번호 설정 이메일 발송", value = "/send/cert")
    @ValidField
    public SingleResult<String> sendCert (
            @ModelAttribute @Valid final UserIdDTO userIdDTO
            , @ApiIgnore final BindingResult result) {
        log.info("UserController.sendCert");

        final User user = userService.findByUserIdReturnOptional(userIdDTO.getUserId()).orElseThrow(
                () -> new CodeMessageHandleException(
                        FailCode.ConfigureError.RETRY_CONFIRM_EMAIL.name()
                        , MessageUtil.getMessage(FailCode.ConfigureError.RETRY_CONFIRM_EMAIL.name())
                )
        );

        return responseService.getSingleResult(
                userMailService.sendMailForSetPassword(user)
                , SuccessCode.ConfigureSuccess.SEND_EMAIL.name()
                , MessageUtil.getMessage(SuccessCode.ConfigureSuccess.SEND_EMAIL.name())
                , true
        );
    }

    /**
     * Change password single result.
     *
     * @param userCertDTO the user cert dto
     * @return the single result
     * @author [오지훈]
     * @since 2020. 6. 22. 오후 4:41:44
     * @apiNote 인증코드 검증 및 비밀번호 설정
     */
    @ApiOperation(value = "인증코드 검증 및 비밀번호 설정", notes = OPERATION_CHARACTER)
    @PutMapping(name = "인증코드 검증 및 비밀번호 설정", value = "/set/password"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<UserResultDTO> settingPassword (
            @ApiParam(value = "유저 인증코드 DTO", required = true) @RequestBody
            @Validated({ValidationGroups.Group1.class, ValidationGroups.Group3.class}) final UserCertDTO userCertDTO
            , @ApiIgnore final BindingResult result) {
        log.info("UserOpenController.settingPassword");
        return responseService.getSingleResult(
                userService.confirmPassword(userCertDTO)
                , SuccessCode.ConfigureSuccess.CHANGE_PASSWORD.name()
                , MessageUtil.getMessage(SuccessCode.ConfigureSuccess.CHANGE_PASSWORD.name())
                , true
        );
    }

    /**
     * Change password single result.
     *
     * @param userCertDTO the user cert dto
     * @return the single result
     * @author [오지훈]
     * @since 2020. 7. 2. 오후 4:08:39
     * @apiNote 인증코드 검증 및 비밀번호 변경
     */
    @ApiOperation(value = "인증코드 검증 및 비밀번호 변경", notes = OPERATION_CHARACTER)
    @PutMapping(name = "인증코드 검증 및 비밀번호 변경", value = "/change/password"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<UserResultDTO> changePassword (
            @ApiParam(value = "유저 인증코드 DTO", required = true) @RequestBody
            @Validated({ValidationGroups.class}) final UserCertDTO userCertDTO
            , @ApiIgnore final BindingResult result) {
        log.info("UserOpenController.changePassword");
        return responseService.getSingleResult(
                userService.confirmPassword(userCertDTO)
                , SuccessCode.ConfigureSuccess.CHANGE_PASSWORD.name()
                , MessageUtil.getMessage(SuccessCode.ConfigureSuccess.CHANGE_PASSWORD.name())
                , true
        );
    }

    /**
     * Check cert single result.
     *
     * @param userCertDTO the user cert dto
     * @param result      the result
     * @return the single result
     * @author [오지훈]
     * @since 2020. 7. 27. 오후 4:12:52
     * @apiNote 인증코드 검증
     */
    @ApiOperation(value = "인증코드 검증", notes = OPERATION_CHARACTER)
    @PostMapping(name = "인증코드 검증", value = "/check/cert"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<Boolean> checkCert (
            @ApiParam(value = "유저 인증코드 DTO", required = true) @RequestBody
            @Validated({ValidationGroups.Group3.class}) final UserCertDTO userCertDTO
            , @ApiIgnore final BindingResult result) {
        log.info("UserOpenController.checkCert");
        return responseService.getSingleResult(
                userService.checkCertCode(userCertDTO)
                , SuccessCode.ConfigureSuccess.SUCCESS.name()
                , MessageUtil.getMessage(SuccessCode.ConfigureSuccess.SUCCESS.name())
                , false
        );
    }

}
