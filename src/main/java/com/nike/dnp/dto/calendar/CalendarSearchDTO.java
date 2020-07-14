package com.nike.dnp.dto.calendar;

import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiParam;
import lombok.*;

/**
 * CalendarDTO
 *
 * @author [김형욱]
 * @CreatedOn 2020. 6. 30. 오전 10:00:08
 * @Description calendar Save DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class CalendarSearchDTO extends BasicDTO {

    /**
     * 달력 구분 공통코드
     *
     * @author [김형욱]
     */
//    @ApiModelProperty(name = "calendarSectionCode", value = "달력 구분 공통코드")
//    private String calendarSectionCode;

    /**
     * 일정명
     *
     * @author [김형욱]
     */
//    @ApiModelProperty(name = "scheduleName", value = "일정 명")
//    private String scheduleName;

    /**
     * 내용
     *
     * @author [김형욱]
     */
//    @ApiModelProperty(name = "contents", value = "내용")
//    private String contents;

    /**
     * 시작 일시
     *
     * @author [김형욱]
     */
//    @ApiModelProperty(name = "beginDt", value = "시작 일시")
//    private LocalDateTime beginDt;

    /**
     * 종료 일시
     *
     * @author [김형욱]
     */
    @ApiParam(value = "조회 년월", name = "yyyyMm", defaultValue = "2020.07")
    private String yyyyMm;


}
