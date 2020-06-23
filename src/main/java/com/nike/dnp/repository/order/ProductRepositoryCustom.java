package com.nike.dnp.repository.order;

import com.nike.dnp.dto.order.ProductSearchDTO;
import com.nike.dnp.entity.order.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * ProductRepositoryCustom
 *
 * @author [윤태호]
 * @Description Product(제품) Repository Custom Interface 작성
 * @history [윤태호] [2020.06.18] [최초 작성]
 * @since 2020.06.18
 */
@Repository
public interface ProductRepositoryCustom {

	/**
	 * Find alls page.
	 *
	 * @param productSearchDTO the product search dto
	 * @param pageRequest      the page request
	 * @return the page
	 */
	Page<Product> findAllPages(ProductSearchDTO productSearchDTO,
						PageRequest pageRequest);
}
