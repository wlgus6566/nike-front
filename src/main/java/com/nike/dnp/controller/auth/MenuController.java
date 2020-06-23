package com.nike.dnp.controller.auth;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.user.UserSearchDTO;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


/**
 * The Class Menu controller.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 23. 오후 5:22:25
 * @Description
 */
@Slf4j
@RestController
@Api(description = "권한/메뉴 정보", tags = "3_AUTH/MENU")
@RequestMapping(value = "/api/menu", name = "권한/메뉴")
@RequiredArgsConstructor
public class MenuController {

    /**
     * The Response service
     *
     * @author [오지훈]
     */
    private final ResponseService responseService;

    private final UserService userService;

    /**
     * Find pages single result.
     *
     * @param userSearchDTO the user search dto
     * @param authUserDTO   the auth user dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 3:37:10
     * @Description 유저 목록 조회
     */
    @ApiOperation(
            value = "유저 목록 조회"
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
            + "size||노출갯수|Integer\n\n\n\n"
    )
    @GetMapping(name = "유저 목록 조회"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Page<User>> findPages(
            final UserSearchDTO userSearchDTO
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        log.info("UserController.findPages");
        return responseService.getSingleResult(userService.findPages(userSearchDTO));
    }


}
