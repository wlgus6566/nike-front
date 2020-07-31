package com.nike.dnp.repository.order;

import com.nike.dnp.entity.order.OrderProductMapping;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface Order product mapper repository.
 *
 * @author [윤태호]
 * @since 2020. 7. 14. 오후 3:37:40
 * @implNote
 */
public interface OrderProductMapperRepository extends JpaRepository<OrderProductMapping,Long> , OrderProductMapperRepositoryCustom{


}
