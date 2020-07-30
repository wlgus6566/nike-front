package com.nike.dnp.dto.calendar;

import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiParam;
import lombok.*;

/**
 * The Class Calendar day search dto.
 *
 * @author [윤태호]
 * @since 2020. 7. 22. 오후 4:16:45
 * @implNote
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class CalendarDaySearchDTO extends BasicDTO {

	/**
	 * 조회 년월일
	 *
	 * @author [윤태호]
	 */
	@ApiParam(value = "조회 년월일", name = "searchDt", defaultValue = "2020.07.12")
	private String searchDt;

}
