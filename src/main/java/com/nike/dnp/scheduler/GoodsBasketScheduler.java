package com.nike.dnp.scheduler;

import com.nike.dnp.service.goodsbasket.GoodsBasketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * GoodsBasketScheduler
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 6. 오후 4:28:04
 * @Description
 */
@Slf4j
@Component
@AllArgsConstructor
public class GoodsBasketScheduler {

	/**
	 * The Goods basket service
	 *
	 * @author [윤태호]
	 */
	final private GoodsBasketService goodsBasketService;

	/**
	 * 장바구니 초기화 <br />
	 * [00초], [00분], [00시], [매일], [매월], [매년]
	 *
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 6. 오후 4:28:04
	 * @Description
	 */
	//@Scheduled(cron = "0 * * * * *")
	@Scheduled(cron = "0 0 0 * * *")
	public void goodsBasketDeleteScheduler(){
		log.debug("===== 장바구니 초기화 ==== ");
		goodsBasketService.deleteAll();
	}
}
