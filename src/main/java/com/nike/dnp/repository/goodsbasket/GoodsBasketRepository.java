package com.nike.dnp.repository.goodsbasket;


import com.nike.dnp.entity.goodsbasket.GoodsBasket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * The Interface Goods basket repository.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 14. 오후 3:37:34
 * @Description
 */
public interface GoodsBasketRepository extends JpaRepository<GoodsBasket,Long> {

	/**
	 * 제품 시퀀스와 유저 시퀀스 장바구니 조회
	 *
	 * @param goodsSeq the goods seq
	 * @param userSeq  the user seq
	 * @return the optional
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 14. 오후 3:37:34
	 * @Description
	 */
	Optional<GoodsBasket> findByGoodsSeqAndUserSeq(Long goodsSeq,Long userSeq);

	/**
	 * 유저 시퀀스로 장바구니 조회
	 *
	 * @param userSeq the user seq
	 * @return the list
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 14. 오후 3:37:34
	 * @Description
	 */
	List<GoodsBasket> findByUserSeqOrderByGoodsBasketSeqDesc(Long userSeq);



}
