package com.nike.dnp.scheduler;

import com.nike.dnp.service.order.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The Class Order scheduler.
 *
 * @author [윤태호]
 * @since 2020. 8. 3. 오후 2:57:53
 */
@Slf4j
@Component
@AllArgsConstructor
public class OrderScheduler {

	/**
	 * The Order service
	 *
	 * @author [윤태호]
	 */
	final private OrderService orderService;


	/**
	 * 장바구니 초기화 <br />
	 * [00초], [00분], [00시], [매일], [매월], [매년]
	 *
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 6. 오후 4:28:04
	 */
	//@Scheduled(cron = "*/10 * * * * *")
	@Scheduled(cron = "0 0 0 * * *")
	public void after1yearDeleteScheduler() {
		log.info("OrderScheduler.after1yearDeleteScheduler");
		log.debug("==== 1년 지난 주문 삭제 처리 ====");
		orderService.after1yearDelete();
	}
}