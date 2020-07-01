package com.nike.dnp.controller.user;

import com.nike.dnp.dto.user.UserCertDTO;
import com.nike.dnp.dto.user.UserIdDTO;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class User controller.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 4:41:44
 * @Description
 */
@Slf4j
@RestController
@Api(description = "유저(권한ALL)", tags = "1-1_USER")
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
     * REQUEST_CHARACTER
     *
     * @author [오지훈]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n필드명|설명|필수여부|데이터 타입(길이)\n" + "-|-|-|-\n";

    /**
     * 인증코드 생성 및 이메일 발송
     *
     * @param userIdDTO the user id dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 4:41:44
     * @Description
     */
    @ApiOperation(
            value = "인증코드 생성 및 이메일 발송"
            , notes = "## Reqeust ##\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @GetMapping(value = "/send/cert", name = "인증코드 생성 및 이메일 발송")
    public SingleResult<Boolean> sendCert(final UserIdDTO userIdDTO) {
        log.info("UserController.sendCert");
        //userService.sendCreateUserEmail(userService.findByUserId(userIdDTO.getUserId()));
        return responseService.getSingleResult(true);
    }


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
    public SingleResult<Boolean> changePassword(final UserCertDTO userCertDTO) {
        log.info("UserController.changePassword");
        return responseService.getSingleResult(userService.checkCertCode(userCertDTO));
    }



}
