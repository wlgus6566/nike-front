package com.nike.dnp.repository.order;


import com.nike.dnp.dto.order.ProductSearchDTO;
import com.nike.dnp.entity.order.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * ProductRepositoryImpl
 *
 * @author [윤태호]
 * @Description Product(제품) Repository interface 작성
 * @history [윤태호] [2020.06.18] [최초 작성]
 * @since 2020.06.18
 */
@Repository
public class ProductRepositoryImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {


	/**
	 * Instantiates a new Product repository.
	 */
	public ProductRepositoryImpl() {
		super(Product.class);
	}

	/**
	 *
	 * @param productSearchDTO the product search dto
	 * @param pageRequest      the page request
	 * @return
	 */
	@Override
	public Page<Product> findAlls(final ProductSearchDTO productSearchDTO,
								  final PageRequest pageRequest) {

		return null;
	}
}
