package com.nike.dnp.repository.contents;

import com.nike.dnp.dto.contents.ContentsResultDTO;
import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.dto.contents.ContentsUserEmailDTO;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.contents.QContents;
import com.nike.dnp.entity.user.QUser;
import com.nike.dnp.entity.user.QUserAuth;
import com.nike.dnp.entity.user.QUserContents;
import com.nike.dnp.util.ObjectMapperUtil;
import com.querydsl.core.types.Projections;
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
 * @since 2020. 6. 19. 오후 5:54:32
 */
@Repository
public class ContentsRepositoryImpl extends QuerydslRepositorySupport implements ContentsRepositoryCustom {


    /**
     * 생성자 주입
     * Instantiates a new Contents repository.
     *
     * @author [이소정]
     * @since 2020. 6. 19. 오후 6:15:29
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
     * @since 2020. 6. 19. 오후 5:54:39
     */
    @Override
    public Page<ContentsResultDTO> findPageContents(final ContentsSearchDTO contentsSearchDTO, final PageRequest pageRequest) {
        final QContents qContents = QContents.contents;
        final QUserContents qUserContents = QUserContents.userContents;

        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final JPAQuery<ContentsResultDTO> query = queryFactory
                .select(Projections.bean(
                            ContentsResultDTO.class
                            , qContents.contentsSeq
                            , qContents.topMenuCode
                            , qContents.menuCode
                            , qContents.imageFileName
                            , qContents.imageFileSize
                            , qContents.imageFilePhysicalName
                            , qContents.folderName
                            , qContents.folderContents
                            , qContents.campaignPeriodSectionCode
                            , qContents.campaignBeginDt
                            , qContents.campaignEndDt
                            , qContents.readCount
                            , qContents.exposureYn
                            , qUserContents.detailAuthYn
                    )
                )
                .from(qContents)
                .leftJoin(qUserContents).on(qContents.contentsSeq.eq(qUserContents.contentsSeq).and(qUserContents.authSeq.eq(contentsSearchDTO.getUserAuthSeq())))
                .where(
                        ContentsPredicateHelper.compareKeyword(contentsSearchDTO)
                        , ContentsPredicateHelper.eqMenuCode(contentsSearchDTO)
                        , ContentsPredicateHelper.eqExposureYn(contentsSearchDTO.getExposureYn())
                        , qContents.useYn.eq("Y")
                );

        final List<ContentsResultDTO> contentsList = ObjectMapperUtil.mapAll(getQuerydsl().applyPagination(pageRequest, query).fetch(), ContentsResultDTO.class);

        return new PageImpl<>(contentsList, pageRequest, query.fetchCount());
    }

    /**
     * Find recent contents list.
     *
     * @param topMenuCode the top menu code
     * @param pageRequest the page request
     * @return the list
     * @author [이소정]
     * @since 2020. 7. 27. 오후 6:39:10
     */
    public List<ContentsResultDTO> findRecentContents(final String topMenuCode, final PageRequest pageRequest) {
        ContentsSearchDTO contentsSearchDTO = new ContentsSearchDTO();
        contentsSearchDTO.setTopMenuCode(topMenuCode);

        final QContents qContents = QContents.contents;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        final JPAQuery<ContentsResultDTO> query = queryFactory
                .select(Projections.bean(
                        ContentsResultDTO.class
                        , qContents.contentsSeq
                        , qContents.topMenuCode
                        , qContents.menuCode
                        , qContents.imageFileName
                        , qContents.imageFileSize
                        , qContents.imageFilePhysicalName
                        , qContents.folderName
                        , qContents.folderContents
                        , qContents.campaignPeriodSectionCode
                        , qContents.campaignBeginDt
                        , qContents.campaignEndDt
                        , qContents.readCount
                        , qContents.exposureYn
                        )
                )
                .from(qContents)
                .where(
                        ContentsPredicateHelper.eqMenuCode(contentsSearchDTO)
                        , ContentsPredicateHelper.eqExposureYn(contentsSearchDTO.getExposureYn())
                        , qContents.useYn.eq("Y")
                );

        return ObjectMapperUtil.mapAll(getQuerydsl().applyPagination(pageRequest, query).fetch(), ContentsResultDTO.class);

    }

    /**
     * Find all contents mail auth user list.
     *
     * @param contentsSeq the contents seq
     * @return the list
     */
    @Override
    public List<ContentsUserEmailDTO> findAllContentsMailAuthUser(long contentsSeq) {
        final QUserContents qUserContents = QUserContents.userContents;
        final QContents qContents = QContents.contents;
        final QUserAuth qUserAuth = QUserAuth.userAuth;
        final QUser qUser = QUser.user;

        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        List<ContentsUserEmailDTO> userEmailDTOList = queryFactory.select(
                Projections.bean(
                    ContentsUserEmailDTO.class
                        , qUser.userId
                        , qContents.imageFilePhysicalName
                ))
                .from(qUserContents)
                .where(qUserContents.contentsSeq.eq(contentsSeq))
                .innerJoin(qContents).on(qContents.contentsSeq.eq(contentsSeq))
                .innerJoin(qUserAuth).on(qUserContents.authSeq.eq(qUserAuth.authSeq))
                .innerJoin(qUser).on(qUserAuth.userSeq.eq(qUser.userSeq))
                .fetch();

        return userEmailDTOList;
    }

}
