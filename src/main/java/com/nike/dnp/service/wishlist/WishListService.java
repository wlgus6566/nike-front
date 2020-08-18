package com.nike.dnp.service.wishlist;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.wishlist.WishListDeleteDTO;
import com.nike.dnp.dto.wishlist.WishListSearchDTO;
import com.nike.dnp.entity.wishlist.WishList;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.exception.NotFoundHandleException;
import com.nike.dnp.repository.wishlist.WishListRepository;
import com.nike.dnp.util.MessageUtil;
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
 * @since 2020. 7. 6. 오후 2:29:57
 * @implNote
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
	 * 위시 리스트 저장
	 *
	 * @param goodsSeq the goods seq
	 * @return the wish list
	 * @author [윤태호]
	 * @since 2020. 7. 6. 오후 2:29:57
	 * @implNote 위시 리스트 저장
	 */
	@Transactional
	public WishList save(final Long goodsSeq) {
		log.info("WishListService.save");
		final WishList wishList = new WishList();
		wishList.setGoodsSeq(goodsSeq);
		try{
			return wishListRepository.save(wishList);
		} catch (DataIntegrityViolationException e){
			throw (CodeMessageHandleException)new CodeMessageHandleException(
					FailCode.ConfigureError.DUPLICATE_GOODS.name()
					, MessageUtil.getMessage(FailCode.ConfigureError.DUPLICATE_GOODS.name()));
		}
	}

	/**
	 * 위시 리스트 페이징
	 *
	 * @param wishListSearchDTO the wish list search dto
	 * @return the page
	 * @author [윤태호]
	 * @since 2020. 7. 6. 오후 2:29:57
	 * @implNote 위시 리스트 페이징
	 */
	public Page<WishList> findPagesWishList(final WishListSearchDTO wishListSearchDTO) {
		log.info("WishListService.findPagesWishList");
		return wishListRepository.findPagesWishList(wishListSearchDTO, PageRequest.of(wishListSearchDTO.getPage(), wishListSearchDTO.getSize(), Sort.by("wishListSeq").descending()));
	}

	/**
	 * 위시 리스트 삭제
	 *
	 * @param wishListSeq the wish list seq
	 * @author [윤태호]
	 * @since 2020. 7. 6. 오후 2:29:57
	 * @implNote 위시 리스트 삭제
	 */
	@Transactional
	public void delete(final Long wishListSeq) {
		log.info("WishListService.delete");
		final Optional<WishList> userWishList = wishListRepository.findByWishListSeqAndUserSeq(wishListSeq, SecurityUtil.currentUser().getUserSeq());
		final WishList wishList = userWishList.orElseThrow(
				() -> new NotFoundHandleException());
		wishListRepository.delete(wishList);
	}

	/**
	 * 위시리스트 리스트 삭제
	 *
	 * @param wishListDeleteDTO the wish list delete dto
	 * @author [윤태호]
	 * @since 2020. 7. 6. 오후 3:46:26
	 * @implNote 위시리스트 리스트 삭제
	 */
	@Transactional
	public void deleteList(final WishListDeleteDTO wishListDeleteDTO) {
		log.info("WishListService.deleteList");
		final List<WishList> wishListList = wishListRepository.findAllById(wishListDeleteDTO.getWishListSeqList());
		wishListList.forEach(wishList -> {
			if(wishList.getUserSeq().equals(SecurityUtil.currentUser().getUserSeq())){
				wishListRepository.delete(wishList);
			}
		});
	}

	/**
	 * 위시리스트 삭제 [스케쥴용]
	 *
	 * @author [윤태호]
	 * @since 2020. 7. 14. 오후 3:22:10
	 * @implNote 위시리스트 삭제 [스케쥴용]
	 */
	@Transactional
	public void deleteScheduler() {
		log.info("WishListService.deleteScheduler");
		// 7일 이후 데이터 검색
		final List<WishList> byRegistrationDtAfter =
				wishListRepository.findByRegistrationDtBefore(LocalDateTime.of(LocalDate.now().plusDays(-7), LocalTime.of(0,0,0)));
		wishListRepository.deleteAll(byRegistrationDtAfter);
	}
}
