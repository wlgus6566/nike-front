package com.nike.dnp.repository.order;

import com.nike.dnp.entity.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The Interface Order repository.
 *
 * @author [윤태호]
 * @implNote
 * @since 2020. 7. 7. 오후 12:14:22
 */
public interface OrderRepository extends JpaRepository<OrderEntity,Long>,OrderRepositoryCustom {

	/**
	 * 1년 지난 주문서 조회
	 *
	 * @param localDateTime the local date time
	 * @param useYn         the use yn
	 * @return the list
	 * @author [윤태호]
	 * @implNote 1년 지난 주문서 조회
	 * @since 2020. 8. 11. 오후 5:52:43
	 */
	List<OrderEntity> findAllByRegistrationDtBeforeAndUseYn(final LocalDateTime localDateTime, final String useYn);
}