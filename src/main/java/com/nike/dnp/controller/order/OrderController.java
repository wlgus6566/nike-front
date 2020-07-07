package com.nike.dnp.controller.order;


import com.nike.dnp.dto.order.OrderProductMappingSaveDTO;
import com.nike.dnp.dto.order.OrderProductSaveDTO;
import com.nike.dnp.dto.order.OrderSearchDTO;
import com.nike.dnp.entity.order.Order;
import com.nike.dnp.entity.order.OrderProductMapping;
import com.nike.dnp.entity.product.Product;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.order.OrderProductMappingService;
import com.nike.dnp.service.order.OrderService;
import com.nike.dnp.service.product.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Order Controller
 *
 * @author [윤태호]
 * @CreatedOn 2020. 6. 26. 오후 3:27:34
 * @Description
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
	 * Save order single result.
	 *
	 * @param orderProductSaveDTO the order product save dto
	 * @return the single result
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 1. 오후 2:48:06
	 * @Description
	 */
	@ApiOperation(value = "주문 등록", notes = BASIC_CHARACTER)
	@PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public SingleResult<Order> saveOrder(@RequestBody final OrderProductSaveDTO orderProductSaveDTO) {

		Order order = orderService.saveOrder(orderProductSaveDTO);

		for(int i = 0; i < orderProductSaveDTO.getGoodsSeqList().size(); i++){
			Product product = productService.findByGoodsSeq(orderProductSaveDTO.getGoodsSeqList().get(i));
			OrderProductMappingSaveDTO orderProductMappingSaveDTO = new OrderProductMappingSaveDTO();
			orderProductMappingSaveDTO.setGoodsSeq(orderProductSaveDTO.getGoodsSeqList().get(i));
			orderProductMappingSaveDTO.setOrderQuantity(orderProductSaveDTO.getOrderQuantityList().get(i));
			orderProductMappingSaveDTO.setOrderSeq(order.getOrderSeq());
			orderProductMappingSaveDTO.setAgencySeq(product.getAgencySeq());

			orderProductMappingService.saveOrderProductMapping(orderProductMappingSaveDTO);
		}
		orderProductMappingService.orderSheetSend(order);
		return responseService.getSingleResult(order);
	}


	/**
	 * List single result.
	 *
	 * @param orderSearchDTO the order search dto
	 * @return the single result
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 7. 오전 11:25:09
	 * @Description
	 */
	@ApiOperation(value = "주문내역", notes = REQUEST_CHARACTER + "beginDt|시작일|false|String\n" + "endDt|종료일|false|String\n")
	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public SingleResult<Page<OrderProductMapping>> list(final OrderSearchDTO orderSearchDTO) {
		return responseService.getSingleResult(orderProductMappingService.findPageOrder(orderSearchDTO));
	}


	/**
	 * View single result.
	 *
	 * @param orderGoodsSeq the order goods seq
	 * @return the single result
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 7. 오후 2:43:50
	 * @Description
	 */
	@ApiOperation(value = "주문 상세 내역", notes = BASIC_CHARACTER)
	@GetMapping(value = "/{orderGoodsSeq}")
	public SingleResult<OrderProductMapping> view(@ApiParam(name = "orderGoodsSeq", value = "주문 상품 시퀀스", defaultValue = "13") @PathVariable final Long orderGoodsSeq) {
		return responseService.getSingleResult(orderProductMappingService.findById(orderGoodsSeq));
	}


}

