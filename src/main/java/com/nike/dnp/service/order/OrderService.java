package com.nike.dnp.service.order;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.order.*;
import com.nike.dnp.dto.user.UserDTO;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.entity.order.OrderEntity;
import com.nike.dnp.repository.order.OrderRepository;
import com.nike.dnp.service.DeviceService;
import com.nike.dnp.service.auth.AuthService;
import com.nike.dnp.service.user.UserService;
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The Class Order service.
 *
 * @author [윤태호]
 * @implNote
 * @since 2020. 6. 30. 오후 5:37:34
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {


	/**
	 * The Order repository
	 *
	 * @author [윤태호]
	 */
	private final OrderRepository orderRepository;

	/**
	 * The Device service
	 *
	 * @author [이소정]
	 */
	private final DeviceService deviceService;

	/**
	 * The User service
	 *
	 * @author [이소정]
	 */
	private final UserService userService;

	/**
	 * The Auth service
	 *
	 *  @author [이소정]
	 */
	private final AuthService authService;

	/**
	 * The Editor url
	 *
	 * @author [이소정]
	 */
	@Value("${nike.url.pc.domain:}")
	private String editorUrl;

	/**
	 * 주문 저장
	 *
	 * @param orderSaveDTO the order save dto
	 * @return the order
	 * @author [윤태호]
	 * @implNote 주문 저장
	 * @since 2020. 7. 1. 오후 2:47:41
	 */
	@Transactional
	public OrderEntity saveOrder(final OrderSaveDTO orderSaveDTO) {
		log.info("OrderService.saveOrder");
		final OrderEntity orderEntity = new OrderEntity();
		orderEntity.setOrderDescription(orderSaveDTO.getOrderDescription());
		orderEntity.setTotalAmount(orderSaveDTO.getTotalAmount());
		orderEntity.setUseYn(ServiceCode.YesOrNoEnumCode.Y.name());
		return orderRepository.save(orderEntity);
	}

	/**
	 * Save order del order entity.
	 *
	 * @param orderSaveDTO the order save dto
	 * @return the order entity
	 * @author [이소정]
	 * @implNote [method 설명]
	 * @since 2021. 1. 5. 오후 3:26:13
	 */
	@Transactional
	public OrderEntity saveOrder_del(final OrderSaveDTO_del orderSaveDTO) {
		log.info("OrderService.saveOrder");
		final OrderEntity orderEntity = new OrderEntity();
		orderEntity.setOrderDescription(orderSaveDTO.getOrderDescription());
		orderEntity.setTotalAmount(orderSaveDTO.getTotalAmount());
		orderEntity.setUseYn(ServiceCode.YesOrNoEnumCode.Y.name());
		return orderRepository.save(orderEntity);
	}

	/**
	 * 1년 지난 주문서 삭제
	 *
	 * @author [윤태호]
	 * @implNote 1년 지난 주문서 삭제
	 * @since 2020. 8. 5. 오후 3:38:13
	 */
	@Transactional
	public void after1yearDelete() {
		LocalDateTime localDateTime = LocalDateTime.now();
		List<OrderEntity> orderEntityList = orderRepository.findAllByRegistrationDtBeforeAndUseYn(localDateTime.plusYears(-1), ServiceCode.YesOrNoEnumCode.Y.name());
		orderEntityList.forEach(order -> {
			order.setUseYn(ServiceCode.YesOrNoEnumCode.N.name());
		});
	}

	/**
	 * 주문 제품 맴핑 조회
	 *
	 * @param orderSeq the order seq
	 * @param useYn    the use yn
	 * @return the order product mapping
	 * @author [윤태호]
	 * @implNote 주문 제품 맴핑 조회
	 * @since 2020. 7. 7. 오후 2:44:07
	 */
	public OrderEntity findByOrderSeqAndUseYn(final Long orderSeq, String useYn) {
		log.info("OrderService.findByOrderSeqAndUseYn");

		OrderEntity orderEntity = orderRepository.findByOrderSeqAndUseYn(orderSeq, useYn);

		// 주문자 이메일 추가
		orderEntity.setUserEmail(userService.findById(orderEntity.getRegisterSeq()).get().getUserId());
		// 파일 cdnUrl 추가
		orderEntity.setCdnUrl(editorUrl);

		return orderRepository.findByOrderSeqAndUseYn(orderSeq, useYn);
	}

	/**
	 * 주문 페이징
	 *
	 * @param orderSearchDTO the order search dto
	 * @return the page
	 * @author [윤태호]
	 * @implNote 주문 페이징
	 * @since 2020. 7. 7. 오후 2:44:07
	 */
	public Page<OrderEntity> findPageOrder(final OrderSearchDTO orderSearchDTO) {
		log.info("OrderService.findPageOrder");
		orderSearchDTO.setUserSeq(SecurityUtil.currentUser().getUserSeq());
		return orderRepository.findPagesOrder(
				orderSearchDTO,
				PageRequest.of(orderSearchDTO.getPage(), orderSearchDTO.getSize(), Sort.by("orderSeq").descending()));

	}

	/**
	 * Find recipient list list.
	 *
	 * @return the list
	 * @author [이소정]
	 * @implNote 3depth일 경우 상위 계정 목록 조회
	 * @since 2021. 1. 5. 오후 3:26:13
	 */
	public OrderRecipientResultDTO findRecipientList() {
		log.info("OrderService.findRecipientList");
		OrderRecipientResultDTO resultDTO = new OrderRecipientResultDTO();

		Optional<Auth> authOptional = authService.findById(SecurityUtil.currentUser().getAuthSeq());
		Auth auth = authOptional.get();
		if (auth.getAuthDepth() == 3) {
			// 3depth 일 경우 상위 2depth 회원 목록 조회
			resultDTO.setUserList(userService.findAllByAuthSeq(auth.getUpperAuthSeq()));
			resultDTO.setDepthCheckYn("Y");
		} else {
			resultDTO.setDepthCheckYn("N");
		}
		
		return resultDTO;
	}
}
