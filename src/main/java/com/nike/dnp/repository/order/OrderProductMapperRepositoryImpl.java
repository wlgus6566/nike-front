package com.nike.dnp.repository.order;

import com.nike.dnp.entity.agency.QAgency;
import com.nike.dnp.entity.order.OrderProductMapping;
import com.nike.dnp.entity.order.QOrder;
import com.nike.dnp.entity.order.QOrderProductMapping;
import com.nike.dnp.entity.product.QProduct;
import com.nike.dnp.util.TupleUtil;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * The Class Order product mapper repository.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 1. 오후 4:11:28
 * @Description
 */
@Slf4j
@Repository
public class OrderProductMapperRepositoryImpl extends QuerydslRepositorySupport implements OrderProductMapperRepositoryCustom{
	/**
	 * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
	 *
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 1. 오후 4:17:41
	 * @Description
	 */
	public OrderProductMapperRepositoryImpl() {
		super(OrderProductMapping.class);
	}

	/**
	 * Find search email value list.
	 *
	 * @param orderSeq the order seq
	 * @return the list
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 2. 오전 11:25:16
	 * @Description
	 */
	@Override
	public List<HashMap<String, Object>> findSearchEmailValue(Long orderSeq) {
		QOrderProductMapping orderProductMapping = QOrderProductMapping.orderProductMapping;
		QProduct product = QProduct.product;
		QOrder order = QOrder.order;
		QAgency agency = QAgency.agency;

		final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

		List<Tuple> tupleList =
				queryFactory
						.select(orderProductMapping.orderSeq
								, orderProductMapping.registrationDt
								, orderProductMapping.orderQuantity
								, product.goodsName
								, product.goodsDescription
								, agency.agencyName
								, agency.agencySeq
								, agency.email
								, order.orderDescription)
						.from(orderProductMapping)
						.innerJoin(order).on(orderProductMapping.orderSeq.eq(order.orderSeq))
						.innerJoin(product).on(orderProductMapping.goodsSeq.eq(product.goodsSeq))
						.innerJoin(agency).on(product.agencySeq.eq(agency.agencySeq))
						.where(orderProductMapping.orderSeq.eq(orderSeq))
						.orderBy(orderProductMapping.orderSeq.desc()).fetch();

		return TupleUtil.listTupleToListHashMap(tupleList,
				orderProductMapping.orderSeq,
				orderProductMapping.registrationDt,
				orderProductMapping.orderQuantity,
				product.goodsName,
				product.goodsDescription,
				agency.agencyName,
				agency.agencySeq,
				agency.email,
				order.orderDescription) ;
	}
}
