package com.nike.dnp.controller.order;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.order.ProductSaveDTO;
import com.nike.dnp.dto.order.ProductSearchDTO;
import com.nike.dnp.entity.order.Product;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.order.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * ProductController
 *
 * @author [윤태호]
 * @Description 주문 상품관련 컨트롤러
 * @history [윤태호] [2020.06.17] [최초 작성]
 * @since 2020.06.17
 *
 *
 */
@Slf4j
@RestController
@Api(description = "상품 관리",tags="4_ORDER_PRODUCT")
@RequestMapping(value="/api/order/product",name="상품관리")
@AllArgsConstructor
public class ProductController {

	/**
	 *  ProductService
	 */
	private final ProductService productService;

	/**
	 * ResponseService
	 */
	private final ResponseService responseService;

	/**

	 */
	private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";

	/**
	 * 상품품 록 조회
	 *
	 * @param category1code    the category 1 code
	 * @param productSearchDTO the product search dto
	 * @param authUserDTO      the auth user dto
	 * @return the all product
	 */
	@ApiOperation(
			value="상품 목록 조회"
			, notes= REQUEST_CHARACTER
			+ "category1code|카테고리 1 코드|true|String\n"
			+"category2code|카테고리 2 코드|false|String\n"
			+"category3code|카테고리 3 코드|false|String\n"
			+"keyword|키워드|false|String\n"
			+"page|페이지|false|Integer\n"
			+"size|사이즈|false|Integer\n"
	)
	@GetMapping(value="/{category1code}", produces = {MediaType.APPLICATION_JSON_VALUE},name="상품 목록 조회")
	public SingleResult<List<Product>> getAllProduct(
			final @PathVariable(name="category1code") String category1code,
			final ProductSearchDTO productSearchDTO,
			final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO){
		log.debug("productSearchDTO.toString() > ", productSearchDTO.toString());
		log.debug("category1code > ", category1code);
		if(authUserDTO!= null){
			log.debug("authUserDTO.toString() > ", authUserDTO.toString());
		}
		//TODO [yth] 리스트 조회 구축
		return responseService.getSingleResult(productService.findAll());
	}

	/**
	 * 상품 등록
	 *
	 * @param category1code  the category 1 code
	 * @param productSaveDTO the product save dto
	 * @param authUserDTO    the auth user dto
	 * @return the single result
	 */
	@ApiOperation(
			value="상품 등록",
			notes = "## Request ## \n"
			+"[하위 Parameters 참조] \n"
			+"## Request ## \n"
			+"[하위 Model 참조]\n\n"
	)
	@PostMapping(value="/{category1code}",name="상품 등록")
	public SingleResult<Product> saveProduct(
			final @PathVariable(name = "category1code") String category1code
			, final ProductSaveDTO productSaveDTO
			, final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO){

		log.debug("productSaveDTO.getOriginalImg().getSize() > ", productSaveDTO.getOriginalImg().getSize());
		log.debug("productSaveDTO.toString() > ", productSaveDTO.toString());

		productSaveDTO.setRegisterSeq(Long.parseLong("1"));
		productSaveDTO.setUseYn("Y");
		return responseService.getSingleResult(productService.save(productSaveDTO));
	}



}
