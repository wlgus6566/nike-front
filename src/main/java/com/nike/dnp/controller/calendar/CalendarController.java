package com.nike.dnp.controller.calendar;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.calendar.CalendarSaveDTO;
import com.nike.dnp.dto.calendar.CalendarSearchDTO;
import com.nike.dnp.dto.code.CodeSaveDTO;
import com.nike.dnp.dto.code.CodeSearchDTO;
import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.entity.calendar.Calendar;
import com.nike.dnp.entity.code.Code;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.calendar.CalendarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

/**
 * CalendarController.
 *
 * @author [김형욱]
 * @CreatedOn 2020. 6. 29. 오후 8:57:04
 * @Description 일정 Controller 작성
 */
@Slf4j
@RestController
@Api(description = "CALENDAR", tags = "CALENDAR")
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
     * @return the single result
     * @author [김형욱]
     * @CreatedOn 2020. 7. 2. 오전 8:55:29
     * @Description
     */
    @ApiOperation(
            value = "Calendar 조회"
            , notes = REQUEST_CHARACTER + "\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "Calendar 조회")
    public SingleResult<List<Calendar>> getAllContents(
            final CalendarSearchDTO calendarSearchDTO
    ) {
        // Asset 메뉴 코드 넣어줌.
//        Calendar prove = new Calendar();
//        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withIgnorePaths("up","down");
//        Example<Calendar> example = Example.of(prove, exampleMatcher);

        return responseService.getSingleResult(calendarService.findAll(calendarSearchDTO));
    }


    /**
     * Calendar 등록
     *
     * @param calendarSaveDTO the calendar save dto
     * @return the single result
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
    public SingleResult<Calendar> saveCalendar(
            final @RequestBody CalendarSaveDTO calendarSaveDTO
    ) {
        return responseService.getSingleResult(calendarService.save(calendarSaveDTO));
    }

    /**
     * Calendar 수정
     *
     * @param calendarSaveDTO the calendar save dto
     * @return the single result
     */
    @ApiOperation(
            value = "Calendar 수정"
            , notes = REQUEST_CHARACTER + "\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @PutMapping(name = "Calendar 수정"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Optional<Calendar>> updateCalendar(
            final @RequestBody CalendarSaveDTO calendarSaveDTO
    ) {

        return responseService.getSingleResult(calendarService.update(calendarSaveDTO));
    }

    /**
     * Calendar 삭제
     *
     * @param calendarSeq the calendar seq
     * @return the single result
     */
    @ApiOperation(
            value = "Calendar 삭제"
            , notes = REQUEST_CHARACTER + "\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @DeleteMapping(name = "Calendar 삭제"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Long> deleteCalendar(
            final @RequestBody Long calendarSeq
    ) {
        return responseService.getSingleResult(calendarService.delete(calendarSeq));
    }
}
