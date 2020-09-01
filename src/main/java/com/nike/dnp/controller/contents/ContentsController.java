package com.nike.dnp.controller.contents;

import com.nike.dnp.common.aspect.ValidField;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.dto.contents.ContentsMailSendDTO;
import com.nike.dnp.dto.contents.ContentsResultDTO;
import com.nike.dnp.dto.contents.ContentsSaveDTO;
import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.contents.ContentsFileService;
import com.nike.dnp.service.contents.ContentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * The Class Contents controller.
 *
 * @author [이소정]
 * @implNote 컨텐츠 컨트롤러
 * @since 2020. 7. 13. 오전 11:52:57
 */
@Slf4j
@RestController
@Api(description = "컨텐츠", tags = "CONTENTS")
@RequestMapping(value = "/api/contents", name = "컨텐츠")
@RequiredArgsConstructor
public class ContentsController {

    /**
     * 응답 서비스
     *
     * @author [이소정]
     */
    private final ResponseService responseService;

    /**
     * 컨텐츠 서비스
     *
     * @author [이소정]
     */
    private final ContentsService contentsService;

    /**
     * 컨텐츠 파일 서비스
     *
     * @author [이소정]
     */
    private final ContentsFileService contentsFileService;

    /**
     * The constant REQUEST_CHARACTER
     *
     * @author [이소정]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";

    /**
     * The constant TOP_MENU_CODE
     *
     * @author [이소정]
     */
    private static final String TOP_MENU_CODE = "topMenuCode";

    /**
     * The constant MENU_CODE
     *
     * @author [이소정]
     */
    private static final String MENU_CODE = "menuCode";

    /**
     * The constant TOP_MENU_VALUE
     *
     * @author [이소정]
     */
    private static final String TOP_MENU_VALUE = "상위 메뉴";

    /**
     * The constant TOP_MENU_EXAMPLE
     *
     * @author [이소정]
     */
    private static final String TOP_MENU_EXAMPLE = "ASSET";

    /**
     * The constant MENU_CODE_VALUE
     *
     * @author [이소정]
     */
    private static final String MENU_CODE_VALUE = "2depth 메뉴코드";

    /**
     * The constant REQUEST_MENU_DESC
     *
     * @author [이소정]
     */
    private static final String REQUEST_MENU_DESC =
            TOP_MENU_CODE+"|상위메뉴|true|String|ASSET/TOOLKIT/FOUNDATION\n"
            + MENU_CODE+"|파일구분(2depth menu, 업로드 위치)|true|String|Asset일 경우 > SP/SU/FA/HO\n"
            + "||||TOOLKIT일 경우 > VMS/EKIN/SOCIAL/RB\n"
            + "||||FOUNDATION 경우 > VMS/EKIN/DIGITAL/RB\n";


    /**
     * 컨텐츠 전체목록 조회
     *
     * @param topMenuCode       the top menu code
     * @param menuCode          the menu code
     * @param contentsSearchDTO the contents search dto
     * @return all managers
     * @author [이소정]
     * @implNote 컨텐츠 목록 조회
     * @since 2020. 6. 19. 오후 5:56:03
     */
    @ApiOperation(
        value = "컨텐츠 목록 조회"
        , notes = REQUEST_CHARACTER
        + "page|페이지|false|Integer|0부터 시작\n"
        + "size|사이즈|false|Integer\n"
        + "keyword|검색어|false|Strin|ASSET/TOOLKIT/FOUNDATION\n"
        + "orderType|정렬 타입|false|String|최신순:LATEST/시작일 순:START_DATE\n"
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
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "컨텐츠 목록 조회", value = "/{topMenuCode}/{menuCode}")
    public SingleResult<Page<ContentsResultDTO>> findAllContents(
            @ApiParam(name = TOP_MENU_CODE, value = TOP_MENU_VALUE, defaultValue = TOP_MENU_EXAMPLE, required = true) @PathVariable final String topMenuCode,
            @ApiParam(name = MENU_CODE, value = "파일구분(2depth menu)", defaultValue = "ALL", required = true) @PathVariable final String menuCode,
            final ContentsSearchDTO contentsSearchDTO

    ) {
        contentsSearchDTO.setTopMenuCode(topMenuCode);
        contentsSearchDTO.setMenuCode(menuCode);
        return responseService.getSingleResult(contentsService.findAllPaging(contentsSearchDTO, topMenuCode, menuCode));
    }

    /**
     * Save contents single result.
     *
     * @param topMenuCode     the top menu code
     * @param menuCode        the menu code
     * @param contentsSaveDTO the contents save dto
     * @return the single result
     * @author [이소정]
     * @implNote 컨텐츠 등록
     * @since 2020. 7. 13. 오전 11:58:47
     */
    @ApiOperation(
            value = "컨텐츠 등록"
            , notes = REQUEST_CHARACTER + REQUEST_MENU_DESC
    )
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "컨텐츠 등록", value = "/{topMenuCode}/{menuCode}")
    @ValidField
    public SingleResult<Contents> saveContents(
            @ApiParam(name = TOP_MENU_CODE, value = TOP_MENU_VALUE, defaultValue = TOP_MENU_EXAMPLE, required = true) @PathVariable final String topMenuCode
            , @ApiParam(name = MENU_CODE, value = MENU_CODE_VALUE, defaultValue = "SP", required = true) @PathVariable final String menuCode
            , @RequestBody @Valid final ContentsSaveDTO contentsSaveDTO
            , @ApiIgnore final BindingResult result
    ) {
        contentsSaveDTO.setTopMenuCode(topMenuCode);
        contentsSaveDTO.setMenuCode(menuCode);
        return responseService.getSingleResult(
                contentsService.save(contentsSaveDTO)
                , ServiceCode.ReturnTypeEnumCode.CREATE.toString()
                , ServiceCode.ReturnTypeEnumCode.CREATE.getMessage()
                , true
        );
    }

    /**
     * Find contents single result.
     *
     * @param topMenuCode the top menu code
     * @param menuCode    the menu code
     * @param contentsSeq the contents seq
     * @return the single result
     * @author [이소정]
     * @implNote 컨텐츠 상세조회
     * @since 2020. 7. 13. 오전 11:58:42
     */
    @ApiOperation(
            value = "컨텐츠 상세조회"
            , notes = REQUEST_CHARACTER
            + REQUEST_MENU_DESC
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = " 컨텐츠 상세조회", value = "/{topMenuCode}/{menuCode}/{contentsSeq}")
    public SingleResult<ContentsResultDTO> findContents(
            @ApiParam(name = TOP_MENU_CODE, value = TOP_MENU_VALUE, defaultValue = TOP_MENU_EXAMPLE, required = true) @PathVariable final String topMenuCode
            , @ApiParam(name = MENU_CODE, value = MENU_CODE_VALUE, defaultValue = "SP", required = true) @PathVariable final String menuCode
            , @ApiParam(name = "contentsSeq", value = "컨텐츠 시퀀스", defaultValue = "4", required = true) @PathVariable final Long contentsSeq) {
        return responseService.getSingleResult(contentsService.findByContentsSeq(contentsSeq, topMenuCode, menuCode));
    }


    /**
     * Update contents single result.
     *
     * @param topMenuCode     the top menu code
     * @param menuCode        the menu code
     * @param contentsSeq     the contents seq
     * @param contentsSaveDTO the contents save dto
     * @param result          the result
     * @return the single result
     * @author [이소정]
     * @implNote 콘텐츠 수정
     * @since 2020. 7. 13. 오전 11:59:45
     */
    @ApiOperation(value = "컨텐츠 수정", notes = REQUEST_CHARACTER + REQUEST_MENU_DESC)
    @PutMapping(name = "컨텐츠 수정", value = "/{topMenuCode}/{menuCode}/{contentsSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<Contents> updateContents(
            @ApiParam(name = TOP_MENU_CODE, value = TOP_MENU_VALUE, defaultValue = TOP_MENU_EXAMPLE, required = true) @PathVariable final String topMenuCode
            , @ApiParam(name = MENU_CODE, value = MENU_CODE_VALUE, defaultValue = "SP", required = true) @PathVariable final String menuCode
            , @ApiParam(name = "contentsSeq", value = "컨텐츠 시퀀스", defaultValue = "4", required = true) @PathVariable final Long contentsSeq
            , @ApiParam(name="contentsUpdateDTO", value = "Contents 수정 Json") @RequestBody @Valid final ContentsSaveDTO contentsSaveDTO
            , @ApiIgnore final BindingResult result
    ) {
        contentsSaveDTO.setTopMenuCode(topMenuCode);
        contentsSaveDTO.setMenuCode(menuCode);
        contentsSaveDTO.setContentsSeq(contentsSeq);
        return responseService.getSingleResult(contentsService.update(contentsSaveDTO));
    }


    /**
     * Delete contents single result.
     *
     * @param topMenuCode the top menu code
     * @param menuCode    the menu code
     * @param contentsSeq the contents seq
     * @return the single result
     * @author [이소정]
     * @implNote
     * @apiNote
     * @since 2020. 7. 7. 오후 2:06:55
     */
    @ApiOperation(value="컨텐츠 삭제", notes = REQUEST_CHARACTER + REQUEST_MENU_DESC)
    @DeleteMapping(name = "컨텐츠 삭제", value = "/{topMenuCode}/{menuCode}/{contentsSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Contents> deleteContents(
            @ApiParam(name = TOP_MENU_CODE, value = TOP_MENU_VALUE, defaultValue = TOP_MENU_EXAMPLE, required = true) @PathVariable final String topMenuCode
            , @ApiParam(name = MENU_CODE, value = MENU_CODE_VALUE, defaultValue = "SP", required = true) @PathVariable final String menuCode
            , @ApiParam(name = "contentsSeq", value = "컨텐츠 시퀀스", defaultValue = "4", required = true) @PathVariable final Long contentsSeq
    ) {
        log.info("ContentsController.delete");
        return responseService.getSingleResult(contentsService.delete(contentsSeq));
    }

    /**
     * 콘텐츠 파일 다운로드
     *
     * @param contentsFileSeq the contents file seq
     * @return the string
     * @author [이소정]
     * @implNote 콘텐츠 파일 다운로드
     * @since 2020. 7. 15. 오후 6:30:45
     */
    @ApiOperation(value = "컨텐츠 파일 다운로드", notes = REQUEST_CHARACTER)
    @GetMapping(name = "컨텐츠 파일 다운로드", value = "/download/{contentsFileSeq}")
    //@CrossOrigin(origins = {"https://devwww.nikespace.co.kr", "http://devwww.nikespace.co.kr","http://localhost:8081", "https://localhost:8081"})
    public ResponseEntity<Resource> downloadContents(
            @ApiParam(name="contentsFileSeq", value = "컨텐츠 파일 시퀀스", defaultValue = "1", required = true) @PathVariable final Long contentsFileSeq
    ) throws IOException {
        return contentsFileService.downloadFile(contentsFileSeq);
//        return responseService.getSuccessResult();
    }

    /**
     * Send email common result.
     *
     * @param mailSendDTO the mail send dto
     * @param result      the result
     * @return the common result
     * @author [이소정]
     * @implNote 컨텐츠 알림메일전송
     * @since 2020. 7. 30. 오후 3:58:41
     */
    @ApiOperation(value = "컨텐츠 알림메일전송", notes = REQUEST_CHARACTER)
    @PostMapping(name = "컨텐츠 알림메일전송", value = "/sendMail", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public CommonResult sendEmail(
            @RequestBody @Valid final ContentsMailSendDTO mailSendDTO
            , @ApiIgnore final BindingResult result
    ) {
        contentsService.sendEmail(mailSendDTO);
        return responseService.getSuccessResult();
    }

    /**
     * Send email common result.
     *
     * @param topMenuCode the top menu code
     * @param menuCode    the menu code
     * @return the common result
     * @author [이소정]
     * @implNote 컨텐츠 권한 목록 조회
     * @since 2020. 7. 30. 오후 3:58:41
     */
    @ApiOperation(value = "컨텐츠 권한 목록 조회", notes = REQUEST_CHARACTER)
    @GetMapping(name = "컨텐츠 권한 목록 조회", value = "/{topMenuCode}/{menuCode}/authList", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<AuthReturnDTO> loadAuthList(
            @ApiParam(name = TOP_MENU_CODE, value = TOP_MENU_VALUE, defaultValue = TOP_MENU_EXAMPLE, required = true) @PathVariable final String topMenuCode
            , @ApiParam(name = MENU_CODE, value = MENU_CODE_VALUE, defaultValue = "SP", required = true) @PathVariable final String menuCode
    ) {
        return contentsService.loadAuthList(topMenuCode, menuCode);
    }



}


