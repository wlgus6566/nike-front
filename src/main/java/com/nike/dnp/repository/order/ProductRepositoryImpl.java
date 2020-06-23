package com.nike.dnp.repository.order;


import com.nike.dnp.dto.order.ProductSearchDTO;
import com.nike.dnp.entity.order.Product;
import com.nike.dnp.entity.order.QProduct;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProductRepositoryImpl
 *
 * @author [윤태호]
 * @CreatedOn 2020. 6. 23. 오후 3:23:31
 * @Description Product(제품) Repository interface 작성
 * @history [윤태호] [2020.06.18] [최초 작성]
 * @since 2020.06.18
 */
@Slf4j
@Repository
public class ProductRepositoryImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {


	/**
	 * 생성자 주입
	 * Instantiates a new Manager repository.
	 *
	 * @author [윤태호]
	 * @CreatedOn 2020. 6. 23. 오후 3:23:31
	 * @Description
	 */
	public ProductRepositoryImpl() {
		super(Product.class);
	}

	/**
	 * Find all pages page.
	 *
	 * @param productSearchDTO the product search dto
	 * @param pageRequest      the page request
	 * @return page
	 * @author [윤태호]
	 * @CreatedOn 2020. 6. 23. 오후 3:23:31
	 * @Description
	 */
	@Override
	public Page<Product> findAllPages(ProductSearchDTO productSearchDTO,
									  PageRequest pageRequest) {
		final QProduct qProduct = QProduct.product;
		final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
		final JPAQuery<Product> query = queryFactory.selectFrom(qProduct)
				.where(ProductPredicateHelper.eqCate1gory(productSearchDTO.getCategory1code()),
						ProductPredicateHelper.eqCate2gory(productSearchDTO.getCategory2code()),
						ProductPredicateHelper.eqCate3gory(productSearchDTO.getCategory3code()),
						ProductPredicateHelper.likeGoodName(productSearchDTO.getKeyword()),
						ProductPredicateHelper.eqAgentSeq(productSearchDTO.getAgencySeq()),
						ProductPredicateHelper.eqExposureYn(productSearchDTO.getExposureYn()));
		List<Product> productList = getQuerydsl().applyPagination(pageRequest,query).fetch();
		return new PageImpl<>(productList,pageRequest,query.fetchCount());
	}

}
