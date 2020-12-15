package com.nike.dnp.dto.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class Order product mapping save dto.
 *
 * @author [윤태호]
 * @implNote
 * @since 2020. 7. 1. 오후 12:00:47
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
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

	/**
	 * 상품 설명
	 *
	 * @author [이소정]
	 */
	private String productDescription;

	/**
	 * Instantiates a new Order product mapping save dto.
	 *
	 * @param goodsSeq           the goods seq
	 * @param orderSeq           the order seq
	 * @param agencySeq          the agency seq
	 * @param orderQuantity      the order quantity
	 * @param productDescription the product description
	 * @author [오지훈]
	 * @implNote 생성자 주입 *
	 * @since 2020. 7. 31. 오후 3:56:29
	 */
	@Builder
	public OrderProductMappingSaveDTO (
			final Long goodsSeq
			, final Long orderSeq
			, final Long agencySeq
			, final Long orderQuantity
			, final String productDescription
	) {
		this.goodsSeq = goodsSeq;
		this.orderSeq = orderSeq;
		this.agencySeq = agencySeq;
		this.orderQuantity = orderQuantity;
		this.productDescription = productDescription;
	}

}
