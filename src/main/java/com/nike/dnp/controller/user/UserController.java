package com.nike.dnp.controller.user;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.user.UserDeleteDTO;
import com.nike.dnp.dto.user.UserSaveDTO;
import com.nike.dnp.dto.user.UserSearchDTO;
import com.nike.dnp.dto.user.UserUpdateDTO;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.entity.user.UserAuth;
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
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

/**
 * The Class User controller.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 4:41:44
 * @Description
 */
@Slf4j
@RestController
@Api(description = "유저", tags = "1_USER")
@RequestMapping(value = "/api/user", name = "사용자")
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
     * REQUEST_CHARACTER
     *
     * @author [오지훈]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n필드명|설명|필수여부|데이터 타입(길이)\n" + "-|-|-|-\n";

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
        SingleResult<Page<User>> result = responseService.getSingleResult(userService.findPages(userSearchDTO));

        //result.getData().forEach(value -> );
        /*for (User user : result.getData()) {
            user.getUserAuth().stream().map(userAuth -> userAuth.getAuth().getAuthName()).forEach(System.out::println);
        }*/

        return result;
    }

    /**
     * Find user single result.
     *
     * @param userSeq     the user seq
     * @param authUserDTO the auth user dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 5:19:29
     * @Description 유저 상세 조회
     */
    @ApiOperation(
            value = "유저 상세 조회"
            , notes = REQUEST_CHARACTER
            + "userSeq|사용자시퀀스|true|Long\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @GetMapping(name = "유저 상세 조회", value = "/{userSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<UserAuth> findUser(
            @PathVariable Long userSeq
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        log.info("UserController.findUser");
        return responseService.getSingleResult(userService.findByUserAuth(userSeq));
    }

    /**
     * Save single result.
     *
     * @param codeSaveDTO the code save dto
     * @param authUserDTO the auth user dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 5:33:44
     * @Description 유저 등록
     */
    @ApiOperation(
            value = "유저 등록"
            , notes = REQUEST_CHARACTER + "\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @PostMapping(name = "유저 등록"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<UserAuth> save(
            final @RequestBody UserSaveDTO codeSaveDTO
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        log.info("UserController.save");
        return responseService.getSingleResult(userService.save(codeSaveDTO, authUserDTO));
    }

    /**
     * Update single result.
     *
     * @param userSeq       the user seq
     * @param userUpdateDTO the user update dto
     * @param authUserDTO   the auth user dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 5:31:41
     * @Description 유저 상세 수정
     */
    @ApiOperation(
            value = "유저 상세 수정" + "\n"
            , notes = REQUEST_CHARACTER
            + "userSeq|사용자시퀀스|true|Long\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @PutMapping(name = "유저 상세 수정", value = "/{userSeq}"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Optional<UserAuth>> update(
            @PathVariable Long userSeq
            , final @RequestBody UserUpdateDTO userUpdateDTO
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        log.info("UserController.update");
        return responseService.getSingleResult(userService.update(userSeq, userUpdateDTO, authUserDTO));
    }

    /**
     * Delete single result.
     *
     * @param userSeq     the user seq
     * @param authUserDTO the auth user dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 5:47:53
     * @Description 유저 단건 삭제
     */
    @ApiOperation(
            value = "유저 단건 삭제" + "\n"
            , notes = REQUEST_CHARACTER
            + "userSeq|사용자시퀀스|true|Long\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @DeleteMapping(name = "유저 단건 삭제", value = "/{userSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Optional<User>> deleteOne(
            @PathVariable Long userSeq
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        log.info("UserController.deleteOne");
        return responseService.getSingleResult(userService.deleteOne(userSeq, authUserDTO));
    }

    /**
     * Delete array single result.
     *
     * @param userDeleteDTO the user delete dto
     * @param authUserDTO   the auth user dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 6:01:16
     * @Description 유저 배열 삭제
     */
    @ApiOperation(
            value = "유저 배열 삭제" + "\n"
            , notes = "## Reqeust ##\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @DeleteMapping(name = "유저 배열 삭제"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<User>> deleteArray(
            final @RequestBody UserDeleteDTO userDeleteDTO
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        log.info("UserController.deleteArray");
        return responseService.getSingleResult(userService.deleteArray(userDeleteDTO, authUserDTO));
    }
}
