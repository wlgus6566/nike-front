package com.nike.dnp.controller.calendar;

import com.nike.dnp.common.aspect.ValidField;
import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.calendar.CalendarDaySearchDTO;
import com.nike.dnp.dto.calendar.CalendarSaveDTO;
import com.nike.dnp.dto.calendar.CalendarSearchDTO;
import com.nike.dnp.dto.calendar.CalendarUpdateDTO;
import com.nike.dnp.entity.calendar.Calendar;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.exception.NotFoundHandleException;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.calendar.CalendarService;
import com.nike.dnp.util.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * 캘린더 컨트롤러
 *
 * @author [김형욱]
 * @apiNote 일정 Controller 작성
 * @since 2020. 6. 29. 오후 8:57:04
 */
@Slf4j
@RestController
@Api(description = "캘린더", tags = "CALENDAR")
@RequestMapping(value = "/api/calendar", name = "Calendar 관리")
@RequiredArgsConstructor
public class CalendarController {

    /**
     * 응답 서비스
     *
     * @author [이소정]
     */
    private final ResponseService responseService;


    /**
     * The constant REQUEST_CHARACTER
     *
     * @author [김형욱]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n필드명|설명|필수여부|데이터 타입(길이)\n" + "-|-|-|-\n";

    /**
     * The Calendar service
     *
     * @author [김형욱]
     */
    private final CalendarService calendarService;


    /**
     * Calendar 조회
     *
     * @param calendarSearchDTO the calendar search dto
     * @param result            the result
     * @return the single result
     * @author [김형욱]
     * @apiNote Calendar 조회
     * @since 2020. 7. 2. 오전 8:55:29
     */
    @ApiOperation(
            value = "Calendar 조회"
            , notes = REQUEST_CHARACTER + "\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "Calendar 조회")
    @ValidField
    public SingleResult<List<Calendar>> findAllContents(@Valid @ModelAttribute final CalendarSearchDTO calendarSearchDTO,@ApiIgnore final BindingResult result) {
        log.info("CalendarController.findAllContents");
        return responseService.getSingleResult(calendarService.findAll(calendarSearchDTO));
    }


    /**
     * Calendar 등록
     *
     * @param calendarSaveDTO the calendar save dto
     * @param result          the result
     * @return the single result
     * @author [윤태호]
     * @apiNote Calendar 등록
     * @since 2020. 7. 22. 오후 4:18:17
     */
    @ApiOperation(
            value = "Calendar 등록"
            , notes = REQUEST_CHARACTER + "\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @PostMapping(name = "Calendar 등록"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<Calendar> saveCalendar(@Valid @RequestBody final CalendarSaveDTO calendarSaveDTO,
                                               @ApiIgnore final BindingResult result) {
        log.info("CalendarController.saveCalendar");
        return responseService.getSingleResult(calendarService.save(calendarSaveDTO));
    }

    /**
     * Calendar 수정
     *
     * @param calendarSeq       the calendar seq
     * @param calendarUpdateDTO the calendar update dto
     * @param result            the result
     * @return the single result
     * @author [윤태호]
     * @apiNote Calendar 수정
     * @since 2020. 7. 22. 오후 4:18:17
     */
    @ApiOperation(
            value = "Calendar 수정"
            , notes = REQUEST_CHARACTER + "\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @PutMapping(value="/{calendarSeq}"
            , name = "Calendar 수정"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<Calendar> updateCalendar(@PathVariable @ApiParam(value="캘린더 시퀀스",name="calendarSeq",defaultValue = "4") final Long calendarSeq,
                                                 @Valid @RequestBody final CalendarUpdateDTO calendarUpdateDTO,
                                                 @ApiIgnore final BindingResult result) {
        log.info("CalendarController.updateCalendar");
        calendarUpdateDTO.setCalendarSeq(calendarSeq);
        return responseService.getSingleResult(calendarService.update(calendarUpdateDTO));
    }

    /**
     * Calendar 삭제
     *
     * @param calendarSeq the calendar seq
     * @return the single result
     * @author [윤태호]
     * @implNote
     * @apiNote Calendar 삭제
     * @since 2020. 7. 22. 오후 4:18:17
     */
    @ApiOperation(
            value = "Calendar 삭제"
            , notes = REQUEST_CHARACTER + "\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @DeleteMapping(value="/{calendarSeq}"
            , name = "Calendar 삭제"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Long> deleteCalendar(
            @PathVariable @ApiParam(value = "캘린더 시퀀스", name = "calendarSeq", example = "4") final Long calendarSeq
    ) {
        log.info("CalendarController.deleteCalendar");
        return responseService.getSingleResult(calendarService.delete(calendarSeq));
    }


    /**
     * 캘린더 상세 조회
     *
     * @param calendarSeq the calendar seq
     * @return the single result
     * @author [윤태호]
     * @apiNote 캘린더 상세 조회
     * @since 2020. 7. 22. 오후 4:18:17
     */
    @ApiOperation(value = "Calendar 상세보기", notes = REQUEST_CHARACTER + "\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n")
    @GetMapping(value="/view/{calendarSeq}",produces = MediaType.APPLICATION_JSON_VALUE)
    public SingleResult<Calendar> findById(@PathVariable @ApiParam(value="켈린더 시퀀스",name = "calendarSeq",example = "4") final Long calendarSeq){
        log.info("CalendarController.findById");
        return responseService.getSingleResult(calendarService.findById(calendarSeq).orElseThrow(() ->
                new NotFoundHandleException()
        ));
    }

    /**
     * 캘린더 오늘 조회
     *
     * @param calendarDaySearchDTO the calendar day search dto
     * @return the single result
     * @author [윤태호]
     * @apiNote 캘린더 오늘 조회
     * @since 2020. 7. 22. 오후 4:18:17
     */
    @ApiOperation(value = "Calendar 오늘 조회", notes = REQUEST_CHARACTER + "\n" + "[하위 Parameters 참조]\n\n\n\n" + "## Response ## \n" + "[하위 Model 참조]\n\n\n\n")
    @GetMapping(value = "/today", produces = MediaType.APPLICATION_JSON_VALUE)
    public SingleResult<List<Calendar>> findByAllToday(final CalendarDaySearchDTO calendarDaySearchDTO){
        log.info("CalendarController.findByAllToday");
        return responseService.getSingleResult(calendarService.findAllToday(calendarDaySearchDTO));
    }
}
