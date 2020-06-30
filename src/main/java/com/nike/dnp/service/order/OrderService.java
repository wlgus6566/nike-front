package com.nike.dnp.service.order;

import com.nike.dnp.dto.order.OrderProductSaveDTO;
import com.nike.dnp.entity.order.Order;
import com.nike.dnp.entity.order.OrderProductMapping;
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

	final OrderRepository orderRepository;

	final OrderProductMapping orderProductMapping;


	public OrderProductMapping saveOrder(final OrderProductSaveDTO orderProductSaveDTO) {
		Order order = new Order();
		order.setOrderDescription(orderProductSaveDTO.getOrderDescription());
		order.setTotalAmount(orderProductSaveDTO.getTotalAmount());

		OrderProductMapping orderProductMapping = new OrderProductMapping();




		return new OrderProductMapping();


	}
}
