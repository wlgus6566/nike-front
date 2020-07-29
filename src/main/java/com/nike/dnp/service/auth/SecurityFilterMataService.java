package com.nike.dnp.service.auth;

import com.nike.dnp.entity.auth.SecurityIpFilterMata;
import com.nike.dnp.repository.auth.SecurityIpFilterMataRepositiory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Security filter mata service.
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 6:12:05
 * @implNote
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SecurityFilterMataService {


	/**
	 * The Ip filter repo
	 *
	 * @author [오지훈]
	 */
	private final SecurityIpFilterMataRepositiory ipFilterRepo;


	/**
	 * Ip find all list.
	 *
	 * @return the list
	 * @author [오지훈]
	 * @since 2020. 6. 24. 오후 6:12:05
	 * @implNote
	 */
	public List<SecurityIpFilterMata> ipFindAll() {
		return ipFilterRepo.findAll();
	}


}
