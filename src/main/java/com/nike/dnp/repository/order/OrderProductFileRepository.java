package com.nike.dnp.repository.order;

import com.nike.dnp.entity.order.OrderProductFile;
import com.nike.dnp.entity.order.OrderProductMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * The Interface Order product file repository.
 *
 * @author [이소정]
 * @since 2020. 12. 14. 오후 6:52:56
 */
public interface OrderProductFileRepository extends JpaRepository<OrderProductFile,Long> {


    List<OrderProductFile> findByOrderGoodsSeq(Long orderGoodsSeq);
}
