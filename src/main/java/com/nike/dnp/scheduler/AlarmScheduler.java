package com.nike.dnp.scheduler;

import com.nike.dnp.service.alarm.AlarmService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * The Class Alarm scheduler.
 *
 * @author [이소정]
 * @since 2020. 9. 11. 오후 3:07:11
 */
@Slf4j
@Component
@AllArgsConstructor
public class AlarmScheduler {

	/**
	 * The Alarm service
	 *
	 * @author [이소정]
	 */
	final private AlarmService alarmService;

	/**
	 * Delete alarm scheduler.
	 * [30초], [00분], [00시], [매일], [매월], [매년]
	 *
	 * @author [이소정]
	 * @implNote 알림 삭제
	 * @since 2020. 9. 11. 오후 3:08:20
	 */
//	@Scheduled(cron = "30 0 0 * * *")
//	@Scheduled(cron = "0 47 15 * * *")
	// TODO[lsj] local 테스트 후 올릴 예정
	public void deleteAlarmScheduler() {
		log.info("AlarmScheduler.deleteAlarmScheduler");
		alarmService.deleteAlarmScheduler();

	}

}
