package com.nike.dnp.controller.agency;

import com.nike.dnp.dto.agency.AgencySaveDTO;
import com.nike.dnp.dto.agency.AgencyUpdateDTO;
import com.nike.dnp.entity.agency.Agency;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.agency.AgencyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * The Class Agency controller.
 *
 * @author [이소정]
 * @apiNote 메이전시 관리 컨트롤러
 * @since 2020. 7. 20. 오후 12:00:36
 */
@Slf4j
@RestController
@Api(description = "에이전시 관리", tags = "AGENCY")
@RequestMapping(value = "/api/agency", name = "에이전시")
@RequiredArgsConstructor
public class AgencyController {

    /**
     * 응답 서비스
     *
     * @author [이소정]
     */
    private final ResponseService responseService;

    /**
     * The Agency service
     *
     * @author [이소정]
     */
    private final AgencyService agencyService;

    /**
     * The constant REQUEST_CHARACTER
     *
     * @author [이소정]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";


    /**
     * Find all agency single result.
     *
     * @return the single result
     * @author [이소정]
     * @implNote 에이전시 목록 조회
     * @since 2020. 7. 20. 오후 12:09:11
     */
    @ApiOperation(
        value = "에이전시 목록 조회"
        , notes = REQUEST_CHARACTER
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "에이전시 목록 조회")
    public SingleResult<List<Agency>> findAllAgency () {
        return responseService.getSingleResult(agencyService.findAll());
    }

    /**
     * Save contents single result.
     *
     * @param agencySaveDTO the agency save dto
     * @return the single result
     * @author [이소정]
     * @implNote 에이전시 등록
     * @since 2020. 7. 20. 오후 12:12:19
     */
    @ApiOperation(
            value = "에이전시 등록"
            , notes = REQUEST_CHARACTER
    )
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "에이전시 등록")
    public SingleResult<Agency> saveContents(
            @RequestBody final AgencySaveDTO agencySaveDTO
    ) {
        return responseService.getSingleResult(agencyService.save(agencySaveDTO));
    }

    /**
     * Find agency single result.
     *
     * @param agencySeq the agency seq
     * @return the single result
     * @author [이소정]
     * @implNote 에이전시 상세조회
     * @since 2020. 7. 20. 오후 12:10:50
     */
    @ApiOperation(
            value = "에이전시 상세조회"
            , notes = REQUEST_CHARACTER
    )
    @GetMapping(name = " 에이전시 상세조회", value = "/{agencySeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Agency> findAgency(
            @ApiParam(name = "agencySeq", value = "에이전시 시퀀스", defaultValue = "1", required = true) @PathVariable final Long agencySeq) {
        return  responseService.getSingleResult(agencyService.findByAgencySeq(agencySeq));
    }

    /**
     * Update agency single result.
     *
     * @param agencySeq       the agency seq
     * @param agencyUpdateDTO the agency update dto
     * @return the single result
     * @author [이소정]
     * @implNote 에이전시 수정
     * @since 2020. 7. 20. 오후 2:16:42
     */
    @ApiOperation(value = "에이전시 수정", notes = REQUEST_CHARACTER)
    @PutMapping(name = "에이전시 수정", value = "/{agencySeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Agency> updateAgency(
            @ApiParam(name = "agencySeq", value = "에이전시 시퀀스", defaultValue = "1", required = true) @PathVariable final Long agencySeq,
            @RequestBody final AgencyUpdateDTO agencyUpdateDTO
    ) {
        agencyUpdateDTO.setAgencySeq(agencySeq);
        return responseService.getSingleResult(agencyService.update(agencyUpdateDTO));
    }

    /**
     * Delete agency single result.
     *
     * @param agencySeq the agency seq
     * @return the single result
     * @author [이소정]
     * @implNote 에이전시 삭제
     * @since 2020. 7. 20. 오후 2:21:32
     */
    @ApiOperation(value="에이전시 삭제", notes = REQUEST_CHARACTER)
    @DeleteMapping(name = "에이전시 삭제", value = "/{agencySeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Agency> deleteAgency(
            @ApiParam(name = "agencySeq", value = "에이전시 시퀀스", defaultValue = "1", required = true) @PathVariable final Long agencySeq) {
        log.info("AgencyController.deleteAgency");
        return responseService.getSingleResult(agencyService.delete(agencySeq));
    }

}

