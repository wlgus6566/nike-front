package com.nike.dnp.service.example;

import com.nike.dnp.entity.example.SecurityFilterMata;
import com.nike.dnp.repository.example.ManagerRepository;
import com.nike.dnp.repository.example.SecurityFilterMataRepositiory;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.log.UserLoginLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SecurityFillterMataService {

	private final ManagerRepository managerRepository;

	/**
	 *
	 */
	private final ResponseService responseService;

	/**
	 *
	 */
	private final UserLoginLogService loginLogService;

	private final SecurityFilterMataRepositiory securityFilterMataRepositiory;


	public List<SecurityFilterMata> findAll(){
		return securityFilterMataRepositiory.findAll();
	}

}
