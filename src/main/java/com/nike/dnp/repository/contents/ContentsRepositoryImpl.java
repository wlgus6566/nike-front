package com.nike.dnp.repository.contents;

import com.nike.dnp.common.ObjectMapperUtils;
import com.nike.dnp.dto.contents.ContentsResultDTO;
import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.contents.QContents;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contents Repository Impl
 *
 * @author [이소정]
 * @CreatedOn 2020. 6. 19. 오후 5:54:32
 * @Description Contents RepositoryImpl 작성
 */
@Repository
public class ContentsRepositoryImpl extends QuerydslRepositorySupport implements ContentsRepositoryCustom {


    /**
     * 생성자 주입
     * Instantiates a new Contents repository.
     *
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 6:15:29
     * @Description
     */
    public ContentsRepositoryImpl() {
        super(Contents.class);
    }

    /**
     * Find alls page.
     *
     * @param contentsSearchDTO the contents search dto
     * @param pageRequest       the page request
     * @return the page
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 5:54:39
     * @Description
     */
    @Override
    public Page<ContentsResultDTO> findPageContents(final ContentsSearchDTO contentsSearchDTO, final PageRequest pageRequest) {
        final QContents qContents = QContents.contents;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final JPAQuery<Contents> query = queryFactory.selectFrom(qContents)
                .where(
                        ContentsPredicateHelper.compareKeyword(contentsSearchDTO)
                        , ContentsPredicateHelper.eqMenuCode(contentsSearchDTO)
                        , ContentsPredicateHelper.eqExposureYn("Y")
                        , qContents.useYn.eq("Y")
                );

        final List<ContentsResultDTO> contentsList = ObjectMapperUtils.mapAll(getQuerydsl().applyPagination(pageRequest, query).fetch(), ContentsResultDTO.class);
        return new PageImpl<>(contentsList, pageRequest, query.fetchCount());

    }
}
