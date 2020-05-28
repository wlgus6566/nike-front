package com.nike.dnp.repository.manage;

import com.nike.dnp.entity.manage.ManagerMenuAuthMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ManagerMenuAuthMappingRepository
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description 관리 메뉴&권한 맵핑 Repository 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Repository
public interface ManagerMenuAuthMappingRepository extends JpaRepository<ManagerMenuAuthMapping, Long> {

    List<ManagerMenuAuthMapping> findByManagerMenu(Long authSeq);

}
