package com.nike.dnp.repository.order;

import com.nike.dnp.dto.order.OrderSearchDTO;
import com.nike.dnp.entity.order.OrderProductMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.List;

/**
 * The Interface Order product mapper repository custom.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 7. 오후 12:07:04
 * @Description
 */
public interface OrderProductMapperRepositoryCustom {
	/**
	 * Find search email value list.
	 *
	 * @param orderSeq the order seq
	 * @return the list
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 7. 오후 12:07:04
	 * @Description
	 */
	List<HashMap<String, Object>> findSearchEmailValue(final Long orderSeq);

	/**
	 * Find pages order page.
	 *
	 * @param orderSearchDTO the order search dto
	 * @param pageRequest    the page request
	 * @return the page
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 7. 오후 12:07:04
	 * @Description
	 */
	Page<OrderProductMapping> findPagesOrder(final OrderSearchDTO orderSearchDTO,
											 final PageRequest pageRequest);
}
