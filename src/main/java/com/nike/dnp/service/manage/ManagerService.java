package com.nike.dnp.service.manage;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.nike.dnp.common.viriable.ErrorEnumCode;
import com.nike.dnp.common.viriable.ErrorEnumCode.LoginError;
import com.nike.dnp.dto.manage.auth.AuthUserDTO;
import com.nike.dnp.dto.manage.manager.ManagerPredicate;
import com.nike.dnp.dto.manage.manager.ManagerSearchDTO;
import com.nike.dnp.dto.manage.manager.ManagerUpdateDTO;
import com.nike.dnp.entity.manage.Manager;
import com.nike.dnp.entity.manage.ManagerAuth;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.manage.ManagerAuthRepository;
import com.nike.dnp.repository.manage.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
public class ManagerService implements UserDetailsService {

    /**
     * @author [오지훈]
     * ManagerRepository
     */
    private final ManagerRepository managerRepository;

    /**
     * @author [오지훈]
     * ManagerAuthRepository
     */
    private final ManagerAuthRepository authRepository;

    /**
     * 전체조회(entire)
     *
     * @return the list
     */
    public List<Manager> findAll() {
        return managerRepository.findAll();
    }

    /**
     * 전체조회(paging)
     *
     * @param managerSearchDTO the manager search dto
     * @return the list
     */
    public Page<Manager> findAllPaging(final ManagerSearchDTO managerSearchDTO) {
        return managerRepository.findAll(
                ManagerPredicate.search(managerSearchDTO),
                PageRequest.of(managerSearchDTO.getPage()
                        , managerSearchDTO.getSize()
                        , Sort.by("managerSeq").descending()));
    }

    /**
     * 상세조회
     *
     * @param managerSeq the manager seq
     * @return the optional
     */
    public Manager findById(final Long managerSeq) {
        return managerRepository.findById(managerSeq)
                .orElseThrow(() -> new CodeMessageHandleException(ErrorEnumCode.ManageError.MANE01.toString(), ErrorEnumCode.ManageError.MANE01.getMessage()));
    }

    /**
     * 삭제
     *
     * @param managerSeq the manager seq
     */
    public void delete(final Long managerSeq) {
        managerRepository.deleteById(managerSeq);
    }

    /**
     * 등록
     *
     * @param managerId   the manager id
     * @param managerName the manager name
     * @param password    the password
     * @param authSeq     the auth seq
     * @param registerSeq the register seq
     * @return the manager
     */
    @Transactional
    public Manager save(
            final String managerId
            , final String managerName
            , final String password
            , final long authSeq
            , final long registerSeq
    ) {
        final ManagerAuth managerAuth = authRepository.findByAuthSeq(authSeq);
        final Manager manager = new Manager();
        manager.setManagerId(managerId);
        manager.setManagerName(managerName);
        manager.setPassword(password);
        manager.setRegisterSeq(registerSeq);
        manager.setManagerAuth(managerAuth);
        return managerRepository.save(manager);
    }

    /**
     * 수정
     *
     * @param managerSeq       the manager seq
     * @param managerUpdateDTO the manager update dto
     */
    @Transactional
    public Optional<Manager> update(final Long managerSeq, final ManagerUpdateDTO managerUpdateDTO) {
        final Optional<Manager> manager = managerRepository.findById(managerSeq);
        if (manager.isPresent()) {
            final Optional<ManagerAuth> managerAuth = authRepository.findById(managerUpdateDTO.getAuthSeq());
            manager.get().update(
                    managerUpdateDTO.getManagerName()
                    , managerUpdateDTO.getPassword()
                    , managerAuth.get()
                    , managerUpdateDTO.getUpdaterSeq()
            );
        }
        return manager;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final Manager manager = managerRepository.findByManagerId(username);
        if(manager == null){
            throw new UserNotFoundException(LoginError.LOGE01.toString());
        }
        return new AuthUserDTO(manager);
    }
}
