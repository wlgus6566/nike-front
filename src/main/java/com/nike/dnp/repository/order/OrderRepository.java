package com.nike.dnp.repository.order;

import com.nike.dnp.entity.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The Interface Order repository.
 *
 * @author [윤태호]
 * @since 2020. 7. 7. 오후 12:14:22
 * @implNote
 */
public interface OrderRepository extends JpaRepository<OrderEntity,Long>,OrderRepositoryCustom {

	List<OrderEntity> findAllByRegistrationDtBeforeAndUseYn(final LocalDateTime localDateTime, final String useYn);
}
