package com.nike.dnp.repository.order;

import com.nike.dnp.dto.order.OrderProductResultDTO;

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




}
