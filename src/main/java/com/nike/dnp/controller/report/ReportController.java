package com.nike.dnp.controller.report;

import com.nike.dnp.dto.report.ReportSaveDTO;
import com.nike.dnp.dto.report.ReportSearchDTO;
import com.nike.dnp.dto.report.ReportUpdateDTO;
import com.nike.dnp.entity.report.Report;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.report.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * The Class Report controller.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 7. 오후 2:37:43
 * @Description
 */
@Slf4j
@RestController
@Api(description = "리포트", tags = "REPORT")
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
    public SingleResult<Page<Report>> findAllReports(
            final ReportSearchDTO reportSearchDTO
    ) {
        log.info("ReportController.findAllReports");
        return responseService.getSingleResult(reportService.findAllPaging(reportSearchDTO));
    }


    /**
     * Save report single result.
     *
     * @param reportSaveDTO the report save dto
     * @return the single result
     * @author [이소정]
     * @CreatedOn 2020. 7. 8. 오후 5:48:17
     * @Description
     */
    @ApiOperation(
            value = "보고서 등록"
            , notes = REQUEST_CHARACTER
    )
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "보고서 등록")
    public SingleResult<Report> saveReport(
            @RequestBody final ReportSaveDTO reportSaveDTO
    ) {
        log.info("ReportController.saveReport");
        Report report = reportService.save(reportSaveDTO);
        return responseService.getSingleResult(report);
    }

    /**
     * Find report single result.
     *
     * @param reportSeq the report seq
     * @return the single result
     */
    @ApiOperation(
            value = "보고서 상세조회"
            , notes = REQUEST_CHARACTER
    )
    @GetMapping(name = " 보고서 상세조회", value = "/{reportSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Report> findReport(
            @ApiParam(name = "reportSeq", value = "보고서 시퀀스", defaultValue = "2") @PathVariable final Long reportSeq) {
        log.info("ReportController.findReport");
        return responseService.getSingleResult(reportService.findByReportSeq(reportSeq));
    }

    /**
     * Update report single result.
     *
     * @param reportUpdateDTO the report update dto
     * @return the single result
     * @author [이소정]
     * @CreatedOn 2020. 7. 9. 오후 6:18:36
     * @Description
     */
    @ApiOperation(value = "보고서 수정", notes = REQUEST_CHARACTER)
    @PutMapping(name = "보고서 수정", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Optional<Report>> updateReport(@ApiParam(name="reportUpdateDTO", value = "보고서 수정 Json") @RequestBody final ReportUpdateDTO reportUpdateDTO) {
        return responseService.getSingleResult(reportService.update(reportUpdateDTO));
    }

    /**
     * Delete report single result.
     *
     * @param reportSeq the report seq
     * @return the single result
     * @author [이소정]
     * @CreatedOn 2020. 7. 9. 오후 6:18:40
     * @Description
     */
    @ApiOperation(value="보고서 삭제", notes = REQUEST_CHARACTER)
    @DeleteMapping(name = "보고서 삭제", value = "/{reportSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Optional<Report>> deleteReport(
            @ApiParam(name = "reportSeq", value = "보고서 시퀀스", defaultValue = "2") @PathVariable final Long reportSeq) {
        log.info("ReportController.deleteReport");
        return responseService.getSingleResult(reportService.delete(reportSeq));
    }

}

