package com.nike.dnp.controller.order;


import com.nike.dnp.dto.order.OrderProductSaveDTO;
import com.nike.dnp.entity.order.OrderProductMapping;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.order.OrderService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Order Controller
 *
 * @author [윤태호]
 * @CreatedOn 2020. 6. 26. 오후 3:27:34
 * @Description
 */
@Slf4j
@RestController
@Api(description = "주문", tags = "21_ORDER")
@RequestMapping(value = "/api/order/", name = "상품관리")
@AllArgsConstructor
public class OrderController {

	final OrderService orderService;

	final ResponseService responseService;

	@PostMapping(value="/save",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = MediaType.APPLICATION_JSON_VALUE)
	public SingleResult<OrderProductMapping> saveOrder(
			final @RequestBody OrderProductSaveDTO orderProductSaveDTO){
		log.debug("goodsList.toString() {}", orderProductSaveDTO.toString());

		// tb_order 에 값을 넣고...
		OrderProductMapping  orderProductMapping= orderService.saveOrder(orderProductSaveDTO);

		// agency seq 가 필요함..

		// tb_order_product_mapper 에 값을 넣고..




		return responseService.getSingleResult(orderProductMapping);
	}


}

