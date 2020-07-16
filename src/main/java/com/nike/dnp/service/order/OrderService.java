package com.nike.dnp.service.order;

import com.nike.dnp.dto.order.OrderProductSaveDTO;
import com.nike.dnp.entity.order.Order;
import com.nike.dnp.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class Order service.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 6. 30. 오후 5:37:34
 * @Description
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
	 * Save order order.
	 *
	 * @param orderProductSaveDTO the order product save dto
	 * @return the order
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 1. 오후 2:47:41
	 * @Description
	 */
	@Transactional
	public Order saveOrder(final OrderProductSaveDTO orderProductSaveDTO) {
		final Order order = new Order();
		order.setOrderDescription(orderProductSaveDTO.getOrderDescription());
		order.setTotalAmount(orderProductSaveDTO.getTotalAmount());
		return orderRepository.save(order);
	}
}
