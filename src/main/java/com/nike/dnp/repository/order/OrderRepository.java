package com.nike.dnp.repository.order;

import com.nike.dnp.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface Order repository.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 7. 오후 12:14:22
 * @Description
 */
public interface OrderRepository extends JpaRepository<Order,Long> {


}
