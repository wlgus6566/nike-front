package com.nike.dnp.repository.order;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.order.OrderSearchDTO;
import com.nike.dnp.entity.order.OrderEntity;
import com.nike.dnp.entity.order.QOrderEntity;
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
public class OrderRepositoryImpl extends QuerydslRepositorySupport implements OrderRepositoryCustom {

	/**
	 * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
	 *
	 * @author [윤태호]
	 * @since 2020. 7. 1. 오후 4:17:41
	 * @implNote
	 */
	public OrderRepositoryImpl() {
		super(OrderEntity.class);
	}

	/**
	 * 제품 상세 정보 조회
	 *
	 * @param orderSeq the order seq
	 * @param useYn    the use yn
	 * @return the order product mapping
	 * @author [윤태호]
	 * @since 2020. 7. 31. 오후 12:23:18
	 */
	@Override
	public OrderEntity findByOrderSeqAndUseYn(Long orderSeq, String useYn) {
		log.info("OrderRepositoryImpl.findByOrderSeqAndUseYn");
		final QOrderEntity order = QOrderEntity.orderEntity;
		final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

		final OrderEntity result = queryFactory.selectFrom(order)
											   .where(order.orderSeq.eq(orderSeq).and(order.useYn.eq(useYn))).fetchOne();

		return result;
	}

	/**
	 * 주문 내역 리스트 조회
	 *
	 * @param orderSearchDTO the order search dto
	 * @param pageRequest    the page request
	 * @return the page
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 7. 오후 12:14:28
	 */
	@Override
	public Page<OrderEntity> findPagesOrder(final OrderSearchDTO orderSearchDTO, final PageRequest pageRequest) {
		log.info("OrderRepositoryImpl.findPagesOrder");
		final QOrderEntity order = QOrderEntity.orderEntity;
		final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
		final JPAQuery<OrderEntity> query = queryFactory.selectFrom(order)
														.where(
														  OrderPredicateHelper.afterStartDt(orderSearchDTO.getBeginDt()),
														  OrderPredicateHelper.beforeEndDt(orderSearchDTO.getEndDt()),
														  OrderPredicateHelper.eqRegisterSeq(orderSearchDTO.getUserSeq()),
														  OrderPredicateHelper.eqUseYn(ServiceCode.YesOrNoEnumCode.Y.name()));

		final List<OrderEntity> orderEntityList = getQuerydsl().applyPagination(pageRequest, query).fetch();
		return new PageImpl<>(orderEntityList, pageRequest, query.fetchCount());
	}
}
