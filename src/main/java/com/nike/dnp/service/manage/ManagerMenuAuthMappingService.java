package com.nike.dnp.service.manage;

import com.nike.dnp.dto.manage.menu.ManagerMenuAuthMappingSaveDTO;
import com.nike.dnp.entity.manage.ManagerAuth;
import com.nike.dnp.entity.manage.ManagerMenu;
import com.nike.dnp.entity.manage.ManagerMenuAuthMapping;
import com.nike.dnp.repository.manage.ManagerAuthRepository;
import com.nike.dnp.repository.manage.ManagerMenuAuthMappingRepository;
import com.nike.dnp.repository.manage.ManagerMenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * ManagerMenuService
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description 관리메뉴 서비스 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Service
@Transactional
public class ManagerMenuAuthMappingService {

    private final ManagerMenuAuthMappingRepository managerMenuAuthMappingRepository;
    private final ManagerMenuRepository managerMenuRepository;
    private final ManagerAuthRepository managerAuthRepository;

    /**
     * Instantiates a new Manager menu service.
     *
     * @param managerMenuAuthMappingRepository the manager menu auth mapping repository
     * @param managerMenuRepository            the manager menu repository
     * @param managerAuthRepository            the manager auth repository
     */
    public ManagerMenuAuthMappingService(
            ManagerMenuAuthMappingRepository managerMenuAuthMappingRepository
            , ManagerMenuRepository managerMenuRepository
            , ManagerAuthRepository managerAuthRepository
    ) {
        this.managerMenuAuthMappingRepository = managerMenuAuthMappingRepository;
        this.managerMenuRepository = managerMenuRepository;
        this.managerAuthRepository = managerAuthRepository;
    }

    /**
     * 삭제
     *
     * @param menuSeq the menu seq
     */
    public void delete(Long menuSeq) {
        managerMenuAuthMappingRepository.deleteByManagerMenu(menuSeq);
    }

    /**
     * 등록
     *
     * @param managerMenuAuthMappingSaveDTO the manager menu auth mapping dto
     * @return the long
     */
    @Transactional
    public Long save(ManagerMenuAuthMappingSaveDTO managerMenuAuthMappingSaveDTO) {
        Optional<ManagerMenu> managerMenu = managerMenuRepository.findById(managerMenuAuthMappingSaveDTO.getMenuSeq());
        Optional<ManagerAuth> managerAuth = managerAuthRepository.findById(managerMenuAuthMappingSaveDTO.getAuthSeq());

        return managerMenuAuthMappingRepository.save(ManagerMenuAuthMapping.builder()
                .managerMenu(managerMenu.get())
                .managerAuth(managerAuth.get())
                .useYn(managerMenuAuthMappingSaveDTO.getUseYn())
                .registerSeq(managerMenuAuthMappingSaveDTO.getRegisterSeq())
                .build()).getMappingSeq();
    }

}
