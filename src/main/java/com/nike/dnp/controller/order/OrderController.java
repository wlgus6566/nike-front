package com.nike.dnp.controller.order;


import com.nike.dnp.common.aspect.ValidField;
import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.dto.file.FileUploadDTO;
import com.nike.dnp.dto.notice.EditorImageDto;
import com.nike.dnp.dto.order.*;
import com.nike.dnp.entity.order.OrderEntity;
import com.nike.dnp.entity.order.OrderProductMapping;
import com.nike.dnp.entity.product.Product;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.exception.FileHandleException;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.goodsbasket.GoodsBasketService;
import com.nike.dnp.service.order.OrderProductFileService;
import com.nike.dnp.service.order.OrderProductMappingService;
import com.nike.dnp.service.order.OrderService;
import com.nike.dnp.service.product.ProductService;
import com.nike.dnp.util.FileUtil;
import com.nike.dnp.util.MessageUtil;
import com.nike.dnp.util.S3Util;
import com.nike.dnp.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 주문 Controller
 *
 * @author [윤태호]
 * @apiNote
 * @since 2020. 6. 26. 오후 3:27:34
 */
@Slf4j
@RestController
@Api(description = "주문", tags = "ORDER")
@RequestMapping(value = "/api", name = "주문")
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
	 * The Goods basket service
	 *
	 * @author [윤태호]
	 */
	private final GoodsBasketService goodsBasketService;

	/**
	 * The Order product file service
	 *
	 * @author [이소정]
	 */
	private final OrderProductFileService orderProductFileService;

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
	 * 1차 오픈(1/8이후) 후 삭제 예정
	 *
	 * @param orderSaveDTO the order save dto
	 * @param result       the result
	 * @return the single result
	 * @author [윤태호]
	 * @implNote [method 설명]
	 * @apiNote 주문 등록
	 * @since 2020. 7. 1. 오후 2:48:06
	 */
	@ApiOperation(value = "주문 등록", notes = BASIC_CHARACTER)
	@PostMapping(value = "/order/save", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@ValidField
	public SingleResult<OrderEntity> saveOrder(
			@RequestBody @Valid final OrderSaveDTO_del orderSaveDTO
			, @ApiIgnore final BindingResult result) {
		log.info("OrderController.saveOrder");
		final OrderEntity orderEntity = orderService.saveOrder_del(orderSaveDTO);
		for (OrderProductSaveDTO_del orderProductSaveDTO : orderSaveDTO.getOrderProductList()) {
			final Product product = productService.findByGoodsSeq(orderProductSaveDTO.getGoodsSeq());
			OrderProductMapping orderProduct = orderProductMappingService.saveOrderProductMapping(
					OrderProductMappingSaveDTO.builder()
							.goodsSeq(orderProductSaveDTO.getGoodsSeq())
							.orderSeq(orderEntity.getOrderSeq())
							.agencySeq(product.getAgencySeq())
							.orderQuantity(orderProductSaveDTO.getOrderQuantity())
							.productDescription(orderProductSaveDTO.getProductDescription())
							.build()
			);

			// 장바구니 삭제
			goodsBasketService.deleteByGoodsSeqAndUserSeq(
					orderProductSaveDTO.getGoodsSeq(), SecurityUtil.currentUser().getUserSeq()
			);
		}
			orderProductMappingService.orderSheetSend_del(orderEntity);
			return responseService.getSingleResult(orderEntity);
	}


	/**
	 * 주문 등록
	 * 2차 오픈 내용
	 *
	 * @param orderSaveDTO the order save dto
	 * @param result       the result
	 * @return the single result
	 * @author [윤태호]
	 * @implNote [method 설명]
	 * @apiNote 주문 등록
	 * @since 2020. 7. 1. 오후 2:48:06
	 */
	@ApiOperation(value = "주문 등록(2차오픈)", notes = BASIC_CHARACTER)
	@PostMapping(value = "/order/save/2nd", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@ValidField
	public SingleResult<OrderEntity> saveOrder(
			@RequestBody @Valid final OrderSaveDTO orderSaveDTO
			, @ApiIgnore final BindingResult result) {
		log.info("OrderController.saveOrder");
		final OrderEntity orderEntity = orderService.saveOrder(orderSaveDTO);
		for (OrderProductSaveDTO orderProductSaveDTO : orderSaveDTO.getOrderProductList()) {
			final Product product = productService.findByGoodsSeq(orderProductSaveDTO.getGoodsSeq());
			OrderProductMapping orderProduct = orderProductMappingService.saveOrderProductMapping(
					OrderProductMappingSaveDTO.builder()
							.goodsSeq(orderProductSaveDTO.getGoodsSeq())
							.orderSeq(orderEntity.getOrderSeq())
							.agencySeq(product.getAgencySeq())
							.orderQuantity(orderProductSaveDTO.getOrderQuantity())
							.productDescription(orderProductSaveDTO.getProductDescription())
							.build()
			);

			// 파일 저장
			if (!ObjectUtils.isEmpty(orderProductSaveDTO.getFileList()) && !orderProductSaveDTO.getFileList().isEmpty()) {
				for (OrderProductFileSaveDTO orderProductFileSaveDTO : orderProductSaveDTO.getFileList()) {
					orderProductFileSaveDTO.setOrderGoodsSeq(orderProduct.getOrderGoodsSeq());
					orderProductFileService.saveOrderProductFile(orderProductFileSaveDTO);
				}
			}

			// 장바구니 삭제
			goodsBasketService.deleteByGoodsSeqAndUserSeq(
					orderProductSaveDTO.getGoodsSeq(), SecurityUtil.currentUser().getUserSeq()
			);
		}
		orderProductMappingService.orderSheetSend(orderEntity, orderSaveDTO.getRecipientList());
		return responseService.getSingleResult(orderEntity);
	}

	/**
	 * 주문내역 조회
	 *
	 * @param orderSearchDTO the order search dto
	 * @return the single result
	 * @author [윤태호]
	 * @implNote [method 설명]
	 * @apiNote 주문내역 조회
	 * @since 2020. 7. 7. 오전 11:25:09
	 */
	@ApiOperation(value = "주문내역", notes = REQUEST_CHARACTER + "beginDt|시작일|false|String\n" + "endDt|종료일|false|String\n")
	@GetMapping(value = "/mypage/order/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public SingleResult<Page<OrderEntity>> list(final OrderSearchDTO orderSearchDTO) {
		log.info("OrderController.list");
		return responseService.getSingleResult(orderService.findPageOrder(orderSearchDTO));
	}

	/**
	 * 주문 상세 내역
	 *
	 * @param orderSeq the order seq
	 * @return the single result
	 * @author [윤태호]
	 * @implNote [method 설명]
	 * @apiNote 주문 상세 내역
	 * @since 2020. 7. 7. 오후 2:43:50
	 */
	@ApiOperation(value = "주문 상세 내역", notes = BASIC_CHARACTER)
	@GetMapping(value = "/mypage/order/{orderSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
	public SingleResult<OrderEntity> view(@ApiParam(name = "orderSeq", value = "주문 시퀀스", defaultValue = "48") @PathVariable final Long orderSeq) {
		log.info("OrderController.view");
		return responseService.getSingleResult(orderService.findByOrderSeqAndUseYn(orderSeq, ServiceCode.YesOrNoEnumCode.Y.name()));
	}

	/**
	 * Find recipient list single result.
	 *
	 * @return the single result
	 * @author [이소정]
	 * @implNote 상위 2depth 수신자 목록 조회
	 * @since 2021. 1. 5. 오후 3:20:23
	 */
	@ApiOperation(value = "수신자 목록 조회", notes = BASIC_CHARACTER)
	@GetMapping(value = "/order/recipientList", produces = MediaType.APPLICATION_JSON_VALUE)
	public SingleResult<OrderRecipientResultDTO> findRecipientList() {
		log.info("OrderController.findRecipientList");
		return responseService.getSingleResult(orderService.findRecipientList());
	}

}