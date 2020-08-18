package com.nike.dnp.scheduler;

import com.nike.dnp.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The Class File scheduler.
 *
 * @author [윤태호]
 * @since 2020. 7. 14. 오전 11:51:33
 * @implNote
 */
@Slf4j
@Component
@AllArgsConstructor
public class FileScheduler {
	/**
	 * 파일 삭제
	 * [00초], [00분], [00시], [매일], [매월], [매년]
	 *
	 * @author [윤태호]
	 * @since 2020. 7. 10. 오후 5:55:23
	 * @implNote 파일 삭제
	 */
	@Scheduled(cron = "0 0 0 * * *")
//	@Scheduled(cron = "*/10 * * * * *")
	public void fileDeleteScheduler() {
		log.debug("===== temp 파일 삭제 ==== ");
		FileUtil.deleteTemp();

	}

}
