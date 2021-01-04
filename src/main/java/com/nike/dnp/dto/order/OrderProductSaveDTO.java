package com.nike.dnp.dto.order;


import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The Class Order product save dto.
 *
 * @author [이소정]
 * @since 2020. 12. 14. 오후 5:08:12
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class OrderProductSaveDTO extends BasicDTO {

	/**
	 * The Goods seq
	 * 상품 seq
	 *
	 * @author [이소정]
	 */
	@ApiModelProperty(name="goodsSeq",value="상품 seq", example="28")
	@NotNull(message = "order.goodsSeq")
	private Long goodsSeq;

	/**
	 * The Order quantity
	 * 주문 수량
	 *
	 * @author [이소정]
	 */
	@ApiModelProperty(name="orderQuantity",value="주문 수량", example="10")
	@NotNull(message = "order.goodsSeqList")
	private Long orderQuantity;

	/**
	 * 상품 설명
	 *
	 * @author [이소정]
	 */
	@ApiModelProperty(name = "productDescription", value = "상품 설명", example = "상품 설명입니다.")
	private String productDescription;

	// TODO lsj 2차오픈
//	/**
//	 * The File list
//	 *
//	 * @author [이소정]
//	 */
//	@ApiModelProperty(name = "fileList", value = "파일 목록")
//	private List<OrderProductFileSaveDTO> fileList;

}
