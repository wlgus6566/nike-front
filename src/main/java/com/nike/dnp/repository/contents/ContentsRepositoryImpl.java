package com.nike.dnp.repository.contents;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.contents.ContentsResultDTO;
import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.dto.contents.ContentsUserEmailDTO;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.contents.QContents;
import com.nike.dnp.entity.user.QUser;
import com.nike.dnp.entity.user.QUserAuth;
import com.nike.dnp.entity.user.QUserContents;
import com.nike.dnp.service.auth.AuthService;
import com.nike.dnp.service.contents.ContentsService;
import com.nike.dnp.util.ObjectMapperUtil;
import com.nike.dnp.util.SecurityUtil;
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
 * @implNote 콘텐츠 repository
 * @since 2020. 6. 19. 오후 5:54:32
 */
@Repository
public class ContentsRepositoryImpl extends QuerydslRepositorySupport implements ContentsRepositoryCustom {


    /**
     * 생성자 주입
     * Instantiates a new Contents repository.
     *
     * @author [이소정]
     * @implNote 생성자 주입
     * @since 2020. 6. 19. 오후 6:15:29
     * @param authService
     */
    public ContentsRepositoryImpl(AuthService authService) {
        super(Contents.class);
        this.contentsService = contentsService;
    }

    private ContentsService contentsService;

    /**
     * Find alls page.
     *
     * @param contentsSearchDTO the contents search dto
     * @param pageRequest       the page request
     * @return the page
     * @author [이소정]
     * @implNote 페이징 처리 된 콘텐츠 목록 조회
     * @since 2020. 6. 19. 오후 5:54:39
     */
    @Override
    public Page<ContentsResultDTO> findPageContents(final ContentsSearchDTO contentsSearchDTO, final PageRequest pageRequest) {
        final JPAQuery<ContentsResultDTO> query = this.findContents(contentsSearchDTO);
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
     * @implNote 최근 콘텐츠 목록 조회
     * @since 2020. 7. 27. 오후 6:39:10
     */
    @Override
    public List<ContentsResultDTO> findRecentContents(final String topMenuCode, final PageRequest pageRequest, final String exposureYn) {
        final ContentsSearchDTO contentsSearchDTO = new ContentsSearchDTO();
        contentsSearchDTO.setTopMenuCode(topMenuCode);
        contentsSearchDTO.setExposureYn(exposureYn);
        contentsSearchDTO.setUserAuthSeq(SecurityUtil.currentUser().getAuthSeq());
        final JPAQuery<ContentsResultDTO> query = this.findContents(contentsSearchDTO);

        return ObjectMapperUtil.mapAll(getQuerydsl().applyPagination(pageRequest, query).fetch(), ContentsResultDTO.class);

    }

    /**
     * Find contents jpa query.
     *
     * @param contentsSearchDTO the contents search dto
     * @return the jpa query
     * @author [이소정]
     * @implNote 콘텐츠 목록 쿼리 공통
     * @since 2020. 8. 28. 오후 9:00:09
     */
    public JPAQuery<ContentsResultDTO> findContents(final ContentsSearchDTO contentsSearchDTO) {
        final QContents qContents = QContents.contents;
        final QUserContents qUserContents = QUserContents.userContents;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        return queryFactory
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
    }

    /**
     * Find all contents mail auth user list.
     *
     * @param contentsSeq the contents seq
     * @return the list
     * @author [이소정]
     * @implNote 콘텐츠 메일 수신자 목록 조회
     * @since 2020. 7. 30. 오후 3:40:06
     */
    @Override
    public List<ContentsUserEmailDTO> findAllContentsMailAuthUser(final long contentsSeq) {
        final QUserContents qUserContents = QUserContents.userContents;
        final QUserAuth qUserAuth = QUserAuth.userAuth;
        final QUser qUser = QUser.user;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        return queryFactory
                .select(
                    Projections.bean(
                        ContentsUserEmailDTO.class
                            , qUser.userId
                ))
                .from(qUserContents)
                .where(qUserContents.contentsSeq.eq(contentsSeq).and(qUserContents.emailReceptionYn.eq("Y")))
                .innerJoin(qUserAuth).on(qUserContents.authSeq.eq(qUserAuth.authSeq))
                .innerJoin(qUser).on(qUserAuth.userSeq.eq(qUser.userSeq).and(qUser.userStatusCode.eq(ServiceCode.UserStatusEnumCode.NORMAL.toString())))
                .fetch();
    }

}
