package com.nike.dnp.service.wishlist;

import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.dto.wishlist.WishListDeleteDTO;
import com.nike.dnp.dto.wishlist.WishListSearchDTO;
import com.nike.dnp.entity.wishlist.WishList;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.wishlist.WishListRepository;
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
	private final WishListRepository wishListRepository;


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
		final WishList wishList = new WishList();
		wishList.setGoodsSeq(goodsSeq);
		try{
			return wishListRepository.save(wishList);
		} catch (DataIntegrityViolationException e){
			throw (CodeMessageHandleException)new CodeMessageHandleException(ErrorEnumCode.WishListError.DUPLICATE_GOODS.name(), ErrorEnumCode.WishListError.DUPLICATE_GOODS.getMessage());
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
		final Optional<WishList> userWishList = wishListRepository.findByWishListSeqAndUserSeq(wishListSeq, SecurityUtil.currentUser().getUserSeq());
		final WishList wishList = userWishList.orElseThrow(() -> new CodeMessageHandleException(ErrorEnumCode.WishListError.NOT_FOUND_WISHLIST.name(), ErrorEnumCode.WishListError.NOT_FOUND_WISHLIST.getMessage()));
		wishListRepository.delete(wishList);
	}

	/**
	 * Delete list.
	 *
	 * @param wishListDeleteDTO the wish list delete dto
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 6. 오후 3:46:26
	 * @Description
	 */
	@Transactional
	public void deleteList(final WishListDeleteDTO wishListDeleteDTO) {
		final List<WishList> wishListList = wishListRepository.findAllById(wishListDeleteDTO.getWishListSeqList());
		wishListList.forEach(wishList -> {
			if(wishList.getUserSeq().equals(SecurityUtil.currentUser().getUserSeq())){
				wishListRepository.delete(wishList);
			}
		});
	}

	/**
	 * Delete scheduler.
	 *
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 14. 오후 3:22:10
	 * @Description
	 */
	@Transactional
	public void deleteScheduler() {
		// 7일 이후 데이터 검색
		final List<WishList> byRegistrationDtAfter =
				wishListRepository.findByRegistrationDtBefore(LocalDateTime.of(LocalDate.now().plusDays(-7), LocalTime.of(0,0,0)));

		wishListRepository.deleteAll(byRegistrationDtAfter);

	}
}
