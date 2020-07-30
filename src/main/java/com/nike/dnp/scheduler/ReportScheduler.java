package com.nike.dnp.scheduler;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.service.contents.ContentsBasketService;
import com.nike.dnp.service.report.ReportBasketService;
import com.nike.dnp.service.report.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * The Class Report scheduler.
 *
 * @author [이소정]
 * @implNote 보고서 관련 배치
 * @since 2020. 7. 30. 오후 5:42:03
 */
@Slf4j
@Component
@AllArgsConstructor
public class ReportScheduler {

	/**
	 * The Report basket service
	 *
	 * @author [이소정]
	 */
	final private ReportBasketService reportBasketService;

	/**
	 * The Report service
	 *
	 * @author [이소정]
	 */
	final private ReportService reportService;

	/**
	 * Basket delete scheduler.
	 * 장바구니 초기화 <br/>
	 * [00초], [00분], [00시], [매일], [매월], [매년]
	 *
	 * @author [이소정]
	 * @implNote 보고서 장바구니 삭제 배치
	 * @since 2020. 7. 30. 오후 5:41:52
	 */
	@Scheduled(cron = "0 0 0 * * *")
	public void basketDeleteScheduler() {
		log.info("ReportScheduler.basketDeleteScheduler");
		reportBasketService.deleteAll();

	}

	/**
	 * report delete scheduler.
	 * 보고서 삭제 <br/>
	 * [00초], [01분], [00시], [매일], [매월], [매년]
	 *
	 * @author [이소정]
	 * @implNote 1년 이상된 보고서 삭제
	 * @since 2020. 7. 30. 오후 6:24:59
	 */
	@Scheduled(cron = "0 1 0 * * *")
	public void reportDeleteScheduler() {
		log.info("ReportScheduler.reportDeleteScheduler");
		reportService.deleteReport(LocalDateTime.of(LocalDate.now().plusYears(-1), LocalTime.of(0,0,0)));
	}

}
