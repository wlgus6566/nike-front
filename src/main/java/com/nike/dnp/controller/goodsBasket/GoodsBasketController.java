package com.nike.dnp.controller.goodsBasket;

import com.nike.dnp.dto.goodsBasket.GoodsBasketSaveDTO;
import com.nike.dnp.dto.goodsBasket.GoodsBasketSaveListDTO;
import com.nike.dnp.entity.goodsBasket.GoodsBasket;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.goodsBasket.GoodsBasketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping(value = "/api/goodsBasket", name = "장바구니")
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
	public SingleResult<GoodsBasket> saveBasket(@RequestBody final GoodsBasketSaveDTO goodsBasketSaveDTO) {
		GoodsBasket goodsBasket = goodsBasketService.saveBasket(goodsBasketSaveDTO);
		return responseService.getSingleResult(goodsBasket);
	}

	/**
	 * Save basket list single result.
	 *
	 * @param goodsBasketSaveListDTO the goods basket save list dto
	 * @return the single result
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 6. 오전 11:38:25
	 * @Description
	 */
	@ApiOperation(value = "장바구니 다수 등록", notes = BASIC_CHARACTER)
	@PostMapping(value = "/saveList", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SingleResult<List<GoodsBasket>> saveBasketList(@RequestBody final GoodsBasketSaveListDTO goodsBasketSaveListDTO) {
		return responseService.getSingleResult(goodsBasketService.saveBasketList(goodsBasketSaveListDTO));
	}


	/**
	 * Find all single result.
	 *
	 * @return the single result
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 2. 오후 6:20:53
	 * @Description
	 */
	@ApiOperation(value = "장바구니 조회", notes = BASIC_CHARACTER)
	@GetMapping(value = "/list", produces = {MediaType.APPLICATION_JSON_VALUE})
	public SingleResult<List<GoodsBasket>> findAll(){
		return responseService.getSingleResult(goodsBasketService.findByAll());
	}


	/**
	 * Delete basket common result.
	 *
	 * @param goodsBasketSeq the goods basket seq
	 * @return the common result
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 3. 오후 12:09:20
	 * @Description
	 */
	@ApiOperation(value = "장바구니 삭제", notes = BASIC_CHARACTER)
	@DeleteMapping(value = "/{goodsBasketSeq}",produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResult deleteBasket(final @ApiParam(name = "goodsBasketSeq", value = "장바구니 시퀀스", defaultValue = "6") @PathVariable Long goodsBasketSeq) {
		goodsBasketService.deleteBasket(goodsBasketSeq);
		return responseService.getSuccessResult();
	}


}
