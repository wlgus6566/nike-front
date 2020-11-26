package com.nike.dnp.service.order;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.order.OrderProductSaveDTO;
import com.nike.dnp.dto.order.OrderSearchDTO;
import com.nike.dnp.entity.order.OrderEntity;
import com.nike.dnp.repository.order.OrderRepository;
import com.nike.dnp.service.DeviceService;
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The Class Order service.
 *
 * @author [윤태호]
 * @since 2020. 6. 30. 오후 5:37:34
 * @implNote
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
	 * 주문 저장
	 *
	 * @param orderProductSaveDTO the order product save dto
	 * @return the order
	 * @author [윤태호]
	 * @since 2020. 7. 1. 오후 2:47:41
	 * @implNote 주문 저장
	 */
	@Transactional
	public OrderEntity saveOrder(final OrderProductSaveDTO orderProductSaveDTO) {
		log.info("OrderService.saveOrder");
		final OrderEntity orderEntity = new OrderEntity();
		orderEntity.setOrderDescription(orderProductSaveDTO.getOrderDescription());
		orderEntity.setTotalAmount(orderProductSaveDTO.getTotalAmount());
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
}
