package com.nike.dnp.service.user;

import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.dto.user.UserContentsSaveDTO;
import com.nike.dnp.dto.user.UserContentsSearchDTO;
import com.nike.dnp.entity.auth.AuthMenuRole;
import com.nike.dnp.entity.menu.Menu;
import com.nike.dnp.entity.menu.MenuRole;
import com.nike.dnp.entity.user.UserContents;
import com.nike.dnp.repository.auth.AuthMenuRoleRepository;
import com.nike.dnp.repository.auth.AuthRepository;
import com.nike.dnp.repository.menu.MenuRepository;
import com.nike.dnp.repository.menu.MenuRoleRepository;
import com.nike.dnp.repository.user.UserContentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * UserContentsService
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 2:40:43
 * @Description UserContents(유저 컨텐츠 권한) Service 작성
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserContentsService {

    private final UserContentsRepository userContentsRepository;
    private final MenuRepository menuRepository;
    private final MenuRoleRepository menuRoleRepository;
    private final AuthMenuRoleRepository authMenuRoleRepository;
    private final AuthRepository authRepository;

    /**
     * Save list.
     *
     * @param userContentsSaveDTO the user contents save dto
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 7. 20. 오후 12:30:08
     * @Description 컨텐츠 권한 저장
     */
    public List<UserContents> save (final UserContentsSaveDTO userContentsSaveDTO) {
        log.info("UserContentsService.save");
        userContentsRepository.deleteByContentsSeq(userContentsSaveDTO.getContentsSeq());

        List<UserContents> contents = new ArrayList<>();
        for (UserContentsSaveDTO.AuthCheckDTO authCheckDTO : userContentsSaveDTO.getChecks()) {
            UserContents userContents = userContentsRepository.save(
                    new UserContents().save(
                        userContentsSaveDTO.getContentsSeq()
                        , authCheckDTO.getAuthSeq()
                        , authCheckDTO.getDetailAuthYn()
                        , authCheckDTO.getEmailReceptionYn()
                    )
            );
            contents.add(userContents);
        }

        return contents;
    }

    public List<AuthReturnDTO> getAuthList (final UserContentsSearchDTO userContentsSearchDTO) {
        log.info("UserContentsService.getAuthList");
        final String menuCode = userContentsSearchDTO.getMenuCode();
        final String skillCode = userContentsSearchDTO.getSkillCode();

        Menu menu = menuRepository.findByMenuCode(menuCode);
        List<MenuRole> menuRoles = menuRoleRepository.findByMenuSeqAndMenuSkillCode(
                                        menu.getMenuSeq()
                                        , skillCode);

        int i = 0;
        Long[] menuRoleSeqArray = new Long[menuRoles.size()];
        for (MenuRole menuRole : menuRoles) {
            menuRoleSeqArray[i] = menuRole.getMenuRoleSeq();
            i++;
        }

        List<AuthMenuRole> authMenuRoles = authMenuRoleRepository.findByMenuRoleSeqIn(menuRoleSeqArray);

        int j = 0;
        Long[] authSeqArray = new Long[authMenuRoles.size()];
        for (AuthMenuRole authMenuRole : authMenuRoles) {
            authSeqArray[j] = authMenuRole.getAuthSeq();
            j++;
        }

        return authRepository.findByAuthSeqArray(authSeqArray);
    }



}
