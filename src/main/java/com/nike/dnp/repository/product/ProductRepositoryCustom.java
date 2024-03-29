package com.nike.dnp.repository.product;

import com.nike.dnp.dto.product.ProductResultDTO;
import com.nike.dnp.dto.product.ProductSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * ProductRepositoryCustom
 *
 * @author [윤태호]
 * @implNote Product(제품) Repository Custom Interface 작성
 * @history [윤태호] [2020.06.18] [최초 작성]
 * @since 2020.06.18 8. 11. 오후 5:55:36
 */
@Repository
public interface ProductRepositoryCustom {

	/**
	 * 제품 관리 리스트 페이지
	 *
	 * @param productSearchDTO the product search dto
	 * @param pageRequest      the page request
	 * @return the page
	 * @author [윤태호]
	 * @implNote 제품 관리 리스트 페이지
	 * @since 2020. 8. 11. 오후 5:55:37
	 */
	Page<ProductResultDTO> findPagesProduct(ProductSearchDTO productSearchDTO,
											PageRequest pageRequest);
}