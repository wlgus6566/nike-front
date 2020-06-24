package com.nike.dnp.repository.order;

import com.nike.dnp.entity.order.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository
 *
 * @author [윤태호]
 * @Description Product(제품) Repository Interface 작성
 * @history [윤태호] [2020.06.18] [최초 작성]
 * @since 2020.06.18
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Long>,ProductRepositoryCustom {


	Product findByGoodsSeqAndCategory1Code(Long goodsSeq,String category1Code);
}
