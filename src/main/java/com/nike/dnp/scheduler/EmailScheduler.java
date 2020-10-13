package com.nike.dnp.scheduler;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.service.user.UserMailService;
import com.nike.dnp.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.net.UnknownHostException;


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
	private final static String PROD_SERVER_IP = "10.0.1.93";
	private final static String TEST_SERVER_IP = "10.0.11.39";

	/**
	 * Is ip boolean.
	 *
	 * @return the boolean
	 * @author [오지훈]
	 * @implNote IP 체크
	 * @since 2020. 10. 13. 오전 10:18:25
	 */
	public boolean isIp() {
		log.info("EmailScheduler.isIp");
		try {
			final InetAddress ip = InetAddress.getLocalHost();
			return (PROD_SERVER_IP.equals(ip.getHostAddress()) || TEST_SERVER_IP.equals(ip.getHostAddress()));
		} catch (UnknownHostException exception) {
			log.error("exception", exception);
			return false;
		}
	}

	/**
	 * Send user mail by password change.
	 *
	 * @author [오지훈]
	 * @implNote 90일 비밀번호 변경 안내 메일 전송, 오전 8시 일괄 발송 1일 전
	 * @since 2020. 9. 11. 오후 3:08:20
	 */
	@Scheduled(cron = "0 0 8 * * *")
	public void sendUserMailByPasswordChangeOne() {
		if (isIp()) {
			log.info("EmailScheduler.sendUserMailByPasswordChangeOne");
			for (User user : userService.findByPasswordChange(0)) {
				userMailService.sendMailForChangePassword(user);
			}
		}
	}

	/**
	 * Send user mail by password change.
	 *
	 * @author [오지훈]
	 * @implNote 90일 비밀번호 변경 안내 메일 전송, 오전 8시 5분 일괄 발송 3일 전
	 * @since 2020. 9. 11. 오후 3:08:20
	 */
	@Scheduled(cron = "0 5 8 * * *")
	public void sendUserMailByPasswordChangeThree() {
		if (isIp()) {
			log.info("EmailScheduler.sendUserMailByPasswordChangeThree");
			for (User user : userService.findByPasswordChange(2)) {
				userMailService.sendMailForChangePassword(user);
			}
		}
	}

	/**
	 * Send user mail by password change.
	 *
	 * @author [오지훈]
	 * @implNote 90일 비밀번호 변경 안내 메일 전송, 오전 8시 10분 일괄 발송 7일 전
	 * @since 2020. 9. 11. 오후 3:08:20
	 */
	@Scheduled(cron = "0 10 8 * * *")
	public void sendUserMailByPasswordChangeSeven() {
		if (isIp()) {
			log.info("EmailScheduler.sendUserMailByPasswordChangeSeven");
			for (User user : userService.findByPasswordChange(6)) {
				userMailService.sendMailForChangePassword(user);
			}
		}
	}

	/**
	 * Send user mail by dormant prev one.
	 *
	 * @author [오지훈]
	 * @implNote 휴면 회원 변경 사전 안내 1일전
	 * @since 2020. 10. 13. 오후 1:55:32
	 */
	@Scheduled(cron = "0 5 9 * * *")
	public void sendUserMailByDormantPrevOne() {
		if (isIp()) {
			log.info("EmailScheduler.sendUserMailByDormantPrevOne");
			for (User user : userService.findByDormantConfigure(0)) {
				userMailService.sendMailForChangeDormant(user);
			}
		}
	}

	/**
	 * Send user mail by dormant prev three.
	 *
	 * @author [오지훈]
	 * @implNote 휴면 회원 변경 사전 안내 3일전
	 * @since 2020. 10. 13. 오후 1:55:54
	 */
	@Scheduled(cron = "0 10 9 * * *")
	public void sendUserMailByDormantPrevThree() {
		if (isIp()) {
			log.info("EmailScheduler.sendUserMailByDormantPrevThree");
			for (User user : userService.findByDormantConfigure(2)) {
				userMailService.sendMailForChangeDormant(user);
			}
		}
	}

	/**
	 * Send user mail by dormant prev seven.
	 *
	 * @author [오지훈]
	 * @implNote 휴면 회원 변경 사전 안내 7일전
	 * @since 2020. 10. 13. 오후 1:55:59
	 */
	@Scheduled(cron = "0 15 9 * * *")
	public void sendUserMailByDormantPrevSeven() {
		if (isIp()) {
			log.info("EmailScheduler.sendUserMailByDormantPrevSeven");
			for (User user : userService.findByDormantConfigure(6)) {
				userMailService.sendMailForChangeDormant(user);
			}
		}
	}

	/**
	 * Send user mail by dormant active.
	 *
	 * @author [오지훈]
	 * @implNote 휴면회원으로 전환
	 * @since 2020. 10. 13. 오후 1:38:53
	 */
	@Scheduled(cron = "0 1 9 * * *")
	@Transactional
	public void sendUserMailByDormantActive() {
		if (isIp()) {
			log.info("EmailScheduler.sendUserMailByDormantActive");
			for (User user : userService.findByDormantConfigure(-1)) {
				userMailService.sendMailForProcessingDormant(user);
				user.updateStatus(ServiceCode.UserStatusEnumCode.DORMANT.name());
			}
		}
	}

	/**
	 * Send user mail by dormant delete.
	 *
	 * @author [오지훈]
	 * @implNote 휴면회원 탈퇴 처리
	 * @since 2020. 10. 13. 오후 1:38:36
	 */
	@Scheduled(cron = "0 0 9 * * *")
	@Transactional
	public void sendUserMailByDormantDelete() {
		if (isIp()) {
			log.info("EmailScheduler.sendUserMailByDormantDelete");
			for (User user : userService.findByDormantDelete()) {
				user.delete();
			}
		}
	}

}
