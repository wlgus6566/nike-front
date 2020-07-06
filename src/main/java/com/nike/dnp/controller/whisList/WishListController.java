package com.nike.dnp.controller.whisList;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.wishList.WishListResultDTO;
import com.nike.dnp.dto.wishList.WishListSearchDTO;
import com.nike.dnp.entity.wishList.WishList;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.wishList.WishListService;
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


/**
 * WishListController
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 3. 오후 3:38:34
 * @Description
 */
@Slf4j
@RestController
@Api(description = "위시리스트", tags = "23_wishList")
@RequestMapping(value = "/api/wishlist", name = "위시리스트")
@AllArgsConstructor
public class WishListController {

	/**
	 * The constant BASIC_CHARACTER
	 *
	 * @author [윤태호]
	 */
	private static final String BASIC_CHARACTER = "## Request ## \n" + "[하위 Parameters 참조] \n" + "## Request ## \n" + "[하위 Model 참조]\n\n";

	/**
	 * The constant REQUEST_CHARACTER
	 *
	 * @author [윤태호]
	 */
	private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";


	/**
	 * The Response service
	 *
	 * @author [윤태호]
	 */
	private final ResponseService responseService;

	/**
	 * The Wish list service
	 *
	 * @author [윤태호]
	 */
	private final WishListService wishListService;


	/**
	 * 위시리스트 등록
	 *
	 * @param goodsSeq the goods seq
	 * @return the single result
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 3. 오후 3:38:34
	 * @Description
	 */
	@ApiOperation(value = "위시리스트 등록", notes = BASIC_CHARACTER)
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public SingleResult<WishListResultDTO> saveWishList(@ApiParam(name = "goodsSeq", value = "제품 시퀀스", defaultValue = "4") @RequestParam final Long goodsSeq) {
		return responseService.getSingleResult(WishListResultDTO.ofSave(wishListService.save(goodsSeq)));
	}

	/**
	 * 위시 리스트 조회
	 *
	 * @param authUserDTO the auth user dto
	 * @return the single result
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 3. 오후 3:59:10
	 * @Description
	 */
	@ApiOperation(value = "위시리스트 조회", notes = REQUEST_CHARACTER
			+ "page|페이지 번호|true|int\n"
			+ "size|페이지 사이즈|true|int\n")
	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public SingleResult<Page<WishList>> findByPagesWishList(@ApiIgnore @AuthenticationPrincipal final AuthUserDTO authUserDTO,
															final WishListSearchDTO wishListSearchDTO) {
		wishListSearchDTO.setUserSeq(authUserDTO.getUserSeq());
		return responseService.getSingleResult(wishListService.findPagesWishList(wishListSearchDTO));
	}

	/**
	 * Delete wish list common result.
	 *
	 * @param wishListSeq the wish list seq
	 * @return the common result
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 6. 오후 2:22:31
	 * @Description
	 */
	@ApiOperation(value = "위시리스트 삭제", notes = BASIC_CHARACTER)
	@DeleteMapping(value = "/delete/{wishListSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResult deleteWishList(@ApiParam(name = "wishListSeq", value = "위시리스트 시퀀스", defaultValue = "20") @PathVariable final Long wishListSeq) {
		wishListService.delete(wishListSeq);
		return responseService.getSuccessResult();
	}
}

