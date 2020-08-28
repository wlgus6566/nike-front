package com.nike.dnp.dto.calendar;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Lob;
import java.time.LocalDateTime;

/**
 * CalendarDTO
 *
 * @author [이소정]
 * @since 2020. 6. 30. 오전 10:00:08
 * @implNote calendar Save DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class CalendarResultDTO extends BasicDTO {

    /**
    * 달력 시퀀스
    *
    * @author [오지훈]
    */
    @ApiModelProperty(name = "calendarSeq", value = "달력 시퀀스")
    private Long calendarSeq;

    /**
     * 메뉴 코드
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "calendarSectionCode", value = "메뉴 코드", required = true)
    private String calendarSectionCode;

    /**
     * 일정 명
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "scheduleName", value = "일정 명", required = true)
    private String scheduleName;

    /**
     * 내용
     *
     * @author [오지훈]
     */
    @Lob
    @ApiModelProperty(name = "contents", value = "내용", required = true)
    private String contents;

    /**
     * 시작 일시
     *
     * @author [오지훈]
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    @ApiModelProperty(name = "beginDt", value = "시작 일시")
    private LocalDateTime beginDt;

    /**
     * 종료 일시
     *
     * @author [오지훈]
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    @ApiModelProperty(name = "endDt", value = "종료 일시")
    private LocalDateTime endDt;

    /**
     * 최초 작성자
     *
     * @author [김형욱]
     */
    @ApiModelProperty(name = "registerSeq", value = "최초 작성자 시퀀스", hidden = true, required = true)
    private Long registerSeq;

    /**
     * 최종 수정자
     *
     * @author [김형욱]
     */
    @ApiModelProperty(name = "updaterSeq", value = "최종 수정자 시퀀스", hidden = true, required = true)
    private Long updaterSeq;

    /**
     * 최초 작성일
     *
     * @author [김형욱]
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    @ApiModelProperty(name = "registrationDt", value = "최초 작성일", hidden = true)
    private LocalDateTime registrationDt;

    /**
     * 최종 수정일
     *
     * @author [김형욱]
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    @ApiModelProperty(name = "updateDt", value = "최종 수정일", hidden = true)
    private LocalDateTime updateDt;

    /**
     * 종료날자 +1
     *
     * @author [이소정]
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    @ApiModelProperty(name = "registrationDt", value = "종료 일시 +1", hidden = false)
    private LocalDateTime viewEndDt;

    public LocalDateTime getViewEndDt() {
        return this.endDt.plusDays(1);
    }

}
