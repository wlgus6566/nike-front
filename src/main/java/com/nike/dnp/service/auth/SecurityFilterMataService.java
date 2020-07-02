package com.nike.dnp.service.auth;

import com.nike.dnp.entity.auth.SecurityIpFilterMata;
import com.nike.dnp.entity.auth.SecurityUrlFilterMata;
import com.nike.dnp.repository.auth.SecurityIpFilterMataRepositiory;
import com.nike.dnp.repository.auth.SecurityUrlFilterMataRepositiory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Security filter mata service.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:12:05
 * @Description
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SecurityFilterMataService {

	/**
	 * The Url filter repo
	 *
	 * @author [오지훈]
	 */
	private final SecurityUrlFilterMataRepositiory urlFilterRepo;

	/**
	 * The Ip filter repo
	 *
	 * @author [오지훈]
	 */
	private final SecurityIpFilterMataRepositiory ipFilterRepo;


	/**
	 * Url find all list.
	 *
	 * @return the list
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:12:05
	 * @Description
	 */
	public List<SecurityUrlFilterMata> urlFindAll(){
		return urlFilterRepo.findAll();
	}


	/**
	 * Ip find all list.
	 *
	 * @return the list
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:12:05
	 * @Description
	 */
	public List<SecurityIpFilterMata> ipFindAll() {
		return ipFilterRepo.findAll();
	}


}
