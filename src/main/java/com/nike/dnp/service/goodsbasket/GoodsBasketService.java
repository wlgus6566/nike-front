package com.nike.dnp.service.goodsbasket;

import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.dto.goodsbasket.GoodsBasketSaveDTO;
import com.nike.dnp.dto.goodsbasket.GoodsBasketSaveListDTO;
import com.nike.dnp.entity.goodsbasket.GoodsBasket;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.goodsbasket.GoodsBasketRepository;
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
 * @CreatedOn 2020. 7. 2. 오후 4:30:52
 * @Description
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
	 * Save basket.
	 *
	 * @param goodsBasketSaveDTO the goods basket save dto
	 * @return goods basket
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 2. 오후 4:34:23
	 * @Description
	 */
	@Transactional
	public GoodsBasket saveBasket(final GoodsBasketSaveDTO goodsBasketSaveDTO) {
		final Optional<GoodsBasket> goodsBasket = goodsBasketRepository.findByGoodsSeqAndUserSeq(goodsBasketSaveDTO.getGoodsSeq(), SecurityUtil.currentUser().getUserSeq());
		final GoodsBasket saveGoodsBasket = goodsBasket.orElse(new GoodsBasket());
		saveGoodsBasket.setGoodsSeq(goodsBasketSaveDTO.getGoodsSeq());
		saveGoodsBasket.setOrderQuantity(goodsBasketSaveDTO.getOrderQuantity());
		return goodsBasketRepository.save(saveGoodsBasket);

	}

	/**
	 * Find by all list.
	 *
	 * @return the list
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 3. 오후 12:05:29
	 * @Description
	 */
	public List<GoodsBasket> findByAll() {
		return goodsBasketRepository.findByUserSeqOrderByGoodsBasketSeqDesc(SecurityUtil.currentUser().getUserSeq());
	}

	/**
	 * Delete basket.
	 *
	 * @param goodsBasketSeq the goods basket seq
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 3. 오후 12:05:29
	 * @Description
	 */
	@Transactional
	public void deleteBasket(final Long goodsBasketSeq) {

		final Optional<GoodsBasket> optionalGoodsBasket = goodsBasketRepository.findById(goodsBasketSeq);
		final GoodsBasket goodsBasket = optionalGoodsBasket.orElseThrow(() -> new CodeMessageHandleException(ErrorEnumCode.BasketError.NOT_FOUND_BASKET.name(),ErrorEnumCode.BasketError.NOT_FOUND_BASKET.getMessage()));
		goodsBasketRepository.delete(goodsBasket);
	}

	/**
	 * 장바구니 전체 삭제 - 스케쥴용
	 *
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 3. 오후 12:05:29
	 * @Description
	 */
	@Transactional
	public void deleteAll() {
		goodsBasketRepository.deleteAll();

	}

	/**
	 * 장바구니 다건 저장
	 *
	 * @param goodsBasketSaveListDTO the goods basket save list dto
	 * @return the list
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 6. 오전 11:37:09
	 * @Description
	 */
	@Transactional
	public List<GoodsBasket> saveBasketList(final GoodsBasketSaveListDTO goodsBasketSaveListDTO) {
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