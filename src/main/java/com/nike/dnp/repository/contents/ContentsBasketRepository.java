package com.nike.dnp.repository.contents;

import com.nike.dnp.entity.contents.ContentsBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface Contents basket repository.
 *
 * @author [이소정]
 * @implNote 콘텐츠 장바구니 repository interface
 * @since 2020. 7. 30. 오후 3:31:04
 */
@Repository
public interface ContentsBasketRepository extends JpaRepository<ContentsBasket, Long>, ContentsBasketRepositoryCustom {

}
