package com.nike.dnp.repository.order;

import com.nike.dnp.entity.order.OrderProductMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface OrderProductMapperRepository extends JpaRepository<OrderProductMapping,Long> , OrderProductMapperRepositoryCustom{


	Page<OrderProductMapping> findAllByRegisterSeqAndRegistrationDtBetween(Pageable pageable, Long userSeq,
																		   LocalDateTime startDt,LocalDateTime endDt);
}
