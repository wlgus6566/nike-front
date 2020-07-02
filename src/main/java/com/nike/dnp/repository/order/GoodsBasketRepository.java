package com.nike.dnp.repository.order;


import com.nike.dnp.entity.order.GoodsBasket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodsBasketRepository extends JpaRepository<GoodsBasket,Long> {

	Optional<GoodsBasket> findByGoodsSeqAndUserSeq(Long goodsSeq,Long userSeq);

}
