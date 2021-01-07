package com.nike.dnp.repository.order;

import com.nike.dnp.entity.order.OrderMail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMailRepository extends JpaRepository<OrderMail,Long> {

}