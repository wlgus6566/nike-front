package com.nike.dnp.dto.calendar;

import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * CalendarDTO
 *
 * @author [김형욱]
 * @implNote calendar Save DTO 작성
 * @since 2020. 6. 30. 오전 10:00:08
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class CalendarSearchDTO {

    /**
     * 종료 일시
     *
     * @author [김형욱]
     */
    @ApiParam(value = "조회 년월", name = "yyyyMm", defaultValue = "2020.07")
    @NotBlank(message = "calendar.yyyymm")
    private String yyyyMm;

    /**
     * The Start date
     */
    @ApiParam(value = "조회시작일", name = "yyyyMm",  hidden = true)
    private LocalDateTime startDate;

    /**
     * The End date
     */
    @ApiParam(value = "조회 종료일", name = "yyyyMm",  hidden = true)
    private LocalDateTime endDate;


}
