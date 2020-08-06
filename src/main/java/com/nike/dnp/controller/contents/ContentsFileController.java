package com.nike.dnp.controller.contents;

import com.nike.dnp.dto.contents.ContentsFileResultDTO;
import com.nike.dnp.dto.contents.ContentsFileSearchDTO;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.contents.ContentsFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class Contents file controller.
 *
 * @author [이소정]
 * @implNote 컨텐츠 파일 컨트롤러
 * @since 2020. 7. 13. 오후 5:42:24
 */
@Slf4j
@RestController
@Api(description = "컨텐츠", tags = "CONTENTS")
@RequestMapping(value = "/api/contents", name = "컨텐츠 파일")
@RequiredArgsConstructor
public class ContentsFileController {

    /**
     * 응답 서비스
     *
     * @author [이소정]
     */
    private final ResponseService responseService;

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
     * Gets all contents file.
     *
     * @param topMenuCode   the top menu code
     * @param menuCode      the menu code
     * @param contentsSeq   the contents seq
     * @param fileSearchDTO the file search dto
     * @return the all contents file
     * @author [이소정]
     * @implNote 컨텐츠 파일 목록 조회
     * @since 2020. 7. 13. 오후 6:23:50
     */
    @ApiOperation(
        value = "컨텐츠 파일 목록 조회"
        , notes = REQUEST_CHARACTER
        + "page|페이지|false|Integer|0부터 시작\n"
        + "size|사이즈|false|Integer\n"
        + "keyword|검색어|false|String\n"
        + "topMenuCode|상위메뉴|true|String|ASSET/TOOLKIT/FOUNDATION\n"
        + "menuCode|파일구분(2depth menu)|true|String|Asset일 경우 > SP/SU/FA/HO\n"
        + "||||TOOLKIT일 경우 > VMS/EKIN/SOCIAL/RB\n"
        + "||||FOUNDATION 경우 > VMS/EKIN/DIGITAL/RB\n"
        + "orderType|정렬 타입|false|String|기본 정렬:ORDER/파일명 순:FILE_NAME\n"
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
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "컨텐츠 파일 목록 조회", value = "/{topMenuCode}/{menuCode}/{contentsSeq}/file")
    public SingleResult<Page<ContentsFileResultDTO>> findAllContentsFile(
            @ApiParam(name = "topMenuCode", value = "상위 메뉴", defaultValue = "ASSET", required = true) @PathVariable final String topMenuCode,
            @ApiParam(name = "menuCode", value = "파일구분(2depth menu)", defaultValue = "SP", required = true) @PathVariable final String menuCode,
            @ApiParam(name = "contentsSeq", value = "컨텐츠 시퀀스", defaultValue = "4", required = true) @PathVariable final Long contentsSeq,
            final ContentsFileSearchDTO fileSearchDTO
    ) {
        fileSearchDTO.setContentsSeq(contentsSeq);
        return responseService.getSingleResult(contentsFileService.findAllContentsFilePaging(fileSearchDTO));
    }

}

