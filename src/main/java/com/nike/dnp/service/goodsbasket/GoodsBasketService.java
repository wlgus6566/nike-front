package com.nike.dnp.service.goodsbasket;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.goodsbasket.GoodsBasketSaveDTO;
import com.nike.dnp.dto.goodsbasket.GoodsBasketSaveListDTO;
import com.nike.dnp.entity.goodsbasket.GoodsBasket;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.goodsbasket.GoodsBasketRepository;
import com.nike.dnp.util.MessageUtil;
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Class Goods basket service.
 *
 * @author [윤태호]
 * @since 2020. 7. 2. 오후 4:30:52
 * @implNote
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GoodsBasketService {


	/**
	 * The Goods basket repository
	 *
	 * @author [윤태호]
	 */
	private final GoodsBasketRepository goodsBasketRepository;

	/**
	 * 장바구니 저장
	 *
	 * @param goodsBasketSaveDTO the goods basket save dto
	 * @return goods basket
	 * @author [윤태호]
	 * @since 2020. 7. 2. 오후 4:34:23
	 * @implNote
	 */
	@Transactional
	public GoodsBasket saveBasket(final GoodsBasketSaveDTO goodsBasketSaveDTO) {
		log.info("GoodsBasketService.saveBasket");
		final Optional<GoodsBasket> goodsBasket = goodsBasketRepository.findByGoodsSeqAndUserSeq(goodsBasketSaveDTO.getGoodsSeq(), SecurityUtil.currentUser().getUserSeq());
		final GoodsBasket saveGoodsBasket = goodsBasket.orElse(new GoodsBasket());
		saveGoodsBasket.setGoodsSeq(goodsBasketSaveDTO.getGoodsSeq());
		saveGoodsBasket.setOrderQuantity(goodsBasketSaveDTO.getOrderQuantity());
		return goodsBasketRepository.save(saveGoodsBasket);

	}

	/**
	 * 장바구니 조회
	 *
	 * @return the list
	 * @author [윤태호]
	 * @since 2020. 7. 3. 오후 12:05:29
	 * @implNote
	 */
	public List<GoodsBasket> findByAll() {
		log.info("GoodsBasketService.findByAll");
		return goodsBasketRepository.findByUserSeqOrderByGoodsBasketSeqDesc(SecurityUtil.currentUser().getUserSeq());
	}

	/**
	 * 장바구니 삭제
	 *
	 * @param goodsBasketSeq the goods basket seq
	 * @author [윤태호]
	 * @since 2020. 7. 3. 오후 12:05:29
	 * @implNote
	 */
	@Transactional
	public void deleteBasket(final Long goodsBasketSeq) {
		log.info("GoodsBasketService.deleteBasket");
		final Optional<GoodsBasket> optionalGoodsBasket = goodsBasketRepository.findById(goodsBasketSeq);
		final GoodsBasket goodsBasket = optionalGoodsBasket.orElseThrow(
				() -> new CodeMessageHandleException(
						FailCode.ExceptionError.NOT_FOUND.name()
						, MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name())));
		goodsBasketRepository.delete(goodsBasket);
	}

	/**
	 * 장바구니 전체 삭제 - 스케쥴용
	 *
	 * @author [윤태호]
	 * @since 2020. 7. 3. 오후 12:05:29
	 * @implNote
	 */
	@Transactional
	public void deleteAll() {
		log.info("GoodsBasketService.deleteAll");
		goodsBasketRepository.deleteAll();

	}

	/**
	 * 장바구니 다건 저장
	 *
	 * @param goodsBasketSaveListDTO the goods basket save list dto
	 * @return the list
	 * @author [윤태호]
	 * @since 2020. 7. 6. 오전 11:37:09
	 * @implNote
	 */
	@Transactional
	public List<GoodsBasket> saveBasketList(final GoodsBasketSaveListDTO goodsBasketSaveListDTO) {
		log.info("GoodsBasketService.saveBasketList");
		if(goodsBasketSaveListDTO.getGoodsSeqList().size()!= goodsBasketSaveListDTO.getOrderQuantityList().size()){
			throw new CodeMessageHandleException(
					FailCode.ConfigureError.INVALID_ORDER.name()
					, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_ORDER.name()));
		}

		final List<GoodsBasket> resultList = new ArrayList<>();
		for(int i = 0; i < goodsBasketSaveListDTO.getGoodsSeqList().size(); i++){
			final Optional<GoodsBasket> findGoodsBasket = goodsBasketRepository.findByGoodsSeqAndUserSeq(goodsBasketSaveListDTO.getGoodsSeqList().get(i), SecurityUtil.currentUser().getUserSeq());
			final GoodsBasket goodsBasket = findGoodsBasket.orElse(new GoodsBasket());
			goodsBasket.setGoodsSeq(goodsBasketSaveListDTO.getGoodsSeqList().get(i));
			goodsBasket.setOrderQuantity(goodsBasketSaveListDTO.getOrderQuantityList().get(i));
			resultList.add(goodsBasketRepository.save(goodsBasket));
		}
		return resultList;
	}
}
