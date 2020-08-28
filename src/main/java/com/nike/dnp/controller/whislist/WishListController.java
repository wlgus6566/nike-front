package com.nike.dnp.controller.whislist;

import com.nike.dnp.common.aspect.ValidField;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.wishlist.WishListDeleteDTO;
import com.nike.dnp.dto.wishlist.WishListResultDTO;
import com.nike.dnp.dto.wishlist.WishListSaveDTO;
import com.nike.dnp.dto.wishlist.WishListSearchDTO;
import com.nike.dnp.entity.wishlist.WishList;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.wishlist.WishListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;


/**
 * 위시리스트 Controller
 *
 * @author [윤태호]
 * @since 2020. 7. 3. 오후 3:38:34
 * @apiNote
 */
@Slf4j
@RestController
@Api(description = "위시리스트", tags = "WISHLIST")
@RequestMapping(value = "/api", name = "위시리스트")
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
	 * @param wishListSaveDTO the wish list save dto
	 * @param result          the result
	 * @return the single result
	 * @author [윤태호]
	 * @since 2020. 7. 3. 오후 3:38:34
	 * @apiNote 위시리스트 등록
	 */
	@ApiOperation(value = "위시리스트 등록", notes = BASIC_CHARACTER)
	@PostMapping(value = "/wishlist/save", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ValidField
	public SingleResult<WishListResultDTO> saveWishList(@Valid @ApiParam(name = "wishListSaveDTO", value = "위시리스트 등록 JSON") @RequestBody final WishListSaveDTO wishListSaveDTO,
														@ApiIgnore final BindingResult result) {
		log.info("WishListController.saveWishList");
		return responseService.getSingleResult(WishListResultDTO.ofSave(wishListService.save(wishListSaveDTO.getGoodsSeq())));
	}


	/**
	 * 위시리스트 중복 체크
	 *
	 * @param wishListSaveDTO the wish list save dto
	 * @param result          the result
	 * @return the single result
	 * @author [윤태호]
	 * @apiNote 위시리스트 등록
	 * @since 2020. 7. 3. 오후 3:38:34
	 */
	@ApiOperation(value = "위시리스트 체크", notes = BASIC_CHARACTER)
	@GetMapping(value = "/wishlist/check", produces = MediaType.APPLICATION_JSON_VALUE)
	@ValidField
	public SingleResult<WishListResultDTO> chackWishList(@Valid @ModelAttribute final WishListSaveDTO wishListSaveDTO, @ApiIgnore final BindingResult result) {
		log.info("WishListController.chackWishList");
		return responseService.getSingleResult(WishListResultDTO.ofSave(wishListService.findByGoodSeq(wishListSaveDTO)));
	}

	/**
	 * 위시 리스트 조회
	 *
	 * @param authUserDTO the auth user dto
	 * @return the single result
	 * @author [윤태호]
	 * @since 2020. 7. 3. 오후 3:59:10
	 * @apiNote 위시 리스트 조회
	 */
	@ApiOperation(value = "위시리스트 조회", notes = REQUEST_CHARACTER
			+ "page|페이지 번호|true|int\n"
			+ "size|페이지 사이즈|true|int\n")
	@GetMapping(value = "/mypage/wishlist/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public SingleResult<Page<WishList>> findByPagesWishList(@ApiIgnore @AuthenticationPrincipal final AuthUserDTO authUserDTO,
															final WishListSearchDTO wishListSearchDTO) {
		log.info("WishListController.findByPagesWishList");
		wishListSearchDTO.setUserSeq(authUserDTO.getUserSeq());
		return responseService.getSingleResult(wishListService.findPagesWishList(wishListSearchDTO));
	}

	/**
	 * 위시 리스트 삭제
	 *
	 * @param wishListSeq the wish list seq
	 * @return the common result
	 * @author [윤태호]
	 * @since 2020. 7. 6. 오후 2:22:31
	 * @apiNote 위시 리스트 삭제
	 */
	@ApiOperation(value = "위시리스트 삭제", notes = BASIC_CHARACTER)
	@DeleteMapping(value = "/mypage/wishlist/delete/{wishListSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResult deleteWishList(@ApiParam(name = "wishListSeq", value = "위시리스트 시퀀스", defaultValue = "20") @PathVariable final Long wishListSeq) {
		log.info("WishListController.deleteWishList");
		wishListService.delete(wishListSeq);
		return responseService.getSuccessResult();
	}

	/**
	 * 위시 리스트 다건 삭제
	 *
	 * @param wishListDeleteDTO the wish list delete dto
	 * @return the common result
	 * @author [윤태호]
	 * @since 2020. 7. 6. 오후 3:46:42
	 * @apiNote 위시 리스트 다건 삭제
	 */
	@ApiOperation(value = "위시리스트 다건 삭제", notes = BASIC_CHARACTER)
	@DeleteMapping(value = "/mypage/wishlist/delete", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ValidField
	public CommonResult deleteWishListList(@RequestBody @Valid final WishListDeleteDTO wishListDeleteDTO, @ApiIgnore final BindingResult result) {
		log.info("WishListController.deleteWishListList");
		wishListService.deleteList(wishListDeleteDTO);
		return responseService.getSuccessResult();
	}
}