package com.nike.dnp.controller.main;

import com.nike.dnp.common.aspect.ValidField;
import com.nike.dnp.dto.calendar.CalendarSearchDTO;
import com.nike.dnp.dto.main.MainResultDTO;
import com.nike.dnp.entity.calendar.Calendar;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.calendar.CalendarService;
import com.nike.dnp.service.main.MainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * The Class Main controller.
 *
 * @author [이소정]
 * @since 2020. 7. 27. 오후 5:04:19
 * @apiNote 메인 페이지 controller
 */
@Slf4j
@RestController
@Api(description = "메인", tags = "MAIN")
@RequestMapping(value = "/api/main", name = "메인")
@RequiredArgsConstructor
public class MainController {

    /**
     * 응답 서비스
     *
     * @author [이소정]
     */
    private final ResponseService responseService;

    /**
     * The Main service
     *
     * @author [이소정]
     */
    private final MainService mainService;

    /**
     * The Calendar service
     *
     * @author [김형욱]
     */
    private final CalendarService calendarService;

    /**
     * The constant REQUEST_CHARACTER
     *
     * @author [이소정]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";


    /**
     * Find main info single result.
     *
     * @return the single result
     * @author [이소정]
     * @since 2020. 7. 27. 오후 5:38:53
     * @apiNote 메인(Home) 조회 (메인비쥬얼, asset, toolkit, foundation, report, notice, report, news 포함 calendar 제외)
     */
    @ApiOperation(
        value = "메인 조회(calendar 제외)"
        , notes = REQUEST_CHARACTER
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "메인 조회(calendar 제외)")
    public SingleResult<MainResultDTO> findMainInfo() {
        log.info("MainController.findMainInfo");
        return responseService.getSingleResult(mainService.findMainInfo());
    }

    /**
     * Calendar 조회
     *
     * @param calendarSearchDTO the calendar search dto
     * @return the single result
     * @author [김형욱]
     * @since 2020. 7. 2. 오전 8:55:29
     * @apiNote Calendar 조회
     */
    @ApiOperation(
            value = "Calendar 조회"
            , notes = REQUEST_CHARACTER + "\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "Calendar 조회", value = "/calendar")
    @ValidField
    public SingleResult<List<Calendar>> findAllCalendar(@Valid @ModelAttribute final CalendarSearchDTO calendarSearchDTO, @ApiIgnore final BindingResult result) {
        log.info("MainController.findAllCalendar");
        return responseService.getSingleResult(calendarService.findAll(calendarSearchDTO));
    }


}

