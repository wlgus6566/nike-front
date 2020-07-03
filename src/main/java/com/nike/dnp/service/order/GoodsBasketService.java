package com.nike.dnp.service.order;

import com.nike.dnp.dto.goodsBasket.GoodsBasketSaveDTO;
import com.nike.dnp.entity.goodsBasket.GoodsBasket;
import com.nike.dnp.repository.goodsBasket.GoodsBasketRepository;
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	final GoodsBasketRepository goodsBasketRepository;

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
		Optional<GoodsBasket> goodsBasket = goodsBasketRepository.findByGoodsSeqAndUserSeq(goodsBasketSaveDTO.getGoodsSeq(), SecurityUtil.currentUser().getUserSeq());

		GoodsBasket saveGoodsBasket = goodsBasket.orElse(new GoodsBasket());
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
	public void deleteBasket(Long goodsBasketSeq) {
		GoodsBasket goodsBasket = new GoodsBasket();
		goodsBasket.setGoodsBasketSeq(goodsBasketSeq);
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
}
