package com.nike.dnp.repository.menu;

import com.nike.dnp.entity.menu.MenuRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * MenuRoleRepository
 *
 * @author [오지훈]
 * @implNote 메뉴 역할 Repository 작성
 * @since 2020. 7. 7. 오후 5:02:35
 */
@Repository
public interface MenuRoleRepository extends JpaRepository<MenuRole, Long> {

    /**
     * Find by menu seq list.
     *
     * @param menuSeq the menu seq
     * @return the list
     * @author [오지훈]
     * @implNote [Description 작성]
     * @since 2020. 9. 1. 오전 11:26:11
     */
    List<MenuRole> findByMenuSeq(final Long menuSeq);

    /**
     * Find by menu seq and menu skill code list.
     *
     * @param menuSeq       the menu seq
     * @param menuSkillCode the menu skill code
     * @return the list
     * @author [오지훈]
     * @since 2020. 7. 20. 오후 2:44:04
     * @implNote
     */
    List<MenuRole> findByMenuSeqAndMenuSkillCode(final Long menuSeq, final String menuSkillCode);

}
