package com.nike.dnp.controller.alarm;

import com.nike.dnp.dto.SearchDTO;
import com.nike.dnp.dto.alarm.AlarmResultDTO;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.alarm.AlarmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class Alarm controller.
 *
 * @author [이소정]
 * @apiNote 알림 컨트롤러
 * @since 2020. 7. 24. 오후 7:41:14
 */
@Slf4j
@RestController
@Api(description = "알림", tags = "ALARM")
@RequestMapping(value = "/api/alarm", name = "알림")
@RequiredArgsConstructor
public class AlarmController {

    /**
     * 응답 서비스
     *
     * @author [이소정]
     */
    private final ResponseService responseService;

    /**
     * The Alarm service
     *
     * @author [이소정]
     */
    private final AlarmService alarmService;

    /**
     * The constant REQUEST_CHARACTER
     *
     * @author [이소정]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";

    /**
     * Find all alarm single result.
     *
     * @param searchDTO   알림 조회 dto
     * @return the single result
     * @author [이소정]
     * @implNote 알림 목록 조회
     * @since 2020. 7. 29. 오후 6:48:34
     */
    @ApiOperation(
        value = "알림 목록 조회"
        , notes = REQUEST_CHARACTER
        + "page|페이지|false|Integer|0부터 시작\n"
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
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "알림 목록 조회")
    public SingleResult<Page<AlarmResultDTO>> findAllAlarm(
            final SearchDTO searchDTO
    ) {
        return responseService.getSingleResult(alarmService.findAllPaging(searchDTO));
    }


}

