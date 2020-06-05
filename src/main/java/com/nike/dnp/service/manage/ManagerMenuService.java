package com.nike.dnp.service.manage;

import com.nike.dnp.dto.manage.menu.ManagerMenuSaveDTO;
import com.nike.dnp.entity.manage.ManagerMenu;
import com.nike.dnp.repository.manage.ManagerMenuRepository;
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
     * 전체조회
     *
     * @return the list
     */
    public List<ManagerMenu> findAll() {
        List<ManagerMenu> managerMenus = new ArrayList<>();
        managerMenus.addAll(managerMenuRepository.findAll());
        return managerMenus;
    }

    /**
     * 상세조회
     *
     * @param menuSeq the menu seq
     * @return the optional
     */
    public Optional<ManagerMenu> findById(Long menuSeq) {
        return managerMenuRepository.findById(menuSeq);
    }

    /**
     * 삭제
     *
     * @param menuSeq the menu seq
     */
    public void delete(Long menuSeq) {
        managerMenuRepository.deleteById(menuSeq);
    }

    /**
     * 등록
     *
     * @param managerMenuSaveDTO the manager menu dto
     * @return the long
     */
    @Transactional
    public Long save(ManagerMenuSaveDTO managerMenuSaveDTO) {
        return managerMenuRepository.save(ManagerMenu.builder()
                .menuName(managerMenuSaveDTO.getMenuName())
                .menuPath(managerMenuSaveDTO.getMenuPath())
                .upperMenuCode(managerMenuSaveDTO.getUpperMenuCode())
                .menuCode(managerMenuSaveDTO.getMenuCode())
                .creationAuthYn(managerMenuSaveDTO.getCreationAuthYn())
                .updateAuthYn(managerMenuSaveDTO.getUpdateAuthYn())
                .deleteAuthYn(managerMenuSaveDTO.getDeleteAuthYn())
                .listAuthYn(managerMenuSaveDTO.getListAuthYn())
                .detailAuthYn(managerMenuSaveDTO.getDetailAuthYn())
                .downloadAuthYn(managerMenuSaveDTO.getDownloadAuthYn())
                .uploadAuthYn(managerMenuSaveDTO.getUploadAuthYn())
                .printingAuthYn(managerMenuSaveDTO.getPrintingAuthYn())
                .excelAuthYn(managerMenuSaveDTO.getExcelAuthYn())
                .lowMenuYn(managerMenuSaveDTO.getLowMenuYn())
                .menuOrder(managerMenuSaveDTO.getMenuOrder())
                .useYn(managerMenuSaveDTO.getUseYn())
                .registerSeq(managerMenuSaveDTO.getRegisterSeq())
                .build()).getMenuSeq();
    }

    /**
     * 수정
     *
     * @param menuSeq        the menu seq
     * @param managerMenuSaveDTO the manager menu dto
     */
    @Transactional
    public void update(Long menuSeq, ManagerMenuSaveDTO managerMenuSaveDTO) {
        Optional<ManagerMenu> managerMenu = managerMenuRepository.findById(menuSeq);
        if (managerMenu.isPresent()) {
            managerMenu.get().update(
                managerMenuSaveDTO.getMenuName()
                , managerMenuSaveDTO.getMenuPath()
                , managerMenuSaveDTO.getCreationAuthYn()
                , managerMenuSaveDTO.getUpdateAuthYn()
                , managerMenuSaveDTO.getDeleteAuthYn()
                , managerMenuSaveDTO.getListAuthYn()
                , managerMenuSaveDTO.getDetailAuthYn()
                , managerMenuSaveDTO.getDownloadAuthYn()
                , managerMenuSaveDTO.getUploadAuthYn()
                , managerMenuSaveDTO.getPrintingAuthYn()
                , managerMenuSaveDTO.getExcelAuthYn()
                , managerMenuSaveDTO.getLowMenuYn()
                , managerMenuSaveDTO.getMenuOrder()
                , managerMenuSaveDTO.getUseYn()
                , managerMenuSaveDTO.getUpdaterSeq()
            );
        }
    }

}
