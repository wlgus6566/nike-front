package com.nike.dnp.service.auth;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.nike.dnp.common.variable.ErrorEnumCode.LoginError;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.entity.example.Manager;
import com.nike.dnp.repository.example.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ManagerService
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description 관리자(유저) 서비스 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    /**
     * TODO[ojh] 모델링 후 변경 예정
     * @author [오지훈]
     * ManagerRepository
     */
    private final ManagerRepository managerRepository;

    /**
     * 시큐리티 Db 유저 조회
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(final String username) {
        final Manager manager = managerRepository.findByManagerId(username);
        if(manager == null){
            throw new UserNotFoundException(LoginError.LOGE01.toString());
        }
        return new AuthUserDTO(manager);
    }
}
