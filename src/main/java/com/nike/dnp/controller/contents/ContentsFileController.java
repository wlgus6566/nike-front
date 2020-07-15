package com.nike.dnp.controller.contents;

import com.nike.dnp.dto.contents.*;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.contents.ContentsFileService;
import com.nike.dnp.service.contents.ContentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The Class Contents file controller.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 13. 오후 5:42:24
 * @Description
 */
@Slf4j
@RestController
@Api(description = "콘텐츠", tags = "CONTENTS")
@RequestMapping(value = "/api/contents/file", name = "콘텐츠 파일")
@RequiredArgsConstructor
public class ContentsFileController {

    /**
     * 응답 서비스
     *
     * @author [이소정]
     */
    private final ResponseService responseService;

    /**
     * 콘텐츠 파일 서비스
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
     * @param topMenuCode           the top menu code
     * @param menuCode              the menu code
     * @param contentsSeq           the contents seq
     * @param contentsFileSearchDTO the contents file search dto
     * @return the all contents file
     * @author [이소정]
     * @CreatedOn 2020. 7. 13. 오후 6:23:50
     * @Description
     */
    @ApiOperation(
        value = "콘텐츠 파일 목록 조회"
        , notes = REQUEST_CHARACTER
        + "page|페이지|false|Integer|0부터 시작\n"
        + "size|사이즈|false|Integer\n"
        + "keyword|검색어|false|String\n"
        + "topMenuCode|상위메뉴|true|String|ASSET/TOOLKIT/FOUNDATION\n"
        + "menuCode|파일구분(2depth menu)|true|String|Asset일 경우 > SP/SU/FA/HO\n"
        + "||||TOOLKIT일 경우 > VMS/EKIN/SOCIAL/RB\n"
        + "||||FOUNDATION 경우 > VMS/EKIN/DIGITAL/RB\n"
        + "orderType|정렬 타입|false|String|최신순:LATEST/파일명 순:FILE_NAME\n"
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
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "콘텐츠 파일 목록 조회", value = "/{topMenuCode}/{menuCode}/{contentsSeq}")
    public SingleResult<Page<ContentsFileResultDTO>> getAllContentsFile(
            @ApiParam(name = "topMenuCode", value = "상위 메뉴", defaultValue = "ASSET", required = true) @PathVariable final String topMenuCode,
            @ApiParam(name = "menuCode", value = "파일구분(2depth menu)", defaultValue = "SP", required = true) @PathVariable final String menuCode,
            @ApiParam(name = "contentsSeq", value = "콘텐츠 시퀀스", defaultValue = "4", required = true) @PathVariable final Long contentsSeq,
            final ContentsFileSearchDTO contentsFileSearchDTO
    ) {
        contentsFileSearchDTO.setContentsSeq(contentsSeq);
        return responseService.getSingleResult(contentsFileService.findAllContentsFilePaging(contentsFileSearchDTO));
    }
//
//    /**
//     * Find contents single result.
//     *
//     * @param topMenuCode the top menu code
//     * @param menuCode    the menu code
//     * @param contentsSeq the contents seq
//     * @return the single result
//     * @author [이소정]
//     * @CreatedOn 2020. 7. 13. 오전 11:58:42
//     * @Description
//     */
//    @ApiOperation(
//            value = "Contents 상세조회"
//            , notes = REQUEST_CHARACTER
//    )
//    @GetMapping(name = " Contents 상세조회", value = "/{topMenuCode}/{menuCode}/{contentsSeq}"
//            , produces = {MediaType.APPLICATION_JSON_VALUE})
//    public SingleResult<Contents> findContents(
//            @ApiParam(name = "topMenuCode", value = "상위 메뉴", defaultValue = "ASSET", required = true) @PathVariable final String topMenuCode,
//            @ApiParam(name = "menuCode", value = "2depth 메뉴코드", defaultValue = "SP", required = true) @PathVariable final String menuCode,
//            @ApiParam(name = "contentsSeq", value = "콘텐츠 시퀀스", defaultValue = "4", required = true) @PathVariable final Long contentsSeq) {
//        return responseService.getSingleResult(contentsService.findByContentsSeq(contentsSeq));
//    }
//
//
//    /**
//     * Update contents single result.
//     *
//     * @param topMenuCode       the top menu code
//     * @param menuCode          the menu code
//     * @param contentsUpdateDTO the contents update dto
//     * @return the single result
//     * @author [이소정]
//     * @CreatedOn 2020. 7. 13. 오전 11:59:45
//     * @Description
//     */
//    @ApiOperation(value = "Contents 수정", notes = REQUEST_CHARACTER)
//    @PutMapping(name = "Contents 수정", value = "/{topMenuCode}/{menuCode}"
//            , produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
//    public SingleResult<Optional<Contents>> updateContents(
//            @ApiParam(name = "topMenuCode", value = "상위 메뉴", defaultValue = "ASSET", required = true) @PathVariable final String topMenuCode,
//            @ApiParam(name = "menuCode", value = "2depth 메뉴코드", defaultValue = "SP", required = true) @PathVariable final String menuCode,
//            @ApiParam(name="contentsUpdateDTO", value = "Contents 수정 Json") @RequestBody final ContentsUpdateDTO contentsUpdateDTO
//    ) {
//        contentsUpdateDTO.setTopMenuCode(topMenuCode);
//        contentsUpdateDTO.setMenuCode(menuCode);
//        return responseService.getSingleResult(contentsService.update(contentsUpdateDTO));
//    }
//
//
//    /**
//     * Delete contents single result.
//     *
//     * @param contentsSeq the contents seq
//     * @return the single result
//     * @author [이소정]
//     * @CreatedOn 2020. 7. 7. 오후 2:06:55
//     * @Description
//     */
//    @ApiOperation(value="Contents 삭제", notes = REQUEST_CHARACTER)
//    @DeleteMapping(name = "Contents 삭제", value = "/{topMenuCode}/{menuCode}/{contentsSeq}"
//            , produces = {MediaType.APPLICATION_JSON_VALUE})
//    public SingleResult<Optional<Contents>> deleteContents(
//            @ApiParam(name = "topMenuCode", value = "상위 메뉴", defaultValue = "ASSET", required = true) @PathVariable final String topMenuCode,
//            @ApiParam(name = "menuCode", value = "2depth 메뉴코드", defaultValue = "SP", required = true) @PathVariable final String menuCode,
//            @ApiParam(name = "contentsSeq", value = "콘텐츠 시퀀스", defaultValue = "4", required = true) @PathVariable final Long contentsSeq) {
//        log.info("AssetController.delete");
//        return responseService.getSingleResult(contentsService.delete(contentsSeq));
//    }


}

