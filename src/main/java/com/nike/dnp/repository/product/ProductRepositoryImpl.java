package com.nike.dnp.repository.product;


import com.nike.dnp.dto.product.ProductResultDTO;
import com.nike.dnp.dto.product.ProductSearchDTO;
import com.nike.dnp.entity.agency.QAgency;
import com.nike.dnp.entity.product.Product;
import com.nike.dnp.entity.product.QProduct;
import com.nike.dnp.entity.user.QUser;
import com.querydsl.core.types.Projections;
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
	public Page<ProductResultDTO> findPagesProduct(final ProductSearchDTO productSearchDTO,
												   final PageRequest pageRequest) {
		final QProduct qProduct = QProduct.product;
		final QAgency qAgency = QAgency.agency;
		final QUser qUser = QUser.user;
		final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

		final JPAQuery<ProductResultDTO> jpaQuery = queryFactory.select(Projections.bean(
				ProductResultDTO.class,
				qProduct.category2Code,
				qProduct.category3Code,
				qProduct.goodsName,
				qProduct.thumbnailFileName,
				qProduct.thumbnailFilePhysicalName,
				qProduct.thumbnailFileSize,
				qProduct.imageFileName,
				qProduct.imageFilePhysicalName,
				qProduct.imageFileSize,
				qProduct.goodsDescription,
				qProduct.exposureYn,
				qProduct.updateDt,
				qProduct.minimumOrderQuantity,
				qProduct.unitPrice,
				qProduct.goodsSeq,
				qAgency.agencyName,
				qUser.nickname))
				.from(qProduct)
				.innerJoin(qAgency).on(qProduct.agencySeq.eq(qAgency.agencySeq))
				.innerJoin(qUser).on(qProduct.registerSeq.eq(qUser.userSeq))
				.where(
						ProductPredicateHelper.eqCate2gory(productSearchDTO.getCategory2code()),
						ProductPredicateHelper.eqCate3gory(productSearchDTO.getCategory3code()),
						ProductPredicateHelper.likeGoodName(productSearchDTO.getKeyword()),
						ProductPredicateHelper.eqAgentSeq(productSearchDTO.getAgencySeq()),
						ProductPredicateHelper.eqExposureYn(productSearchDTO.getExposureYn()),
						qProduct.useYn.eq("Y"));


		final List<ProductResultDTO> productList = getQuerydsl().applyPagination(
				pageRequest,
				jpaQuery)
				.fetch();
		return new PageImpl<>(
				productList,
				pageRequest,
				jpaQuery.fetchCount());
	}
}
