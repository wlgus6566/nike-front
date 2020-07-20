package com.nike.dnp.repository.menu;

import com.nike.dnp.entity.menu.MenuRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * MenuRoleRepository
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 7. 오후 5:02:35
 * @Description 메뉴 역할 Repository 작성
 */
@Repository
public interface MenuRoleRepository extends JpaRepository<MenuRole, Long> {

    /**
     * Find by menu seq and menu skill code list.
     *
     * @param menuSeq       the menu seq
     * @param menuSkillCode the menu skill code
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 7. 20. 오후 2:44:04
     * @Description
     */
    List<MenuRole> findByMenuSeqAndMenuSkillCode(final Long menuSeq, final String menuSkillCode);

}
