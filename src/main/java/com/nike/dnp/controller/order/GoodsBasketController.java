package com.nike.dnp.controller.order;

import com.nike.dnp.dto.order.GoodsBasketSaveDTO;
import com.nike.dnp.entity.order.GoodsBasket;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.order.GoodsBasketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class Basket controller.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 2. 오후 4:22:03
 * @Description
 */
@Slf4j
@RestController
@Api(description = "장바구니", tags = "22_BASKET")
@RequestMapping(value = "/api/order/basket", name = "장바구니")
@AllArgsConstructor
public class GoodsBasketController {


	/**
	 * goodsBasketService
	 *
	 * @author [윤태호]
	 */
	private final GoodsBasketService goodsBasketService;

	/**
	 * responseService
	 *
	 * @author [윤태호]
	 */
	private final ResponseService responseService;

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
	 * Save basket single result.
	 *
	 * @param goodsBasketSaveDTO the goods basket save dto
	 * @return the single result
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 2. 오후 4:31:14
	 * @Description
	 */
	@ApiOperation(value = "장바구니 등록", notes = BASIC_CHARACTER)
	@PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public SingleResult<GoodsBasket> saveBasket(final @RequestBody GoodsBasketSaveDTO goodsBasketSaveDTO) {

		GoodsBasket goodsBasket = goodsBasketService.saveBasket(goodsBasketSaveDTO);

		return responseService.getSingleResult(goodsBasket);
	}

	/*public SingleResult<T> findByGoodsBasket(@){
		goodsBasketService.findByAll()
	}*/


}
