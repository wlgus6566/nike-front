package com.nike.dnp.repository.order;

import java.util.HashMap;
import java.util.List;

public interface OrderProductMapperRepositoryCustom {
	List<HashMap<String, Object>> findSearchEmailValue(Long orderSeq);
}
