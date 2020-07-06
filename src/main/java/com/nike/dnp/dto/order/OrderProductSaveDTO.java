package com.nike.dnp.dto.order;


import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * The Class Order product save dto.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 6. 30. 오후 5:21:31
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class OrderProductSaveDTO extends BasicDTO {
	/**
	 * 상품 리스트
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name="goodsSeqList",value="상품 시퀀스", allowableValues="28,30,31")
	List<Long> goodsSeqList;

	/**
	 * 상품 수량
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "orderQuantityList", value = "상품 시퀀스별 수량", allowableValues = "10,20,30")
	List<Long> orderQuantityList;

	/**
	 * 총 금액
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name="totalAmount",value="총 금액",example = "100000")
	Long totalAmount;

	/**
	 * 주문 설명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name="orderDescription",value = "주문 코맨트",example = "코멘트")
	String orderDescription;

	@Override
	public String toString() {
		return "OrderProductSaveDTO{" + "goodsSeqList=" + goodsSeqList + ", orderQuantityList=" + orderQuantityList + ", totalAmount=" + totalAmount + ", orderDescription='" + orderDescription + '\'' + '}';
	}
}
