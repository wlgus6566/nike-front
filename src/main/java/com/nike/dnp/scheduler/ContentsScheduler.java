package com.nike.dnp.scheduler;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.service.contents.ContentsBasketService;
import com.nike.dnp.service.contents.ContentsService;
import com.nike.dnp.service.goodsbasket.GoodsBasketService;
import com.nike.dnp.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * The Class Contents scheduler.
 *
 * @author [이소정]
 * @implNote 콘텐츠 관련 배치
 * @since 2020. 7. 30. 오후 5:42:03
 */
@Slf4j
@Component
@AllArgsConstructor
public class ContentsScheduler {

	/**
	 * The Contents basket service
	 *
	 * @author [이소정]
	 */
	final private ContentsBasketService contentsBasketService;

	/**
	 * The Contents service
	 *
	 * @author [이소정]
	 */
	final private ContentsService contentsService;

	/**
	 * Basket delete scheduler.
	 * 장바구니 초기화 <br/>
	 * [00초], [00분], [00시], [매일], [매월], [매년]
	 *
	 * @author [이소정]
	 * @implNote 콘텐츠 장바구니 삭제 배치
	 * @since 2020. 7. 30. 오후 5:41:52
	 */
	@Scheduled(cron = "0 0 0 * * *")
	public void basketDeleteScheduler() {
		log.info("ContentsScheduler.basketDeleteScheduler");
		contentsBasketService.deleteAll();

	}

	/**
	 * Asset delete scheduler.
	 * Asset 콘텐츠 삭제 <br/>
  	 * [00초], [01분], [00시], [매일], [매월], [매년]
	 *
	 * @author [이소정]
	 * @implNote 6개월 이상된 Asset 삭제
	 * @since 2020. 7. 30. 오후 6:24:59
	 */
	@Scheduled(cron = "0 1 0 * * *")
	public void assetDeleteScheduler() {
		log.info("ContentsScheduler.assetDeleteScheduler");
		contentsService.deleteContents(
				LocalDateTime.of(LocalDate.now().plusMonths(-6), LocalTime.of(0,0,0)), ServiceCode.ContentsTopMenuCode.ASSET.toString()
		);
	}

	/**
	 * Toolkit delete scheduler.
	 * Toolkit 콘텐츠 삭제 <br/>
	 * [00초], [01분], [00시], [매일], [매월], [매년]
	 *
	 * @author [이소정]
	 * @implNote 1년 이상된 Toolkit 삭제
	 * @since 2020. 7. 30. 오후 6:24:59
	 */
	@Scheduled(cron = "0 1 0 * * *")
	public void toolkitDeleteScheduler() {
		log.info("ContentsScheduler.toolkitDeleteScheduler");
		contentsService.deleteContents(
				LocalDateTime.of(LocalDate.now().plusYears(-1), LocalTime.of(0,0,0)), ServiceCode.ContentsTopMenuCode.TOOLKIT.toString()
		);
	}

	/**
	 * Foundation delete scheduler.
	 * Foundation 콘텐츠 삭제 <br/>
	 * [00초], [01분], [00시], [매일], [매월], [매년]
	 *
	 * @author [이소정]
	 * @implNote 2년 이상된 Foundation 삭제
	 * @since 2020. 7. 30. 오후 6:24:59
	 */
	@Scheduled(cron = "0 1 0 * * *")
	public void foundationDeleteScheduler() {
		log.info("ContentsScheduler.foundationDeleteScheduler");
		contentsService.deleteContents(
				LocalDateTime.of(LocalDate.now().plusYears(-2), LocalTime.of(0,0,0)), ServiceCode.ContentsTopMenuCode.FOUNDATION.toString()
		);
	}



}
