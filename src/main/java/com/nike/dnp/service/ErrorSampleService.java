package com.nike.dnp.service;

import com.nike.dnp.dto.manage.manager.ManagerSaveDTO;
import com.nike.dnp.dto.manage.manager.ManagerSearchDTO;
import com.nike.dnp.dto.manage.manager.ManagerUpdateDTO;
import com.nike.dnp.entity.manage.Manager;
import com.nike.dnp.entity.manage.ManagerAuth;
import com.nike.dnp.exception.*;
import com.nike.dnp.model.response.SingleResult;
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
 * ErrorSample Service
 *
 * @since 2020.06.03
 * @author [이소정]
 * @Description ErrorSampleService 작성
 * @history [이소정] [2020.06.03] [최초 작성]
 *
 */

@Service
@Transactional
public class ErrorSampleService {

    private final ManagerRepository managerRepository;
    private final ManagerAuthRepository managerAuthRepository;

    /**
     * 응답 서비스
     */
    private final ResponseService responseService;


    /**
     * Instantiates a new Manager service.
     *
     * @param managerRepository     the manager repository
     * @param managerAuthRepository the manager auth repository
     */
    public ErrorSampleService(
            ManagerRepository managerRepository
            , ManagerAuthRepository managerAuthRepository
            , ResponseService responseService
    ) {
        this.managerRepository = managerRepository;
        this.managerAuthRepository = managerAuthRepository;
        this.responseService = responseService;
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
    public SingleResult<Manager> findById(Long managerSeq) {
        return responseService.getSingleResult(
                managerRepository.findById(managerSeq)
                        .orElseThrow(() -> new Status200Exception(ErrorEnumCode.manageError.MANE01.toString(), ErrorEnumCode.manageError.MANE01.getMessage())));
//        return responseService.getSingleResult(
//                managerRepository.findById(managerSeq)
//                        .orElseThrow(() -> new ManagerNotFoundException()));
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
    public SingleResult<Manager> save(ManagerSaveDTO managerSaveDTO) {
        Optional<ManagerAuth> managerAuth = managerAuthRepository.findById(managerSaveDTO.getAuthSeq());

        if (null == managerSaveDTO.getManagerId()) {
            new Status200Exception(ErrorEnumCode.loginError.LOGE01.toString(), ErrorEnumCode.loginError.LOGE01.getMessage());
        }

        return responseService.getSingleResult(managerRepository.save(Manager.builder()
                .managerId(managerSaveDTO.getManagerId())
                .managerName(managerSaveDTO.getManagerName())
                .password(managerSaveDTO.getPassword())
                .managerAuth(managerAuth.get())
                .registerSeq(managerSaveDTO.getRegisterSeq())
                .build()));
    }

    /**
     * 수정
     *
     * @param managerSeq       the manager seq
     * @param managerUpdateDTO the manager update dto
     */
    @Transactional
    public SingleResult<Manager> update(Long managerSeq, ManagerUpdateDTO managerUpdateDTO) {
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

        return responseService.getSingleResult(e.get());
    }

}
