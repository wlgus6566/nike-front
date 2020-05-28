package com.nike.dnp.service.manage;

import com.nike.dnp.dto.manage.ManagerDTO;
import com.nike.dnp.entity.manage.Manager;
import com.nike.dnp.entity.manage.ManagerAuth;
import com.nike.dnp.repository.manage.ManagerAuthRepository;
import com.nike.dnp.repository.manage.ManagerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

@Service
@Transactional
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final ManagerAuthRepository managerAuthRepository;

    /**
     * Instantiates a new Manager service.
     *
     * @param managerRepository     the manager repository
     * @param managerAuthRepository the manager auth repository
     */
    public ManagerService(ManagerRepository managerRepository
                        , ManagerAuthRepository managerAuthRepository
    ) {
        this.managerRepository = managerRepository;
        this.managerAuthRepository = managerAuthRepository;
    }

    /**
     * 전체조회
     *
     * @return the list
     */
    public List<Manager> findAll() {
        List<Manager> managers = new ArrayList<>();
        //managerRepository.findAll().forEach(e -> managers.add(e));
        managers.addAll(managerRepository.findAll());
        return managers;
    }

    /**
     * 상세조회
     *
     * @param managerSeq the manager seq
     * @return the optional
     */
    public Optional<Manager> findById(Long managerSeq) {
        Optional<Manager> manager = managerRepository.findById(managerSeq);
        return manager;
    }

    /**
     * 삭제
     *
     * @param managerSeq the manager seq
     */
    public void delete(Long managerSeq) {
        managerRepository.deleteById(managerSeq);
    }

    /**
     * 등록
     *
     * @param managerDTO the manager dto
     * @return the long
     */
    @Transactional
    public Long save(ManagerDTO managerDTO) {
        Optional<ManagerAuth> managerAuth = managerAuthRepository.findById(managerDTO.getAuthSeq());
        return managerRepository.save(Manager.builder()
                .managerId(managerDTO.getManagerId())
                .managerName(managerDTO.getManagerName())
                .password(managerDTO.getPassword())
                .managerAuth(managerAuth.get())
                .registerSeq(managerDTO.getRegisterSeq())
                .build()).getManagerSeq();
    }

    /**
     * 수정
     *
     * @param managerSeq the manager seq
     * @param managerDTO the manager dto
     */
    @Transactional
    public void update(Long managerSeq, ManagerDTO managerDTO) {
        Optional<Manager> e = managerRepository.findById(managerSeq);
        if (e.isPresent()) {
            Optional<ManagerAuth> managerAuth = managerAuthRepository.findById(managerDTO.getAuthSeq());
            e.get().update(
                managerDTO.getManagerName()
                , managerDTO.getPassword()
                , managerAuth.get()
                , managerDTO.getUpdaterSeq()
            );
        }
    }

}
