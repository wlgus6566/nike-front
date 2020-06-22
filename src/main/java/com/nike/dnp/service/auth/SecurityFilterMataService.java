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
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SecurityFilterMataService {

	/**
	 *
	 */
	private final SecurityUrlFilterMataRepositiory urlFilterRepo;

	/**
	 *
	 */
	private final SecurityIpFilterMataRepositiory ipFilterRepo;


	/**
	 * Url find all list.
	 *
	 * @return the list
	 */
	public List<SecurityUrlFilterMata> urlFindAll(){
		return urlFilterRepo.findAll();
	}


	/**
	 * Ip find all list.
	 *
	 * @return the list
	 */
	public List<SecurityIpFilterMata> ipFindAll() {
		return ipFilterRepo.findAll();
	}


}
