package com.nike.dnp.repository.product;

import com.nike.dnp.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
	 * @implNote 제품 조회
	 */
	Product findByGoodsSeq(Long goodsSeq);

	/**
	 * Find all by agency seq and use yn list.
	 *
	 * @param agencySeq the agency seq
	 * @param useYn     the use yn
	 * @return the list
	 * @author [이소정]
	 * @implNote 에이전시 상품 목록 조회
	 * @since 2020. 8. 28. 오후 2:46:39
	 */
	List<Product> findAllByAgencySeqAndUseYn(Long agencySeq, String useYn);
}
