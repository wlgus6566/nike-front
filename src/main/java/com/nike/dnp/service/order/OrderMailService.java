package com.nike.dnp.service.order;

import com.nike.dnp.dto.user.UserDTO;
import com.nike.dnp.entity.order.OrderMail;
import com.nike.dnp.repository.order.OrderMailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Order mail service.
 *
 * @author [이소정]
 * @since 2021. 1. 6. 오후 8:33:12
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderMailService {

	/**
	 * The Order mail repository
	 */
	private final OrderMailRepository orderMailRepository;

	/**
	 * Save order mail list.
	 *
	 * @param recipientList the recipient list
	 * @return the list
	 * @author [이소정]
	 * @implNote 주문 수신자 목록 저장
	 * @since 2021. 1. 6. 오후 8:33:12
	 */
	@Transactional
	public List<OrderMail> saveOrderMail(final Long orderSeq, final List<UserDTO> recipientList) {
		log.info("OrderMailService.saveOrderMail");
		List<OrderMail> orderMailList = new ArrayList<>();
		// 수신자 목록 저장
		for (UserDTO userDTO : recipientList) {
			OrderMail orderMail = new OrderMail();
			orderMail.setOrderSeq(orderSeq);
			orderMail.setUserId(userDTO.getUserId());
			orderMail.setUserSeq(userDTO.getUserSeq());
			orderMail.setNickname(userDTO.getNickname());
			orderMailList.add(orderMailRepository.save(orderMail));
		}

		return orderMailList;
	}
}
