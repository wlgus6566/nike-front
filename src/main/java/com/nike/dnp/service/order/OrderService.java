package com.nike.dnp.service.order;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.order.OrderProductSaveDTO;
import com.nike.dnp.entity.order.Order;
import com.nike.dnp.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	 * 주문 저장
	 *
	 * @param orderProductSaveDTO the order product save dto
	 * @return the order
	 * @author [윤태호]
	 * @since 2020. 7. 1. 오후 2:47:41
	 * @implNote
	 */
	@Transactional
	public Order saveOrder(final OrderProductSaveDTO orderProductSaveDTO) {
		log.info("OrderService.saveOrder");
		final Order order = new Order();
		order.setOrderDescription(orderProductSaveDTO.getOrderDescription());
		order.setTotalAmount(orderProductSaveDTO.getTotalAmount());
		order.setUseYn(ServiceCode.YesOrNoEnumCode.Y.name());
		return orderRepository.save(order);
	}

	@Transactional
	public void after1yearDelete() {
		LocalDateTime localDateTime = LocalDateTime.now();
		List<Order> orderList = orderRepository.findAllByRegistrationDtBeforeAndUseYn(localDateTime.plusYears(-1),ServiceCode.YesOrNoEnumCode.Y.name());
		orderList.forEach(order -> {
			order.setUseYn(ServiceCode.YesOrNoEnumCode.N.name());
		});
	}
}
