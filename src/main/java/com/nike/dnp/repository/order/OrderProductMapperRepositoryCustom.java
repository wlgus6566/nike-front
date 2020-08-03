package com.nike.dnp.repository.order;

import com.nike.dnp.dto.order.OrderProductResultDTO;
import com.nike.dnp.dto.order.OrderSearchDTO;
import com.nike.dnp.entity.order.OrderProductMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * The Interface Order product mapper repository custom.
 *
 * @author [윤태호]
 * @implNote
 * @since 2020. 7. 7. 오후 12:07:04
 */
public interface OrderProductMapperRepositoryCustom {

	/**
	 *
	 * 이메일 정보 조회
	 * @param orderSeq the order seq
	 * @return the list
	 * @author [윤태호]
	 * @since 2020. 7. 7. 오후 12:07:04
	 * @implNote
	 */
	List<OrderProductResultDTO> findSearchEmailValue(final Long orderSeq);

	/**
	 * 주문 페이지 리스트 조회
	 *
	 * @param orderSearchDTO the order search dto
	 * @param pageRequest    the page request
	 * @return the page
	 * @author [윤태호]
	 * @since 2020. 7. 7. 오후 12:07:04
	 * @implNote
	 */
	Page<OrderProductMapping> findPagesOrder(final OrderSearchDTO orderSearchDTO,
											 final PageRequest pageRequest);


	/**
	 * 주문내역 상세
	 *
	 * @param orderGoodsSeq the order goods seq
	 * @param useYn         the use yn
	 * @return the optional
	 * @author [윤태호]
	 * @since 2020. 7. 31. 오후 12:14:37
	 */
	OrderProductMapping findByIdAndUseYn(Long orderGoodsSeq, String useYn);
}
