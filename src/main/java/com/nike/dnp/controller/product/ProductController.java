package com.nike.dnp.controller.product;

import com.nike.dnp.common.aspect.ValidField;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.product.*;
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
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * 상품 Controller
 *
 * @author [윤태호]
 * @since 2020. 7. 1. 오후 3:34:40
 * @apiNote 주문 상품관련 컨트롤러
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
	 * 상품 목록 조회[관리자]
	 *
	 * @param productSearchDTO the product search dto
	 * @return the all product
	 * @author [윤태호]
	 * @since 2020. 6. 24. 오후 12:15:13
	 * @apiNote
	 */
	@ApiOperation(value = "상품 목록 조회", notes = REQUEST_CHARACTER
			+ "category2Code|카테고리 2 코드|false|String\n"
			+ "category3Code|카테고리 3 코드|false|String\n"
			+ "agentSeq|에이전트 "
			+ "시퀀스|false|Integer\n"
			+ "exposureYn|노출여부|false|String\n"
			+ "keyword|키워드|false|String\n"
			+ "page|페이지|false|Integer\n"
			+ "size|사이즈|false|Integer\n")
	@GetMapping(value = "/list", name = "상품 목록 조회", produces = {MediaType.APPLICATION_JSON_VALUE})
	public SingleResult<Page<ProductResultDTO>> findPagesProduct(final ProductSearchDTO productSearchDTO) {
		log.info("ProductController.findPagesProduct");
		return responseService.getSingleResult(productService.findPagesProduct(productSearchDTO));
	}


	/**
	 * 상품 목록 조회[사용자] 
	 *
	 * @param category2Code        the category 2 code
	 * @param productUserSearchDTO the product user search dto
	 * @return the single result
	 * @author [윤태호]
	 * @since 2020. 7. 29. 오후 3:07:30
	 */
	@ApiOperation(value = "상품 목록 조회(유저용)", notes = REQUEST_CHARACTER
			+ "keyword|키워드|false|String\n"
			+ "page|페이지|false|Integer\n"
			+ "size|사이즈|false|Integer\n"
			+ "category3Code|카테고리 3 코드|false|String\n")
	@GetMapping(value = "{category2Code}/list", name = "상품 목록 조회", produces = {MediaType.APPLICATION_JSON_VALUE})
	public SingleResult<Page<ProductResultDTO>> findPagesProductCategory2(@PathVariable @ApiParam(name="category2Code",value="카테고리 2 코드",allowableValues = "SUBSIDIARY,NIKE_BY_YOU,CUSTOM23,MNQ",required = true) final String category2Code,
																		  final ProductUserSearchDTO productUserSearchDTO) {
		log.info("ProductController.findPagesProductCategory2");
		final ProductSearchDTO productSearchDTO = new ProductSearchDTO();
		productSearchDTO.setPage(productUserSearchDTO.getPage());
		productSearchDTO.setSize(productUserSearchDTO.getSize());
		productSearchDTO.setCategory2Code(category2Code);
		if(!ObjectUtils.isEmpty(productUserSearchDTO.getCategory3Code())){
			productSearchDTO.setCategory3Code(productUserSearchDTO.getCategory3Code());
		}
		productSearchDTO.setExposureYn("Y");
		productSearchDTO.setKeyword(productUserSearchDTO.getKeyword());

		return responseService.getSingleResult(productService.findPagesProduct(productSearchDTO));
	}

	/**
	 * 상품 상세 조회
	 *
	 * @param goodsSeq the goods seq
	 * @return the single result
	 * @author [윤태호]
	 * @since 2020. 6. 24. 오후 12:14:05
	 * @apiNote
	 */
	@ApiOperation(value = "상품 상세 조회", notes = REQUEST_CHARACTER + "goodsSeq|상품시퀀스|true|Integer\n")
	@GetMapping(value = "/{goodsSeq}", name = "상품상세조회", produces = {MediaType.APPLICATION_JSON_VALUE})
	public SingleResult<Product> findProduct(@ApiParam(name = "goodsSeq", value = "상품 시퀀스", defaultValue = "31") @PathVariable final Long goodsSeq) {
		log.info("ProductController.findProduct");
		return responseService.getSingleResult(productService.findByGoodsSeq(goodsSeq));
	}


	/**
	 * 다수 상품 상세 조회
	 *
	 * @param productViewListDTO the product view list dto
	 * @param result             the result
	 * @return the single result
	 * @author [윤태호]
	 * @since 2020. 6. 24. 오후 12:14:05
	 * @apiNote
	 */
	@ApiOperation(value = "다수 상품 상세 조회", notes = REQUEST_CHARACTER + "goodsSeq|상품시퀀스|true|Integer\n")
	@GetMapping(name = "상품상세조회", produces = MediaType.APPLICATION_JSON_VALUE)
	@ValidField
	public SingleResult<List<Product>> findbySearchProduct( @Valid @ModelAttribute final ProductViewListDTO productViewListDTO,
															@ApiIgnore final BindingResult result ) {
		log.info("ProductController.findbySearchProduct");
		return responseService.getSingleResult(productService.findBySearchId(productViewListDTO.getGoodsSeqList()));
	}

	/**
	 * 상품 등록
	 *
	 * @param productSaveDTO the product save dto
	 * @return the single result
	 * @throws IOException the io exception
	 * @author [윤태호]
	 * @since 2020. 6. 26. 오후 4:42:04
	 * @apiNote
	 */
	@ApiOperation(value = "상품 등록", notes = BASIC_CHARACTER)
	@PostMapping(name = "상품 등록", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ValidField
	public SingleResult<Product> saveProduct(@Valid @ApiParam(name = "productSaveDTO", value = "상품 등록 JSON") @RequestBody final ProductSaveDTO productSaveDTO,
											 @ApiIgnore final BindingResult result) {
		log.info("ProductController.saveProduct");
		return responseService.getSingleResult(productService.save(productSaveDTO));
	}

	/**
	 * 상품 수정
	 *
	 * @param productUpdateDTO the product update dto
	 * @return the single result
	 * @author [윤태호]
	 * @since 2020. 6. 23. 오후 5:28:44
	 * @apiNote
	 */
	@ApiOperation(value = "상품 수정", notes = BASIC_CHARACTER)
	@PutMapping(value="/{goodsSeq}",name = "상품 수정", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ValidField
	public SingleResult<Product> updateProduct(@PathVariable @ApiParam(name="goodsSeq",value="제품 시퀀스") final Long goodsSeq,
											   @Valid @ApiParam(name = "productUpdateDTO", value = "상품 수정 JSON") @RequestBody final ProductUpdateDTO productUpdateDTO,
											   @ApiIgnore final BindingResult result) throws IOException {
		log.info("ProductController.updateProduct");
		productUpdateDTO.setGoodsSeq(goodsSeq);
		return responseService.getSingleResult(productService.update(productUpdateDTO));
	}

	/**
	 * 상품 삭제
	 *
	 * @param goodsSeq the goods seq
	 * @return the single result
	 * @author [윤태호]
	 * @since 2020. 6. 23. 오후 5:28:44
	 * @apiNote
	 */
	@ApiOperation(value = "상품 삭제", notes = BASIC_CHARACTER)
	@DeleteMapping(value = "/{goodsSeq}", name = "상품 삭제", produces = MediaType.APPLICATION_JSON_VALUE)
	public SingleResult<Optional<Product>> delProduct(@ApiParam(name = "goodsSeq", value = "상품 시퀀스", defaultValue = "28") @PathVariable final Long goodsSeq) {
		log.info("ProductController.delProduct");
		final ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
		productUpdateDTO.setGoodsSeq(goodsSeq);
		productUpdateDTO.setUseYn("N");
		return responseService.getSingleResult(productService.delete(productUpdateDTO));
	}


	/**
	 * 다수 상품 삭제
	 *
	 * @param authUserDTO        the auth user dto
	 * @param productViewListDTO the product view list dto
	 * @param result             the result
	 * @return the single result
	 * @author [윤태호]
	 * @since 2020. 6. 26. 오후 3:08:11
	 * @apiNote
	 */
	@ApiOperation(value = "상품 삭제[배열]", notes = BASIC_CHARACTER)
	@DeleteMapping(name = "상품 삭제[배열]", produces = MediaType.APPLICATION_JSON_VALUE)
	@ValidField
	public SingleResult<Boolean> deleteProduct(@ApiIgnore @AuthenticationPrincipal final AuthUserDTO authUserDTO,
											   @Valid @ModelAttribute final ProductViewListDTO productViewListDTO,
											   @ApiIgnore final BindingResult result) {
		log.info("ProductController.deleteProduct");
		final List<Product> productList = productService.findBySearchId(productViewListDTO.getGoodsSeqList());
		final ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
		productUpdateDTO.setUseYn("N");
		productUpdateDTO.setUpdaterSeq(authUserDTO.getUserSeq());
		final boolean check = productService.deleteArray(
				productList,
				productUpdateDTO);
		return responseService.getSingleResult(check);
	}
}