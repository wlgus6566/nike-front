package com.nike.dnp.service.manage;

import com.nike.dnp.dto.manage.auth.ManagerAuthDTO;
import com.nike.dnp.entity.manage.ManagerAuth;
import com.nike.dnp.repository.manage.ManagerAuthRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ManagerAuthService
 *
 * @since 2020.05.28
 * @author [오지훈]
 * @Description 관리권한 서비스 작성
 * @history [오지훈] [2020.05.28] [최초 작성]
 *
 */

@Service
@Transactional
public class ManagerAuthService {

    private final ManagerAuthRepository managerAuthRepository;

    /**
     * Instantiates a new Manager menu service.
     *
     * @param managerAuthRepository the manager menu repository
     */
    public ManagerAuthService(
            ManagerAuthRepository managerAuthRepository
    ) {
        this.managerAuthRepository = managerAuthRepository;
    }

    /**
     * 전체조회
     *
     * @return the list
     */
    public List<ManagerAuth> findAll() {
        List<ManagerAuth> managerAuths = new ArrayList<>();
        managerAuths.addAll(managerAuthRepository.findAll());
        return managerAuths;
    }

    /**
     * 상세조회
     *
     * @param authSeq the auth seq
     * @return the optional
     */
    public Optional<ManagerAuth> findById(Long authSeq) {
        return managerAuthRepository.findById(authSeq);
    }

    /**
     * 삭제
     *
     * @param authSeq the auth seq
     */
    public void delete(Long authSeq) {
        managerAuthRepository.deleteById(authSeq);
    }

    /**
     * 등록
     *
     * @param managerAuthDTO the manager menu dto
     * @return the long
     */
    @Transactional
    public Long save(ManagerAuthDTO managerAuthDTO) {
        return managerAuthRepository.save(ManagerAuth.builder()
                .authName(managerAuthDTO.getAuthName())
                .roleType(managerAuthDTO.getRoleType())
                .useYn(managerAuthDTO.getUseYn())
                .registerSeq(managerAuthDTO.getRegisterSeq())
                .build()).getAuthSeq();
    }

    /**
     * 수정
     *
     * @param authSeq        the auth seq
     * @param managerAuthDTO the manager menu dto
     */
    @Transactional
    public void update(Long authSeq, ManagerAuthDTO managerAuthDTO) {
        Optional<ManagerAuth> managerAuth = managerAuthRepository.findById(authSeq);
        if (managerAuth.isPresent()) {
            managerAuth.get().update(
                managerAuthDTO.getAuthName()
                , managerAuthDTO.getRoleType()
                , managerAuthDTO.getUseYn()
                , managerAuthDTO.getUpdaterSeq()
            );
        }
    }

}
