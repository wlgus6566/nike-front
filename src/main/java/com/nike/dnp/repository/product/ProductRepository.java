package com.nike.dnp.repository.product;

import com.nike.dnp.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository
 *
 * @author [윤태호]
 * @implNote Product(제품) Repository Interface 작성
 * @history [윤태호] [2020.06.18] [최초 작성]
 * @since 2020.06.18
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Long>,ProductRepositoryCustom {


	/**
	 * 제품 조회
	 *
	 * @param goodsSeq      goodsSeq
	 * @return the product
	 * @author [윤태호]
	 * @since 2020. 6. 24. 오후 4:41:35
	 * @implNote
	 */
	Product findByGoodsSeq(Long goodsSeq);
}
