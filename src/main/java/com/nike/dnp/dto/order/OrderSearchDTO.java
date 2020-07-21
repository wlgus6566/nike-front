package com.nike.dnp.dto.order;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiParam;
import lombok.*;

/**
 * The Class Order search dto.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 7. 오전 10:40:22
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class OrderSearchDTO extends SearchDTO {

	/**
	 * The Begin dt
	 *
	 * @author [윤태호]
	 */
	@ApiParam(value = "시작일", name = "beginDt", defaultValue = "2020-06-01")
	private String beginDt;

	/**
	 * The End dt
	 *
	 * @author [윤태호]
	 */
	@ApiParam(value = "종료일", name = "endDt", defaultValue = "2020-12-31")
	private String endDt;


	/**
	 * The User seq
	 *
	 * @author [윤태호]
	 */
	@ApiParam(hidden = true)
	private Long userSeq;

	/**
	 * To string string.
	 *
	 * @return the string
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 7. 오전 10:44:10
	 * @Description
	 */
	@Override
	public String toString() {
		return "OrderSearchDTO{" + "beginDt='" + beginDt + '\'' + ", endDt='" + endDt + '\'' + '}';
	}
}
