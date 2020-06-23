package com.nike.dnp.controller.code;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.code.CodeSaveDTO;
import com.nike.dnp.dto.code.CodeSearchDTO;
import com.nike.dnp.dto.code.CodeUpdateDTO;
import com.nike.dnp.dto.code.CodeUpperDTO;
import com.nike.dnp.entity.code.Code;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.code.CodeService;
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
 * CodeController
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 5:22:30
 * @Description Code(공통 코드) Controller 작성
 */
@Slf4j
@RestController
@Api(description = "코드 정보", tags = "3_CODE")
@RequestMapping(value = "/api/code", name = "코드")
@RequiredArgsConstructor
public class CodeController {

    /**
     * ResponseService
     *
     * @author [오지훈]
     */
    private final ResponseService responseService;

    /**
     * CodeService
     *
     * @author [오지훈]
     */
    private final CodeService codeService;

    /**
     * REQUEST_CHARACTER
     *
     * @author [오지훈]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n필드명|설명|필수여부|데이터 타입(길이)\n" + "-|-|-|-\n";

    /**
     * 전체 코드 목록
     *
     * @param codeSearchDTO the code search dto
     * @param authUserDTO   the auth user dto
     * @return the all codes
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 5:22:31
     * @Description
     */
    @ApiOperation(
        value = "코드 목록 조회"
        , notes = REQUEST_CHARACTER
        + "keyword|검색어|false|String\n"
        + "page|페이지|false|Integer\n"
        + "size|사이즈|false|Integer\n"
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
    @GetMapping(name = "코드 목록 조회"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Page<Code>> getAllCodes(
            final CodeSearchDTO codeSearchDTO
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        return responseService.getSingleResult(codeService.findPages(codeSearchDTO));
    }

    /**
     * 하위 코드 목록
     *
     * @param codeUpperDTO the code upper dto
     * @param authUserDTO  the auth user dto
     * @return the conf codes
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 5:22:31
     * @Description
     */
    @ApiOperation(
        value = "하위 코드 목록 조회"
        , notes = REQUEST_CHARACTER + "\n"
        + "[하위 Parameters 참조]\n\n\n\n"
        + "## Response ## \n"
        + "[하위 Model 참조]\n\n\n\n"
    )
    @GetMapping(value = "/codes", name = "하위 코드 목록 조회"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<Code>> getConfCodes(
            final CodeUpperDTO codeUpperDTO
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        return responseService.getSingleResult(codeService.subCodes(codeUpperDTO.getUpperCode()));
    }

    /**
     * 코드 등록
     *
     * @param codeSaveDTO the code save dto
     * @param authUserDTO the auth user dto
     * @return the response entity
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 5:22:31
     * @Description
     */
    @ApiOperation(
        value = "코드 등록"
        , notes = REQUEST_CHARACTER + "\n"
        + "[하위 Parameters 참조]\n\n\n\n"
        + "## Response ## \n"
        + "[하위 Model 참조]\n\n\n\n"
    )
    @PostMapping(name = "코드 등록"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Code> saveCode(
            final @RequestBody CodeSaveDTO codeSaveDTO
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        Code code = codeService.save(codeSaveDTO, authUserDTO);
        return responseService.getSingleResult(code);
    }

    /**
     * 코드 수정
     *
     * @param code          the code
     * @param codeUpdateDTO the code update dto
     * @param authUserDTO   the auth user dto
     * @return the response entity
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 5:22:31
     * @Description
     */
    @ApiOperation(
            value = "코드 수정"
            , notes = REQUEST_CHARACTER + "\n"
            + "codeCd|코드|true|String\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @PutMapping(value = "/{code}", name = "코드 수정"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Optional<Code>> updateCode(
            final @PathVariable String code
            , final @RequestBody CodeUpdateDTO codeUpdateDTO
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        Optional<Code> codeEntity = codeService.update(code, codeUpdateDTO, authUserDTO);
        return responseService.getSingleResult(codeEntity);
    }

    /**
     * 코드 삭제
     *
     * @param code        the code
     * @param authUserDTO the auth user dto
     * @return the response entity
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 5:22:31
     * @Description
     */
    @ApiOperation(
            value = "코드 삭제"
            , notes = "## Reqeust ## \n"
            + "codeCd|코드|true|String\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @DeleteMapping(value = "/{code}", name = "코드 삭제"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Optional<Code>> deleteCode(
            final @PathVariable String code
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        return responseService.getSingleResult(codeService.delete(code, authUserDTO));
    }
}
