package com.nike.dnp.service.goodsbasket;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.goodsbasket.GoodsBasketSaveDTO;
import com.nike.dnp.dto.goodsbasket.GoodsBasketSaveListDTO;
import com.nike.dnp.entity.goodsbasket.GoodsBasket;
import com.nike.dnp.entity.product.Product;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.goodsbasket.GoodsBasketRepository;
import com.nike.dnp.repository.product.ProductRepository;
import com.nike.dnp.util.MessageUtil;
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Class Goods basket service.
 *
 * @author [윤태호]
 * @implNote
 * @since 2020. 7. 2. 오후 4:30:52
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
	 * The Product repository
	 *
	 * @author [윤태호]
	 */
	private final ProductRepository productRepository;

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
		return goodsBasketSave(goodsBasketSaveDTO);
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

			GoodsBasketSaveDTO goodsBasketSaveDTO = new GoodsBasketSaveDTO();
			goodsBasketSaveDTO.setGoodsSeq(goodsBasketSaveListDTO.getGoodsSeqList().get(i));
			goodsBasketSaveDTO.setOrderQuantity(goodsBasketSaveListDTO.getOrderQuantityList().get(i));
			resultList.add(goodsBasketSave(goodsBasketSaveDTO));
		}
		return resultList;
	}

	/**
	 * 장바구니 저장
	 *
	 * @param goodsBasketSaveDTO the goods basket save dto
	 * @return the goods basket
	 * @author [윤태호]
	 * @since 2020. 8. 3. 오전 11:54:43
	 */
	final private GoodsBasket goodsBasketSave(GoodsBasketSaveDTO goodsBasketSaveDTO) {
		log.info("GoodsBasketService.goodsBasketSave");
		final Product product = productRepository.findById(goodsBasketSaveDTO.getGoodsSeq()).orElse(null);
		if(ObjectUtils.isEmpty(product)){
			throw new CodeMessageHandleException(FailCode.ConfigureError.NOT_FOUND_PRODUCT.name(), MessageUtil.getMessage(FailCode.ConfigureError.NOT_FOUND_PRODUCT.name()));
		}else{
			if(product.getMinimumOrderQuantity() > goodsBasketSaveDTO.getOrderQuantity()){
				throw new CodeMessageHandleException(FailCode.ConfigureError.MINIMUM_ORDER_QUANTITY.name(), MessageUtil.getMessage(FailCode.ConfigureError.MINIMUM_ORDER_QUANTITY.name()));
			}else{
				final Optional<GoodsBasket> goodsBasket = goodsBasketRepository.findByGoodsSeqAndUserSeq(goodsBasketSaveDTO.getGoodsSeq(), SecurityUtil.currentUser().getUserSeq());
				final GoodsBasket saveGoodsBasket = goodsBasket.orElse(new GoodsBasket());
				saveGoodsBasket.setGoodsSeq(goodsBasketSaveDTO.getGoodsSeq());
				saveGoodsBasket.setOrderQuantity(goodsBasketSaveDTO.getOrderQuantity());
				return goodsBasketRepository.save(saveGoodsBasket);
			}
		}
	}


}
