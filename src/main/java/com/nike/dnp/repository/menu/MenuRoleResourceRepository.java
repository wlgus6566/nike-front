package com.nike.dnp.repository.menu;

import com.nike.dnp.entity.menu.MenuRoleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The Interface Menu role repository repository.
 *
 * @author [오지훈]
 * @since 2020. 7. 13. 오전 11:14:38
 * @implNote 메뉴 역할 리소스 Repository 작성
 */
@Repository
public interface MenuRoleResourceRepository extends JpaRepository<MenuRoleResource, Long>, MenuRoleResourceRepositoryCustom {
}
