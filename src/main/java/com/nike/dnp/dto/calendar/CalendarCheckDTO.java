package com.nike.dnp.dto.calendar;

import lombok.*;

import java.time.LocalDateTime;

/**
 * The Class Calendar check dto.
 *
 * @author [윤태호]
 * @since 2020. 8. 7. 오후 6:13:32
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class CalendarCheckDTO {


	/**
	 * The Begin dt
	 *
	 * @author [윤태호]
	 */
	private LocalDateTime BeginDt;

	/**
	 * The Count
	 *
	 * @author [윤태호]
	 */
	private Long count;

}
