package com.nike.dnp.repository.manage;

import com.nike.dnp.entity.manage.ManagerMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ManagerMenuRepository
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description 관리메뉴 Repository 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Repository
public interface ManagerMenuRepository extends JpaRepository<ManagerMenu, Long> {
}
