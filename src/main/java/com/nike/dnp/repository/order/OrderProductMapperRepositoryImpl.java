package com.nike.dnp.repository.order;

import com.nike.dnp.dto.order.OrderProductResultDTO;
import com.nike.dnp.entity.agency.QAgency;
import com.nike.dnp.entity.order.OrderProductMapping;
import com.nike.dnp.entity.order.QOrderEntity;
import com.nike.dnp.entity.order.QOrderProductFile;
import com.nike.dnp.entity.order.QOrderProductMapping;
import com.nike.dnp.entity.product.QProduct;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Class Order product mapper repository.
 *
 * @author [윤태호]
 * @implNote
 * @since 2020. 7. 1. 오후 4:11:28
 */
@Slf4j
@Repository
public class OrderProductMapperRepositoryImpl extends QuerydslRepositorySupport implements OrderProductMapperRepositoryCustom {

	/**
	 * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
	 *
	 * @author [윤태호]
	 * @implNote 주입
	 * @since 2020. 7. 1. 오후 4:17:41
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
	 * @implNote 이메일 정보 조회
	 * @since 2020. 7. 2. 오전 11:25:16
	 */
	@Override
	public List<OrderProductResultDTO> findSearchEmailValue(final Long orderSeq) {
		log.info("OrderProductMapperRepositoryImpl.findSearchEmailValue");
		final QOrderProductMapping orderProductMapping = QOrderProductMapping.orderProductMapping;
		final QProduct product = QProduct.product;
		final QOrderEntity order = QOrderEntity.orderEntity;
		final QAgency agency = QAgency.agency;
		final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
		return queryFactory.select(
				Projections.constructor(OrderProductResultDTO.class,
						orderProductMapping,
						product,
						agency,
						order
						))
				.from(orderProductMapping)
				.innerJoin(order).on(orderProductMapping.orderSeq.eq(order.orderSeq))
				.innerJoin(product).on(orderProductMapping.goodsSeq.eq(product.goodsSeq))
				.innerJoin(agency).on(product.agencySeq.eq(agency.agencySeq))
				.where(orderProductMapping.orderSeq.eq(orderSeq))
				.orderBy(orderProductMapping.orderSeq.desc()).fetch();
	}
}