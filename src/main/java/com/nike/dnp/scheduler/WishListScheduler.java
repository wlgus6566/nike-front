package com.nike.dnp.scheduler;


import com.nike.dnp.service.wishlist.WishListService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The Class Wish list scheduler.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 6. 오후 4:57:56
 * @Description
 */
@Slf4j
@Component
@AllArgsConstructor
public class WishListScheduler {

	/**
	 * The Wish list service
	 *
	 * @author [윤태호]
	 */
	final private WishListService wishListService;


	/**
	 * 위시리스트 7일 이전 데이터 삭제 <br />
	 * [00초], [00분], [00시], [매일], [매월], [매년]
	 *
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 6. 오후 4:28:04
	 * @Description
	 */
	//@Scheduled(cron = "0 * * * * *")
	@Scheduled(cron = "0 0 0 * * *")
	public void wishListDeleteScheduler() {
		log.debug("===== 위시리스트 등록 7일 이후 데이터 삭제 ==== ");
		wishListService.deleteScheduler();
	}
}
