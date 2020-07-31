package com.nike.dnp.controller.order;


import com.nike.dnp.common.aspect.ValidField;
import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.order.OrderProductMappingSaveDTO;
import com.nike.dnp.dto.order.OrderProductSaveDTO;
import com.nike.dnp.dto.order.OrderSearchDTO;
import com.nike.dnp.entity.order.Order;
import com.nike.dnp.entity.order.OrderProductMapping;
import com.nike.dnp.entity.product.Product;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.order.OrderProductMappingService;
import com.nike.dnp.service.order.OrderService;
import com.nike.dnp.service.product.ProductService;
import com.nike.dnp.util.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * 주문 Controller
 *
 * @author [윤태호]
 * @since 2020. 6. 26. 오후 3:27:34
 * @apiNote
 */
@Slf4j
@RestController
@Api(description = "주문", tags = "ORDER")
@RequestMapping(value = "/api/order/", name = "주문")
@AllArgsConstructor
public class OrderController {

	/**
	 * The Order service
	 *
	 * @author [윤태호]
	 */
	private final OrderService orderService;

	/**
	 * The Response service
	 *
	 * @author [윤태호]
	 */
	private final ResponseService responseService;

	/**
	 * The Product service
	 *
	 * @author [윤태호]
	 */
	private final ProductService productService;


	/**
	 * The Order product mapping service
	 *
	 * @author [윤태호]
	 */
	private final OrderProductMappingService orderProductMappingService;

	/**
	 * The constant REQUEST_CHARACTER
	 *
	 * @author [윤태호]
	 */
	private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";

	/**
	 * The constant BASIC_CHARACTER
	 *
	 * @author [윤태호]
	 */
	private static final String BASIC_CHARACTER = "## Request ## \n" + "[하위 Parameters 참조] \n" + "## Request ## \n" + "[하위 Model 참조]\n\n";

	/**
	 * 주문 등록
	 *
	 * @param orderProductSaveDTO the order product save dto
	 * @return the single result
	 * @author [윤태호]
	 * @since 2020. 7. 1. 오후 2:48:06
	 * @apiNote
	 */
	@ApiOperation(value = "주문 등록", notes = BASIC_CHARACTER)
	@PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@ValidField
	public SingleResult<Order> saveOrder(@RequestBody @Valid final OrderProductSaveDTO orderProductSaveDTO,
										 @ApiIgnore final BindingResult result) {
		log.info("OrderController.saveOrder");
		final List<Long> goodsSeqList = orderProductSaveDTO.getGoodsSeqList();
		final List<Long> orderQuantityList = orderProductSaveDTO.getOrderQuantityList();
		final int goodsSeqListSize = goodsSeqList.size();
		final int orderQuantityListSize = orderQuantityList.size();
		if(goodsSeqListSize == orderQuantityListSize){
			final Order order = orderService.saveOrder(orderProductSaveDTO);
			for(int i = 0; i < goodsSeqListSize; i++){
				final Long goodsSeq = goodsSeqList.get(i);
				final Long orderQuantity = orderQuantityList.get(i);
				final Product product = productService.findByGoodsSeq(goodsSeq);

				/*final OrderProductMappingSaveDTO orderProductMappingSaveDTO = new OrderProductMappingSaveDTO();
				orderProductMappingSaveDTO.setGoodsSeq(goodsSeq);
				orderProductMappingSaveDTO.setOrderQuantity(orderQuantity);
				orderProductMappingSaveDTO.setOrderSeq(order.getOrderSeq());
				orderProductMappingSaveDTO.setAgencySeq(product.getAgencySeq());
				orderProductMappingService.saveOrderProductMapping(orderProductMappingSaveDTO);*/

				orderProductMappingService.saveOrderProductMapping(
						OrderProductMappingSaveDTO.builder()
								.goodsSeq(goodsSeq)
								.orderSeq(order.getOrderSeq())
								.agencySeq(product.getAgencySeq())
								.orderQuantity(orderQuantity)
								.build()
				);
			}
			orderProductMappingService.orderSheetSend(order);
			return responseService.getSingleResult(order);
		}else{
			throw new CodeMessageHandleException(
					FailCode.ConfigureError.INVALID_ORDER.name()
					, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_ORDER.name()));
		}
	}


	/**
	 * 주문내역 조회
	 *
	 * @param orderSearchDTO the order search dto
	 * @return the single result
	 * @author [윤태호]
	 * @since 2020. 7. 7. 오전 11:25:09
	 * @apiNote
	 */
	@ApiOperation(value = "주문내역", notes = REQUEST_CHARACTER + "beginDt|시작일|false|String\n" + "endDt|종료일|false|String\n")
	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public SingleResult<Page<OrderProductMapping>> list(final OrderSearchDTO orderSearchDTO) {
		log.info("OrderController.list");
		return responseService.getSingleResult(orderProductMappingService.findPageOrder(orderSearchDTO));
	}


	/**
	 * 주문 상세 내역
	 *
	 * @param orderGoodsSeq the order goods seq
	 * @return the single result
	 * @author [윤태호]
	 * @since 2020. 7. 7. 오후 2:43:50
	 * @apiNote
	 */
	@ApiOperation(value = "주문 상세 내역", notes = BASIC_CHARACTER)
	@GetMapping(value = "/{orderGoodsSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
	public SingleResult<OrderProductMapping> view(@ApiParam(name = "orderGoodsSeq", value = "주문 상품 시퀀스", defaultValue = "13") @PathVariable final Long orderGoodsSeq) {
		log.info("OrderController.view");
		return responseService.getSingleResult(orderProductMappingService.findById(orderGoodsSeq));
	}


}

