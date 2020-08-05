package com.nike.dnp.repository.order;

import com.nike.dnp.dto.order.OrderSearchDTO;
import com.nike.dnp.entity.order.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * The Interface Order product mapper repository custom.
 *
 * @author [윤태호]
 * @implNote
 * @since 2020. 7. 7. 오후 12:07:04
 */
public interface OrderRepositoryCustom {
	/**
	 * 주문내역 상세
	 *
	 * @param orderGoodsSeq the order goods seq
	 * @param useYn         the use yn
	 * @return the optional
	 * @author [윤태호]
	 * @since 2020. 7. 31. 오후 12:14:37
	 */
	OrderEntity findByOrderSeqAndUseYn(Long orderGoodsSeq, String useYn);

	/**
	 * 주문 페이지 리스트 조회
	 *
	 * @param orderSearchDTO the order search dto
	 * @param pageRequest    the page request
	 * @return the page
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 7. 오후 12:07:04
	 */
	Page<OrderEntity> findPagesOrder(final OrderSearchDTO orderSearchDTO, final PageRequest pageRequest);

}
