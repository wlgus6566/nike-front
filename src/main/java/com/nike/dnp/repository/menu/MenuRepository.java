package com.nike.dnp.repository.menu;

import com.nike.dnp.entity.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The Interface Menu repository.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 7. 오후 5:02:35
 * @Description 메뉴 Repository 작성
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByUseYn(final String useYn);

}
