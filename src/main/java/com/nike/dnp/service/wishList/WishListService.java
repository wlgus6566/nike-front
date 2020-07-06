package com.nike.dnp.service.wishList;

import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.dto.wishList.WishListSearchDTO;
import com.nike.dnp.entity.wishList.WishList;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.wishList.WishListRepository;
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The Class Wish list service.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 6. 오후 2:29:57
 * @Description
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WishListService {


	/**
	 * The Wish list repository
	 *
	 * @author [윤태호]
	 */
	final WishListRepository wishListRepository;


	/**
	 * Save wish list.
	 *
	 * @param goodsSeq the goods seq
	 * @return the wish list
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 6. 오후 2:29:57
	 * @Description
	 */
	@Transactional
	public WishList save(final Long goodsSeq) {
		WishList wishList = new WishList();
		wishList.setGoodsSeq(goodsSeq);
		try{
			WishList saveWishList = wishListRepository.save(wishList);
			return saveWishList;
		} catch (DataIntegrityViolationException e){
			throw new CodeMessageHandleException(ErrorEnumCode.WishListError.DUPLICATE_GOODS.name(), ErrorEnumCode.WishListError.DUPLICATE_GOODS.getMessage());
		}
	}

	/**
	 * Find pages wish list page.
	 *
	 * @param wishListSearchDTO the wish list search dto
	 * @return the page
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 6. 오후 2:29:57
	 * @Description
	 */
	public Page<WishList> findPagesWishList(final WishListSearchDTO wishListSearchDTO) {
		return wishListRepository.findPagesWishList(wishListSearchDTO, PageRequest.of(wishListSearchDTO.getPage(), wishListSearchDTO.getSize(), Sort.by("wishListSeq").descending()));
	}

	/**
	 * Delete.
	 *
	 * @param wishListSeq the wish list seq
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 6. 오후 2:29:57
	 * @Description
	 */
	@Transactional
	public void delete(final Long wishListSeq) {
		Optional<WishList> userWishList = wishListRepository.findByWishListSeqAndUserSeq(wishListSeq, SecurityUtil.currentUser().getUserSeq());
		WishList wishList = userWishList.orElseThrow(() -> new CodeMessageHandleException(ErrorEnumCode.WishListError.NOT_FOUND_WISHLIST.name(), ErrorEnumCode.WishListError.NOT_FOUND_WISHLIST.getMessage()));
		wishListRepository.delete(wishList);
	}
}
