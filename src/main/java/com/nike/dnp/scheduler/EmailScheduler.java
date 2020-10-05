package com.nike.dnp.scheduler;

import com.nike.dnp.entity.user.User;
import com.nike.dnp.service.user.UserMailService;
import com.nike.dnp.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * The type Email scheduler.
 *
 * @author [오지훈]
 * @implNote 이메일 전송 관련 배치 스케쥴러
 * @since 2020. 9. 11. 오후 3:07:11
 */
@Slf4j
@Component
@AllArgsConstructor
public class EmailScheduler {

	private final UserMailService userMailService;
	private final UserService userService;

	/**
	 * Send user mail by password change.
	 *
	 * @author [오지훈]
	 * @implNote 90일 비밀번호 변경 안내 메일 전송, 오전 8시 일괄 발송
	 * @since 2020. 9. 11. 오후 3:08:20
	 */
	//@Scheduled(cron = "0 0 8 * * *")
	//@Scheduled(cron = "0 40 16 * * *")
	public void sendUserMailByPasswordChangeOne() {
		log.info("EmailScheduler.sendUserMailByPasswordChangeOne");

		for (User user : userService.findByPasswordChange(1)) {
			userMailService.sendMailForChangePassword(user);
		}
	}

	//@Scheduled(cron = "0 5 8 * * *")
	//@Scheduled(cron = "0 41 16 * * *")
	public void sendUserMailByPasswordChangeThree() {
		log.info("EmailScheduler.sendUserMailByPasswordChangeThree");

		for (User user : userService.findByPasswordChange(3)) {
			userMailService.sendMailForChangePassword(user);
		}
	}

	//@Scheduled(cron = "0 10 8 * * *")
	//@Scheduled(cron = "0 42 16 * * *")
	public void sendUserMailByPasswordChangeSeven() {
		log.info("EmailScheduler.sendUserMailByPasswordChangeSeven");

		for (User user : userService.findByPasswordChange(7)) {
			userMailService.sendMailForChangePassword(user);
		}
	}

}
