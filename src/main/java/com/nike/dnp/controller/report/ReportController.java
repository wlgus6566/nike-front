package com.nike.dnp.controller.report;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.report.ReportSaveDTO;
import com.nike.dnp.dto.report.ReportSearchDTO;
import com.nike.dnp.dto.report.ReportUpdateDTO;
import com.nike.dnp.entity.report.Report;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.auth.AuthService;
import com.nike.dnp.service.report.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * The Class Report controller.
 *
 * @author [이소정]
 * @since 2020. 7. 7. 오후 2:37:43
 * @apiNote 보고서 컨트롤러
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
     * The Auth service
     *
     * @author [오지훈]
     */
    private final AuthService authService;

    /**
     * The constant REQUEST_CHARACTER
     *
     * @author [이소정]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";

    /**
     * Find all reports single result.
     *
     * @param reportSearchDTO the report search dto
     * @param authUserDTO     the auth user dto
     * @return the single result
     * @author [이소정]
     * @implNote [method 설명]
     * @since 2020. 7. 29. 오후 6:47:30
     * @apiNote 보고서 목록 조회
     */
    @ApiOperation(
        value = "보고서 목록 조회"
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
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "보고서 목록 조회")
    public SingleResult<Page<Report>> findAllReports(
            final ReportSearchDTO reportSearchDTO,
            @ApiIgnore @AuthenticationPrincipal final AuthUserDTO authUserDTO
    ) {
        log.info("ReportController.findAllReports");
        return responseService.getSingleResult(reportService.findAllPaging(authUserDTO, reportSearchDTO));
    }


    /**
     * Save report single result.
     *
     * @param reportSaveDTO the report save dto
     * @return the single result
     * @author [이소정]
     * @since 2020. 7. 8. 오후 5:48:17
     * @apiNote 보고서 등록
     */
    @ApiOperation(
            value = "보고서 등록"
            , notes = REQUEST_CHARACTER
    )
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "보고서 등록")
    public SingleResult<Report> saveReport(
            @RequestBody final ReportSaveDTO reportSaveDTO,
            @ApiIgnore @AuthenticationPrincipal final AuthUserDTO authUserDTO
    ) {
        log.info("ReportController.saveReport");
        return responseService.getSingleResult(reportService.save(authUserDTO, reportSaveDTO));
    }

    /**
     * Find report single result.
     *
     * @param reportSeq the report seq
     * @return the single result
     * @author [이소정]
     * @implNote [method 설명]
     * @apiNote 보고서 등록
     * @since 2020. 7. 29. 오후 6:47:56
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
     * @since 2020. 7. 9. 오후 6:18:36
     * @apiNote 보고서 수정
     */
    @ApiOperation(value = "보고서 수정", notes = REQUEST_CHARACTER)
    @PutMapping(name = "보고서 수정", value = "/{reportSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Report> updateReport(
            @ApiParam(name="reportUpdateDTO", value = "보고서 수정 Json") @RequestBody final ReportUpdateDTO reportUpdateDTO,
            @ApiParam(name = "reportSeq", value = "보고서 시퀀스", defaultValue = "2") @PathVariable final Long reportSeq
    ) {
        reportUpdateDTO.setReportSeq(reportSeq);
        return responseService.getSingleResult(reportService.update(reportUpdateDTO));
    }

    /**
     * Delete report single result.
     *
     * @param reportSeq the report seq
     * @return the single result
     * @author [이소정]
     * @since 2020. 7. 9. 오후 6:18:40
     * @apiNote 보고서 삭제
     */
    @ApiOperation(value="보고서 삭제", notes = REQUEST_CHARACTER)
    @DeleteMapping(name = "보고서 삭제", value = "/{reportSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Report> deleteReport(
            @ApiParam(name = "reportSeq", value = "보고서 시퀀스", defaultValue = "2") @PathVariable final Long reportSeq) {
        log.info("ReportController.deleteReport");
        return responseService.getSingleResult(reportService.delete(reportSeq));
    }

    /**
     * Find by auth depth single result.
     *
     * @param authUserDTO the auth user dto
     * @return the single result
     * @author [오지훈]
     * @since 2020. 7. 21. 오후 5:14:14
     * @apiNote 그룹(권한) depth별 목록 조회
     */
    @ApiOperation(
            value = "보고서 그룹(권한) depth별 목록 조회"
            , notes = REQUEST_CHARACTER
    )
    @GetMapping(name = "그룹 목록 조회", value = "/groupList"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<AuthReturnDTO>> findByAuthDepth(
            @ApiIgnore @AuthenticationPrincipal final AuthUserDTO authUserDTO
    ) {
        log.info("AuthController.findByAuthDepth");
        return responseService.getSingleResult(
                authService.findByAuthDepth(authUserDTO.getAuthSeq(), "REPORT_UPLOAD", ServiceCode.MenuSkillEnumCode.REPORT.toString()));
    }
}

