package com.nike.dnp.dto.calendar;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class CalendarCheckDTO {


	private LocalDateTime BeginDt;

	private Long count;

}
