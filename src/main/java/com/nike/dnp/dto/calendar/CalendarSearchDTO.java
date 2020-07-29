package com.nike.dnp.dto.calendar;

import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiParam;
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
public class CalendarSearchDTO extends BasicDTO {

    /**
     * 종료 일시
     *
     * @author [김형욱]
     */
    @ApiParam(value = "조회 년월", name = "yyyyMm", defaultValue = "2020.07")
    @NotBlank(message = "calendar.yyyymm")
    private String yyyyMm;


}
