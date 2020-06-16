package com.nike.dnp.controller.code;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.code.CodeSaveDTO;
import com.nike.dnp.dto.code.CodeSearchDTO;
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

/**
 * CodeController
 *
 * @since 2020.05.27
 * @author [오지훈]
 * @Description Code(공통 코드) Controller 작성
 * @history [오지훈] [2020.05.27] [최초 작성]
 *
 */

@Slf4j
@RestController
@Api(description = "코드 정보", tags = "2_코드")
@RequestMapping(value = "/api/code", name = "코드")
@RequiredArgsConstructor
public class CodeController {

    /**
     * ResponseService
     * @author [오지훈]
     */
    private final ResponseService responseService;

    /**
     * CodeService
     * @author [오지훈]
     */
    private final CodeService codeService;

    /**
     * 목록
     *
     * @param codeSearchDTO the code search dto
     * @param authUserDTO   the auth user dto
     * @return the all codes
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "코드 목록 조회")
    public SingleResult<Page<Code>> getAllCodes(
            final CodeSearchDTO codeSearchDTO
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        return responseService.getSingleResult(codeService.findAlls(codeSearchDTO));
    }

    /**
     * 코드 등록
     *
     * @param codeSaveDTO the code save dto
     * @param authUserDTO the auth user dto
     * @return the response entity
     */
    @ApiOperation(
        value = "코드 등록"
        , notes =
        "## Reqeust ## \n"
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
        Code code = codeService.save(
                codeSaveDTO.getCode()
                , codeSaveDTO.getUpperCode()
                , codeSaveDTO.getCodeName()
                , codeSaveDTO.getCodeDescription()
                , codeSaveDTO.getCodeOrder()
                , codeSaveDTO.getUseYn()
                , authUserDTO.getUserSeq()
        );

        if (!code.getCode().isEmpty()) {
            codeService.redisSaveUpperCode(codeSaveDTO.getUpperCode());
        }

        return responseService.getSingleResult(code);
    }
}
