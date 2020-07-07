package com.nike.dnp.controller.product;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.product.ProductResultDTO;
import com.nike.dnp.dto.product.ProductSaveDTO;
import com.nike.dnp.dto.product.ProductSearchDTO;
import com.nike.dnp.dto.product.ProductUpdateDTO;
import com.nike.dnp.entity.product.Product;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.product.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

/**
 * ProductController
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 1. 오후 3:34:40
 * @Description 주문 상품관련 컨트롤러
 * @history [윤태호] [2020.06.17] [최초 작성]
 * @since 2020.06.17
 */
@Slf4j
@RestController
@Api(description = "상품 관리", tags = "PRODUCT")
@RequestMapping(value = "/api/product", name = "상품관리")
@AllArgsConstructor
public class ProductController {

	/**
	 * ProductService
	 */
	private final ProductService productService;

	/**
	 * ResponseService
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
	 * 상품 목록 조회
	 *
	 * @param productSearchDTO the product search dto
	 * @return the all product
	 * @author [윤태호]
	 * @CreatedOn 2020. 6. 24. 오후 12:15:13
	 * @Description
	 */
	@ApiOperation(value = "상품 목록 조회", notes = REQUEST_CHARACTER
			+ "category2code|카테고리 2 코드|false|String\n"
			+ "category3code|카테고리 3 코드|false|String\n"
			+ "agentSeq|에이전트 "
			+ "시퀀스|false|Integer\n"
			+ "exposureYn|노출여부|false|String\n"
			+ "keyword|키워드|false|String\n"
			+ "page|페이지|false|Integer\n"
			+ "size|사이즈|false|Integer\n")
	@GetMapping(value = "/list", name = "상품 목록 조회", produces = {MediaType.APPLICATION_JSON_VALUE})
	public SingleResult<Page<ProductResultDTO>> findPagesProduct(final ProductSearchDTO productSearchDTO) {
		return responseService.getSingleResult(productService.findPagesProduct(productSearchDTO));
	}

	/**
	 * 상품 상세 조회
	 *
	 * @param goodsSeq the goods seq
	 * @return the single result
	 * @author [윤태호]
	 * @CreatedOn 2020. 6. 24. 오후 12:14:05
	 * @Description
	 */
	@ApiOperation(value = "상품 상세 조회", notes = REQUEST_CHARACTER + "goodsSeq|상품시퀀스|true|Integer\n")
	@GetMapping(value = "/{goodsSeq}", name = "상품상세조회", produces = {MediaType.APPLICATION_JSON_VALUE})
	public SingleResult<Product> findProduct(@ApiParam(name = "goodsSeq", value = "상품 시퀀스", defaultValue = "31") @PathVariable final Long goodsSeq) {
		return responseService.getSingleResult(productService.findByGoodsSeq(goodsSeq));
	}


	/**
	 * 다수 상품 상세 조회
	 *
	 * @param goodsSeqList the goods seq list
	 * @return the single result
	 * @author [윤태호]
	 * @CreatedOn 2020. 6. 24. 오후 12:14:05
	 * @Description
	 */
	@ApiOperation(value = "다수 상품 상세 조회", notes = REQUEST_CHARACTER + "goodsSeq|상품시퀀스|true|Integer\n")
	@GetMapping(name = "상품상세조회", produces = MediaType.APPLICATION_JSON_VALUE)
	public SingleResult<List<Product>> findbySearchProduct(@ApiParam(name = "goodsSeqList", value = "상품 시퀀스", defaultValue = "29,30,31") @RequestParam final List<Long> goodsSeqList) {
		return responseService.getSingleResult(productService.findBySearchId(goodsSeqList));
	}

	/**
	 * 상품 등록
	 *
	 * @param productSaveDTO the product save dto
	 * @param authUserDTO    the auth user dto
	 * @return the single result
	 * @author [윤태호]
	 * @CreatedOn 2020. 6. 26. 오후 4:42:04
	 * @Description
	 */
	@ApiOperation(value = "상품 등록", notes = BASIC_CHARACTER)
	@PostMapping(name = "상품 등록", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SingleResult<Product> saveProduct(@ApiParam(name = "productSaveDTO", value = "상품 등록 JSON") @RequestBody final ProductSaveDTO productSaveDTO) {

		return responseService.getSingleResult(productService.save(productSaveDTO));
	}

	/**
	 * 상품 수정
	 *
	 * @param productUpdateDTO the product update dto
	 * @return the single result
	 * @author [윤태호]
	 * @CreatedOn 2020. 6. 23. 오후 5:28:44
	 * @Description
	 */
	@ApiOperation(value = "상품 수정", notes = BASIC_CHARACTER)
	@PutMapping(name = "상품 수정", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SingleResult<Optional<Product>> updateProduct(@ApiParam(name = "productUpdateDTO", value = "상품 수정 JSON") @RequestBody final ProductUpdateDTO productUpdateDTO) {


		return responseService.getSingleResult(productService.update(productUpdateDTO));
	}

	/**
	 * 상품 삭제
	 *
	 * @param goodsSeq the goods seq
	 * @return the single result
	 * @author [윤태호]
	 * @CreatedOn 2020. 6. 23. 오후 5:28:44
	 * @Description
	 */
	@ApiOperation(value = "상품 삭제", notes = BASIC_CHARACTER)
	@DeleteMapping(value = "/{goodsSeq}", name = "상품 삭제", produces = MediaType.APPLICATION_JSON_VALUE)
	public SingleResult<Optional<Product>> delProduct(@ApiParam(name = "goodsSeq", value = "상품 시퀀스", defaultValue = "28") @PathVariable final Long goodsSeq) {
		final ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
		productUpdateDTO.setGoodsSeq(goodsSeq);
		productUpdateDTO.setUseYn("N");
		return responseService.getSingleResult(productService.delete(productUpdateDTO));
	}


	/**
	 * 다수 상품 삭제
	 *
	 * @param goodsSeqList the goods seq list
	 * @param authUserDTO  the auth user dto
	 * @return the single result
	 * @author [윤태호]
	 * @CreatedOn 2020. 6. 26. 오후 3:08:11
	 * @Description
	 */
	@ApiOperation(value = "상품 삭제", notes = BASIC_CHARACTER)
	@DeleteMapping(name = "상품 삭제", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SingleResult<Boolean> deleteProduct(@ApiParam(name = "goodsSeqList", value = "상품 시퀀스", defaultValue = "29,30,31") @RequestParam final List<Long> goodsSeqList,
											   @ApiIgnore @AuthenticationPrincipal final AuthUserDTO authUserDTO) {

		List<Product> productList = productService.findBySearchId(goodsSeqList);

		final ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
		productUpdateDTO.setUseYn("N");
		productUpdateDTO.setUpdaterSeq(authUserDTO.getUserSeq());
		boolean check = productService.deleteArray(
				productList,
				productUpdateDTO);
		return responseService.getSingleResult(check);
	}
}