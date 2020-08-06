package com.nike.dnp.repository.contents;

import com.nike.dnp.entity.contents.ContentsBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Interface Contents basket repository.
 *
 * @author [이소정]
 * @implNote 콘텐츠 장바구니 repository interface
 * @since 2020. 7. 30. 오후 3:31:04
 */
@Repository
public interface ContentsBasketRepository extends JpaRepository<ContentsBasket, Long>, ContentsBasketRepositoryCustom {

    /**
     * Find all by contents file seq list.
     *
     * @param contensFileSeq the contens file seq
     * @return the list
     * @author [이소정]
     * @implNote 파일 seq로 콘텐츠 장바구니 목록 조회
     * @since 2020. 8. 3. 오후 6:45:05
     */
    List<ContentsBasket> findAllByContentsFileSeq(Long contensFileSeq);

}
