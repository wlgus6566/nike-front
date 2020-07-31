package com.nike.dnp.repository.order;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.order.OrderProductResultDTO;
import com.nike.dnp.dto.order.OrderSearchDTO;
import com.nike.dnp.entity.agency.QAgency;
import com.nike.dnp.entity.order.OrderProductMapping;
import com.nike.dnp.entity.order.QOrder;
import com.nike.dnp.entity.order.QOrderProductMapping;
import com.nike.dnp.entity.product.QProduct;
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
 * The Class Order product mapper repository.
 *
 * @author [윤태호]
 * @since 2020. 7. 1. 오후 4:11:28
 * @implNote
 */
@Slf4j
@Repository
public class OrderProductMapperRepositoryImpl extends QuerydslRepositorySupport implements OrderProductMapperRepositoryCustom {

	/**
	 * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
	 *
	 * @author [윤태호]
	 * @since 2020. 7. 1. 오후 4:17:41
	 * @implNote
	 */
	public OrderProductMapperRepositoryImpl() {
		super(OrderProductMapping.class);
	}

	/**
	 * 이메일 정보 조회
	 *
	 * @param orderSeq the order seq
	 * @return the list
	 * @author [윤태호]
	 * @since 2020. 7. 2. 오전 11:25:16
	 * @implNote
	 */
	@Override
	public List<OrderProductResultDTO> findSearchEmailValue(final Long orderSeq) {
		log.info("OrderProductMapperRepositoryImpl.findSearchEmailValue");
		final QOrderProductMapping orderProductMapping = QOrderProductMapping.orderProductMapping;
		final QProduct product = QProduct.product;
		final QOrder order = QOrder.order;
		final QAgency agency = QAgency.agency;

		final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());


		return queryFactory.select(
				Projections.bean(OrderProductResultDTO.class, orderProductMapping.orderSeq,
																			 orderProductMapping.registrationDt,
																			 orderProductMapping.orderQuantity,
																			 product.goodsName,
																			 product.goodsDescription,
																			 agency.agencyName,
																			 agency.agencySeq,
																			 agency.email,
																			 order.orderDescription))
											.from(orderProductMapping)
											.innerJoin(order).on(orderProductMapping.orderSeq.eq(order.orderSeq))
											.innerJoin(product).on(orderProductMapping.goodsSeq.eq(product.goodsSeq))
											.innerJoin(agency).on(product.agencySeq.eq(agency.agencySeq))
											.where(orderProductMapping.orderSeq.eq(orderSeq))
											.orderBy(orderProductMapping.orderSeq.desc()).fetch();
	}

	/**
	 * 주문 내역 리스트 조회
	 *
	 * @param orderSearchDTO the order search dto
	 * @param pageRequest    the page request
	 * @return the page
	 * @author [윤태호]
	 * @since 2020. 7. 7. 오후 12:14:28
	 * @implNote
	 */
	@Override
	public Page<OrderProductMapping> findPagesOrder(final OrderSearchDTO orderSearchDTO,
													final PageRequest pageRequest) {
		log.info("OrderProductMapperRepositoryImpl.findPagesOrder");
		final QOrderProductMapping qOrderProductMapping = QOrderProductMapping.orderProductMapping;
		final QOrder order = QOrder.order;
		final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

		final JPAQuery<OrderProductMapping> query = queryFactory.selectFrom(qOrderProductMapping)
																.where(
																		OrderProductMappingPredicateHelper.afterStartDt(orderSearchDTO.getBeginDt()),
																		OrderProductMappingPredicateHelper.beforeEndDt(orderSearchDTO.getEndDt()))
																.innerJoin(order).on(qOrderProductMapping.orderSeq.eq(order.orderSeq).and(order.useYn.eq(ServiceCode.YesOrNoEnumCode.Y.name())));

		final List<OrderProductMapping> orderList = getQuerydsl().applyPagination(pageRequest,query).fetch();
		return new PageImpl<>(orderList,pageRequest,query.fetchCount());
	}

	/**
	 * 제품 상세 정보 조회
	 *
	 * @param orderGoodsSeq the order goods seq
	 * @param useYn         the use yn
	 * @return the order product mapping
	 * @author [윤태호]
	 * @since 2020. 7. 31. 오후 12:23:18
	 */
	@Override
	public OrderProductMapping findByIdAndUseYn(Long orderGoodsSeq, String useYn) {

		final QOrderProductMapping qOrderProductMapping = QOrderProductMapping.orderProductMapping;
		final QOrder order = QOrder.order;
		final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

		final OrderProductMapping orderProductMapping = queryFactory.selectFrom(qOrderProductMapping)
																.where(qOrderProductMapping.orderGoodsSeq.eq(orderGoodsSeq))
																.innerJoin(order).on(qOrderProductMapping.orderSeq.eq(order.orderSeq).and(order.useYn.eq(useYn))).fetchOne();

		return orderProductMapping;
	}
}
