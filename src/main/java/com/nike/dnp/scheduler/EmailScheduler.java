package com.nike.dnp.scheduler;

import com.nike.dnp.common.mail.MailService;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.email.SendDTO;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * The type Email scheduler.
 *
 * @author [오지훈]
 * @implNote [Description 작성]
 * @since 2020. 9. 11. 오후 3:07:11
 */
@Slf4j
@Component
@AllArgsConstructor
public class EmailScheduler {

	private final MailService mailService;
	private final UserService userService;

	/**
	 * Delete alarm scheduler.
	 * [00초], [30분], [17시], [23일], [9월], [요일], [2020년]
	 *
	 * @author [오지훈]
	 * @implNote 회원가입 이메일 전송
	 * @since 2020. 9. 11. 오후 3:08:20
	 */
	@Scheduled(cron = "0 30 12 24 9 ?")
	public void sendCronEmail() {
		log.info("EmailScheduler.sendCronEmail");
		if (LocalDateTime.now().getYear() == 2020) {
			for (User user : userService.findByNormal()) {
				final SendDTO sendDTO = new SendDTO();
				sendDTO.setNickname(user.getNickname());
				sendDTO.setEmail(user.getUserId());
				sendDTO.setLoginUrl("https://www.nikespace.co.kr/login");
				mailService.sendMail(
						ServiceCode.EmailTypeEnumCode.USER_CREATE.toString()
						, ServiceCode.EmailTypeEnumCode.USER_CREATE.getMessage()
						, sendDTO
				);
			}
		}
	}

}
