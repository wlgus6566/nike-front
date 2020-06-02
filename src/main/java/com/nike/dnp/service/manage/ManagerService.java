package com.nike.dnp.service.manage;

import com.nike.dnp.dto.manage.manager.ManagerSaveDTO;
import com.nike.dnp.dto.manage.manager.ManagerSearchDTO;
import com.nike.dnp.dto.manage.manager.ManagerUpdateDTO;
import com.nike.dnp.entity.manage.Manager;
import com.nike.dnp.entity.manage.ManagerAuth;
import com.nike.dnp.repository.manage.ManagerAuthRepository;
import com.nike.dnp.repository.manage.ManagerRepository;
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
    public ManagerService(
            ManagerRepository managerRepository
            , ManagerAuthRepository managerAuthRepository
    ) {
        this.managerRepository = managerRepository;
        this.managerAuthRepository = managerAuthRepository;
    }

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
    public Page<Manager> findAllPaging(ManagerSearchDTO managerSearchDTO) {
        PageRequest pageRequest = PageRequest.of(managerSearchDTO.getPage()
                , managerSearchDTO.getSize()
                , Sort.by("registrationDt").descending());

        if (managerSearchDTO.getKeyword().isEmpty()) {
            return managerRepository.findAll(pageRequest);
        }
        return managerRepository.findAllByManagerIdLikeOrManagerNameLike(
                pageRequest, managerSearchDTO.getKeyword(), managerSearchDTO.getKeyword());
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
     * @param managerSaveDTO the manager dto
     * @return the long
     */
    @Transactional
    public Manager save(ManagerSaveDTO managerSaveDTO) {
        Optional<ManagerAuth> managerAuth = managerAuthRepository.findById(managerSaveDTO.getAuthSeq());
        return managerRepository.save(Manager.builder()
                .managerId(managerSaveDTO.getManagerId())
                .managerName(managerSaveDTO.getManagerName())
                .password(managerSaveDTO.getPassword())
                .managerAuth(managerAuth.get())
                .registerSeq(managerSaveDTO.getRegisterSeq())
                .build());
    }

    /**
     * 수정
     *
     * @param managerSeq       the manager seq
     * @param managerUpdateDTO the manager update dto
     */
    @Transactional
    public Manager update(Long managerSeq, ManagerUpdateDTO managerUpdateDTO) {
        Optional<Manager> e = managerRepository.findById(managerSeq);
        if (e.isPresent()) {
            Optional<ManagerAuth> managerAuth = managerAuthRepository.findById(managerUpdateDTO.getAuthSeq());
            e.get().update(
                    managerUpdateDTO.getManagerName()
                    , managerUpdateDTO.getPassword()
                    , managerAuth.get()
                    , managerUpdateDTO.getUpdaterSeq()
            );
        }
        return e.get();
    }

}
