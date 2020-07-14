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
	 * Find by goods seq and user seq optional.
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
	 * Find by user seq order by goods basket seq desc list.
	 *
	 * @param userSeq the user seq
	 * @return the list
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 14. 오후 3:37:34
	 * @Description
	 */
	List<GoodsBasket> findByUserSeqOrderByGoodsBasketSeqDesc(Long userSeq);



}
