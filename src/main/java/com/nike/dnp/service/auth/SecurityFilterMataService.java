package com.nike.dnp.service.auth;

import com.nike.dnp.entity.example.SecurityIpFilterMata;
import com.nike.dnp.entity.example.SecurityUrlFilterMata;
import com.nike.dnp.repository.example.ManagerRepository;
import com.nike.dnp.repository.example.SecurityIpFilterMataRepositiory;
import com.nike.dnp.repository.example.SecurityUrlFilterMataRepositiory;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.log.UserLoginLogService;
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
	private final ManagerRepository managerRepository;

	/**
	 *
	 */
	private final ResponseService responseService;

	/**
	 *
	 */
	private final UserLoginLogService loginLogService;


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
