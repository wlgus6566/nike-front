package com.nike.dnp.repository.example;

import com.nike.dnp.dto.example.manager.ManagerSearchDTO;
import com.nike.dnp.entity.example.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * ManagerRepository
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description 관리자(유저) Repository 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */
@Repository
public interface ManagerRepositoryCustom {

    /**
     * Find alls page.
     *
     * @param managerSearchDTO the manager search dto
     * @param pageRequest      the page request
     * @return the page
     */
    Page<Manager> findAlls(final ManagerSearchDTO managerSearchDTO, final PageRequest pageRequest);

}
