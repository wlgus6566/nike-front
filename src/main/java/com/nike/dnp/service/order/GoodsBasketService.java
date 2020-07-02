package com.nike.dnp.service.order;

import com.nike.dnp.dto.order.GoodsBasketSaveDTO;
import com.nike.dnp.entity.order.GoodsBasket;
import com.nike.dnp.repository.order.GoodsBasketRepository;
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 2. 오후 4:34:23
	 * @Description
	 * @return
	 */
	public GoodsBasket saveBasket(final GoodsBasketSaveDTO goodsBasketSaveDTO) {
		Optional<GoodsBasket> goodsBasket = goodsBasketRepository.findByGoodsSeqAndUserSeq(goodsBasketSaveDTO.getGoodsSeq(), SecurityUtil.currentUser().getUserSeq());

		GoodsBasket saveGoodsBasket = new GoodsBasket();
		goodsBasket.ifPresent(goodsBasket1 -> saveGoodsBasket.setGoodsBasketSeq(goodsBasket1.getGoodsBasketSeq()));

		saveGoodsBasket.setGoodsSeq(goodsBasketSaveDTO.getGoodsSeq());
		saveGoodsBasket.setOrderQuantity(goodsBasketSaveDTO.getOrderQuantity());

		return goodsBasketRepository.save(saveGoodsBasket);

	}
}
