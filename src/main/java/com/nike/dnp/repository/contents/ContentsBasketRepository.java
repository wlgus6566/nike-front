package com.nike.dnp.repository.contents;

import com.nike.dnp.entity.contents.ContentsBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface Contents basket repository.
 *
 * @author [오지훈]
 * @since 2020. 7. 31. 오후 4:12:09
 */
@Repository
public interface ContentsBasketRepository extends JpaRepository<ContentsBasket, Long>, ContentsBasketRepositoryCustom {

}
