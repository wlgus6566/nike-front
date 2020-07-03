package com.nike.dnp.repository.goodsBasket;


import com.nike.dnp.entity.goodsBasket.GoodsBasket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoodsBasketRepository extends JpaRepository<GoodsBasket,Long> {

	Optional<GoodsBasket> findByGoodsSeqAndUserSeq(Long goodsSeq,Long userSeq);

	List<GoodsBasket> findByUserSeqOrderByGoodsBasketSeqDesc(Long userSeq);



}
