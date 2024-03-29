package com.nike.dnp.dto.calendar;

import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * CalendarDTO
 *
 * @author [김형욱]
 * @since 2020. 6. 30. 오전 10:00:08
 * @implNote calendar Save DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class CalendarUpdateDTO extends BasicDTO {

    /**
     * 달력 PK
     *
     * @author [김형욱]
     */
    @ApiModelProperty(name = "calendarSeq", value = "달력 시퀀스",hidden = true)
    private Long calendarSeq;

    /**
     * 달력 구분 공통코드
     *
     * @author [김형욱]
     */
    @ApiModelProperty(name = "calendarSectionCode", value = "달력 구분 공통코드(교육: EDUCATION, 캠페인 : CAMPAIGN, 기타 공개일정: ETC )", required = true, example = "EDUCATION")
    @NotBlank(message = "calendar.calendarSectionCode")
    private String calendarSectionCode;

    /**
     * 일정명
     *
     * @author [김형욱]
     */
    @ApiModelProperty(name = "scheduleName", value = "일정 명", required = true, example = "2020.07.07 이벤트")
    @NotBlank(message = "calendar.scheduleName")
    private String scheduleName;

    /**
     * 내용
     *
     * @author [김형욱]
     */
    @ApiModelProperty(name = "contents", value = "내용", example = "내용입력입니다.")
    private String contents;

    /**
     * 시작 일시
     *
     * @author [김형욱]
     */
    @ApiModelProperty(name = "beginDt", value = "시작 일시", required = true, example = "2020.07.07")
    @NotBlank(message = "calendar.beginDt")
    private String beginDt;

    /**
     * 종료 일시
     *
     * @author [김형욱]
     */
    @ApiModelProperty(name = "endDt", value = "종료 일시", required = true, example = "2020.07.08")
    @NotBlank(message = "calendar.endDt")
    private String endDt;


}
