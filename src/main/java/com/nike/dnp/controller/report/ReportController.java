package com.nike.dnp.controller.report;

import com.nike.dnp.dto.report.ReportSearchDTO;
import com.nike.dnp.entity.report.Report;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.report.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The Class Report controller.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 7. 오후 2:37:43
 * @Description
 */
@Slf4j
@RestController
@Api(description = "리포트", tags = "11_REPORT")
@RequestMapping(value = "/api/report", name = "Report")
@RequiredArgsConstructor
public class ReportController {

    /**
     * 응답 서비스
     * @author [이소정]
     */
    private final ResponseService responseService;


    /**
     * The Report service
     * @author [이소정]
     */
    private final ReportService reportService;

    @Autowired
    MessageSource messageSource;

    /**
     * The constant REQUEST_CHARACTER
     *
     * @author [이소정]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";

    @ApiOperation(
        value = "Report 목록 조회"
        , notes = REQUEST_CHARACTER
        + "page|페이지|false|Integer|0부터 시작\n"
        + "size|사이즈|false|Integer\n"
        + "keyword|검색어|false|String\n"
        + "sectionCode|보고서 구분 코드|false|String|ALL/SP/SU/FA/HO\n"
        + "groupSeq|그룹 시퀀스|false|Integer\n"
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
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "Report 목록 조회")
    public SingleResult<Page<Report>> getAllReports(
            final ReportSearchDTO reportSearchDTO
    ) {
        return responseService.getSingleResult(reportService.findAllPaging(reportSearchDTO));
    }
//
//
//    /**
//     * Save contents single result.
//     *
//     * @param contentsAssetSaveDTO the contents asset save dto
//     * @return the single result
//     * @author [이소정]
//     * @CreatedOn 2020. 6. 24. 오후 3:45:44
//     * @Description
//     */
//    @ApiOperation(
//            value = "Asset 등록"
//            , notes = REQUEST_CHARACTER
//    )
//    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "Asset 등록")
//    public SingleResult<Contents> saveContents(
//            @RequestBody final ContentsAssetSaveDTO contentsAssetSaveDTO
//    ) {
//        contentsAssetSaveDTO.setMenuCode(contentsAssetSaveDTO.getUploadCode());
//        contentsAssetSaveDTO.setTopMenuCode("ASSET");
//        Contents contents = contentsService.save(contentsAssetSaveDTO);
//        return responseService.getSingleResult(contents);
//    }
//
//    @ApiOperation(
//            value = "Asset 상세조회"
//            , notes = REQUEST_CHARACTER
//    )
//    @GetMapping(name = " Toolkit 상세조회", value = "/{contentsSeq}"
//            , produces = {MediaType.APPLICATION_JSON_VALUE})
//    public SingleResult<Contents> findContents(
//            @ApiParam(name = "contentsSeq", value = "콘텐츠 시퀀스", defaultValue = "4") @PathVariable final Long contentsSeq) {
//        return responseService.getSingleResult(contentsService.findByContentsSeq(contentsSeq));
//    }
//
//    /**
//     * Update contents single result.
//     *
//     * @param contentsAssetUpdateDTO the contents asset update dto
//     * @return the single result
//     * @author [이소정]
//     * @CreatedOn 2020. 7. 3. 오후 2:45:11
//     * @Description
//     */
//    @ApiOperation(value = "Asset 수정", notes = REQUEST_CHARACTER)
//    @PutMapping(name = "Asset 수정", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
//    public SingleResult<Optional<Contents>> updateContents(@ApiParam(name="contentsUpdateDTO", value = "ASSET 수정 Json") @RequestBody final ContentsAssetUpdateDTO contentsAssetUpdateDTO) {
//        contentsAssetUpdateDTO.setTopMenuCode("ASSET");
//        contentsAssetUpdateDTO.setMenuCode(contentsAssetUpdateDTO.getUploadCode());
//        return responseService.getSingleResult(contentsService.update(contentsAssetUpdateDTO));
//    }

}

