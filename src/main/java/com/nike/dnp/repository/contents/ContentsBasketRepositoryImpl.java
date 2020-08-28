package com.nike.dnp.repository.contents;

import com.nike.dnp.dto.contents.ContentsBasketResultDTO;
import com.nike.dnp.entity.contents.ContentsBasket;
import com.nike.dnp.entity.contents.QContentsBasket;
import com.nike.dnp.entity.contents.QContentsFile;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The Class Contents basket repository.
 *
 * @author [이소정]
 * @implNote 콘텐츠 장바구니 repository
 * @since 2020. 7. 15. 오후 12:19:07
 */
@Repository
public class ContentsBasketRepositoryImpl extends QuerydslRepositorySupport implements ContentsBasketRepositoryCustom {

    /**
     * 생성자 주입
     * Instantiates a new Contents repository.
     *
     * @author [이소정]
     * @implNote 생성자 주입
     * @since 2020. 6. 19. 오후 6:15:29
     */
    public ContentsBasketRepositoryImpl() {
        super(ContentsBasket.class);
    }

    /**
     * Find all with contents file list.
     *
     * @param userSeq 사용자 seq
     * @return the list
     * @author [이소정]
     * @implNote 사용자에 맞는 콘텐츠 장바구니 목록 조회
     * @since 2020. 7. 15. 오후 12:19:14
     */
    @Override
    public List<ContentsBasketResultDTO> findAllWithContentsFile(final Long userSeq) {
        final QContentsBasket qContentsBasket = QContentsBasket.contentsBasket;
        final QContentsFile qContentsFile = QContentsFile.contentsFile;

        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        return queryFactory
                .select(Projections.bean(
                        ContentsBasketResultDTO.class
                        , qContentsFile.fileName
                        , qContentsFile.fileSize
                        , qContentsFile.thumbnailFilePhysicalName
                        , qContentsFile.fileExtension
                        , qContentsFile.fileKindCode
                        , qContentsBasket.userSeq
                        , qContentsBasket.contentsFileSeq
                        , qContentsBasket.contentsBasketSeq) )
                .from(qContentsBasket)
                .innerJoin(qContentsFile).on(qContentsBasket.contentsFileSeq.eq(qContentsFile.contentsFileSeq))
                .where(qContentsBasket.userSeq.eq(userSeq))
                .orderBy(qContentsBasket.registrationDt.desc())
                .fetch();
    }
}

