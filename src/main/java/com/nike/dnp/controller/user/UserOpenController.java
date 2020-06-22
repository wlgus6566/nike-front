package com.nike.dnp.controller.user;

import com.nike.dnp.dto.user.UserCertDTO;
import com.nike.dnp.dto.user.UserIdDTO;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.user.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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
@Api(description = "유저 정보", tags = "1_USER")
@RequestMapping(value = "/open/api/user", name = "사용자")
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
     * Send cert code single result.
     *
     * @param userIdDTO the user id dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 4:41:44
     * @Description
     */
    @GetMapping("/send/cert")
    public SingleResult<Boolean> sendCertCode(final UserIdDTO userIdDTO) {
        log.info("UserController.sendCertCode");
        User user = userService.findByUserId(userIdDTO.getUserId());
        userService.sendEmail(user.getUserId());
        return responseService.getSingleResult(true);
    }

    /**
     * Change password single result.
     *
     * @param userCertDTO the user cert dto
     * @return the single result
     * @throws Exception the exception
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 4:41:44
     * @Description
     */
    @GetMapping("/check/cert")
    public SingleResult<Boolean> changePassword(final UserCertDTO userCertDTO) throws Exception {
        log.info("UserController.changePassword");
        return responseService.getSingleResult(userService.checkByCertCode(userCertDTO.getCertCode()));
    }
}
