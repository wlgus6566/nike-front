package com.nike.dnp.repository.order;

import com.nike.dnp.entity.order.OrderProductMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductMapperReopsitory extends JpaRepository<OrderProductMapping,Long> {

}
