package com.nike.dnp.service.manager;

import com.nike.dnp.dto.manager.ManagerMenuDTO;
import com.nike.dnp.entity.manager.ManagerMenu;
import com.nike.dnp.repository.manager.ManagerMenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
public class ManagerMenuService {

    private final ManagerMenuRepository managerMenuRepository;

    /**
     * Instantiates a new Manager menu service.
     *
     * @param managerMenuRepository the manager menu repository
     */
    public ManagerMenuService(
            ManagerMenuRepository managerMenuRepository
    ) {
        this.managerMenuRepository = managerMenuRepository;
    }

    /**
     * 전체목록
     *
     * @return the list
     */
    public List<ManagerMenu> findAll() {
        List<ManagerMenu> managerMenus = new ArrayList<>();
        managerMenuRepository.findAll().forEach(e -> managerMenus.add(e));
        return managerMenus;
    }

    /**
     * 상세
     *
     * @param menuSeq the menu seq
     * @return the optional
     */
    public Optional<ManagerMenu> findById(Long menuSeq) {
        Optional<ManagerMenu> managerMenu = managerMenuRepository.findById(menuSeq);
        return managerMenu;
    }

    /**
     * 삭제
     *
     * @param menuSeq the menu seq
     */
    public void deleteById(Long menuSeq) {
        managerMenuRepository.deleteById(menuSeq);
    }

    /**
     * 등록
     *
     * @param managerMenuDTO the manager menu dto
     * @return the long
     */
    @Transactional
    public Long save(ManagerMenuDTO managerMenuDTO) {
        return managerMenuRepository.save(ManagerMenu.builder()
                .menuName(managerMenuDTO.getMenuName())
                .menuPath(managerMenuDTO.getMenuPath())
                .upperMenuCode(managerMenuDTO.getUpperMenuCode())
                .menuCode(managerMenuDTO.getMenuCode())
                .creationAuthYn(managerMenuDTO.getCreationAuthYn())
                .updateAuthYn(managerMenuDTO.getUpdateAuthYn())
                .deleteAuthYn(managerMenuDTO.getDeleteAuthYn())
                .readingAuthYn(managerMenuDTO.getReadingAuthYn())
                .downloadAuthYn(managerMenuDTO.getDownloadAuthYn())
                .uploadAuthYn(managerMenuDTO.getUploadAuthYn())
                .printingAuthYn(managerMenuDTO.getPrintingAuthYn())
                .excelAuthYn(managerMenuDTO.getExcelAuthYn())
                .menuOrder(managerMenuDTO.getMenuOrder())
                .useYn(managerMenuDTO.getUseYn())
                .registerSeq(managerMenuDTO.getRegisterSeq())
                .build()).getMenuSeq();
    }

    /**
     * 수정
     *
     * @param menuSeq        the menu seq
     * @param managerMenuDTO the manager menu dto
     */
    @Transactional
    public void update(Long menuSeq, ManagerMenuDTO managerMenuDTO) {
        Optional<ManagerMenu> e = managerMenuRepository.findById(menuSeq);
        if (e.isPresent()) {
            e.get().update(
                managerMenuDTO.getMenuName()
                , managerMenuDTO.getMenuPath()
                , managerMenuDTO.getCreationAuthYn()
                , managerMenuDTO.getUpdateAuthYn()
                , managerMenuDTO.getDeleteAuthYn()
                , managerMenuDTO.getReadingAuthYn()
                , managerMenuDTO.getDownloadAuthYn()
                , managerMenuDTO.getUploadAuthYn()
                , managerMenuDTO.getPrintingAuthYn()
                , managerMenuDTO.getExcelAuthYn()
                , managerMenuDTO.getMenuOrder()
                , managerMenuDTO.getUseYn()
                , managerMenuDTO.getUpdaterSeq()
            );
        }
    }

}
