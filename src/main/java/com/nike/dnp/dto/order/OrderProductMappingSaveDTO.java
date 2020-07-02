package com.nike.dnp.dto.order;

import lombok.*;

/**
 * The Class Order product mapping save dto.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 1. 오후 12:00:47
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class OrderProductMappingSaveDTO {

	/**
	 * The Goods seq
	 *
	 * @author [윤태호]
	 */
	private  Long goodsSeq;

	/**
	 * The Order seq
	 *
	 * @author [윤태호]
	 */
	private Long orderSeq;

	/**
	 * The Order quantity
	 *
	 * @author [윤태호]
	 */
	private Long orderQuantity;

	/**
	 * The Agency seq
	 *
	 * @author [윤태호]
	 */
	private Long agencySeq;
}
