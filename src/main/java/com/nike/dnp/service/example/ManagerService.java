package com.nike.dnp.service.example;

import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.dto.example.manager.ManagerSearchDTO;
import com.nike.dnp.dto.example.manager.ManagerUpdateDTO;
import com.nike.dnp.entity.example.Manager;
import com.nike.dnp.entity.example.ManagerAuth;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.example.ManagerAuthRepository;
import com.nike.dnp.repository.example.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
public class ManagerService {

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
        /*
        // Predicate 기능 이용
        return managerRepository.findAll(
                ManagerHelper.search(managerSearchDTO),
                PageRequest.of(managerSearchDTO.getPage()
                        , managerSearchDTO.getSize()
                        , Sort.by("managerSeq").descending()));*/
        // QueryDsl 기능 이용
        return managerRepository.findAlls(
                managerSearchDTO,
                PageRequest.of(managerSearchDTO.getPage()
                        , managerSearchDTO.getSize()
                        , Sort.by("managerId").descending()));
    }

    /**
     * 상세조회
     *
     * @param managerSeq the manager seq
     * @return the optional
     */
    public Manager findById(final Long managerSeq) {
        return managerRepository.findById(managerSeq)
                .orElseThrow(() -> new CodeMessageHandleException(ErrorEnumCode.UserError.USER01.toString(), ErrorEnumCode.UserError.USER01.getMessage()));
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
            );
        }
        return manager;
    }

}