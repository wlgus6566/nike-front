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
	 * 굿즈 시퀀스
	 *
	 * @author [윤태호]
	 */
	private  Long goodsSeq;

	/**
	 * 주문 시퀀스
	 *
	 * @author [윤태호]
	 */
	private Long orderSeq;

	/**
	 * 주문 수량
	 *
	 * @author [윤태호]
	 */
	private Long orderQuantity;

	/**
	 * 에이젼시 시퀀스
	 *
	 * @author [윤태호]
	 */
	private Long agencySeq;
}
