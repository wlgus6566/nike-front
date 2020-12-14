package com.nike.dnp.dto.order;


import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The Class Order product save dto.
 *
 * @author [윤태호]
 * @implNote
 * @since 2020. 6. 30. 오후 5:21:31
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class OrderSaveDTO extends BasicDTO {

//	/**
//	 * 상품 리스트
//	 *
//	 * @author [윤태호]
//	 */
//	@ApiModelProperty(name="goodsSeqList",value="상품 시퀀스", allowableValues="28,30,31")
//	@NotNull(message = "order.goodsSeqList")
//	private List<Long> goodsSeqList;
//
//	/**
//	 * 상품 수량
//	 *
//	 * @author [윤태호]
//	 */
//	@ApiModelProperty(name = "orderQuantityList", value = "상품 시퀀스별 수량", allowableValues = "10,20,30")
//	@NotNull(message = "order.orderQuantityList")
//	private List<Long> orderQuantityList;

	/**
	 * 총 금액
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name="totalAmount",value="총 금액",example = "100000")
	@NotNull(message = "order.totalAmount")
	private Long totalAmount;

	/**
	 * 주문 설명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name="orderDescription",value = "주문 코맨트",example = "코멘트")
	private String orderDescription;

	/**
	 * The Device
	 *
	 * @author [이소정]
	 */
	@ApiModelProperty(name = "device", value = "디바이스 정보", example = "PC", hidden = true)
	private String device;


	/**
	 * The Order product list
	 * 주문 상품 목록
	 *
	 * @author [이소정]
	 */
	@ApiModelProperty(name = "orderProductList", value = "주문 상품 목록")
	private List<OrderProductSaveDTO> orderProductList;
}
