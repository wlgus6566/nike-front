package com.nike.dnp.repository.notice;

import com.nike.dnp.common.ObjectMapperUtils;
import com.nike.dnp.dto.notice.CustomerListDTO;
import com.nike.dnp.dto.notice.CustomerSearchDTO;
import com.nike.dnp.entity.notice.NoticeArticle;
import com.nike.dnp.entity.notice.QNoticeArticle;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Class Notice repository custom.
 *
 * @author [정주희]
 * @CreatedOn 2020. 7. 13. 오후 6:25:37
 * @Description
 */
@Slf4j
@Repository
public class NoticeRepositoryCustomImpl extends QuerydslRepositorySupport implements NoticeRepositoryCustom{

    public NoticeRepositoryCustomImpl() {super(NoticeArticle.class);}


    /**
     * Find notice pages page.
     *
     * @param customerSearchDTO the notice search dto
     * @param pageRequest     the page request
     * @return the page
     * @author [정주희]
     * @CreatedOn 2020. 7. 13. 오후 10:58:41
     * @Description Customer Center 목록 조회
     */
    @Override
    public Page<CustomerListDTO> findNoticePages(CustomerSearchDTO customerSearchDTO, PageRequest pageRequest) {
        log.info("NoticeRepositoryCustomImpl.findNoticePages");

        QNoticeArticle qNoticeArticle = QNoticeArticle.noticeArticle;
        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        //일반 게시글 조회
        JPAQuery<NoticeArticle> query = queryFactory.selectFrom(qNoticeArticle)
                .where(
                        qNoticeArticle.useYn.eq("Y"),
                        qNoticeArticle.noticeArticleSectionCode.eq(customerSearchDTO.getNoticeArticleSectionCode()),
                        NoticePredicateHelper.eqCategoryCode(customerSearchDTO.getNoticeArticleCategoryCode()),
                        NoticePredicateHelper.containsKeword(customerSearchDTO.getKeyword())
                );

        if (StringUtils.equalsIgnoreCase(customerSearchDTO.getNoticeArticleSectionCode() ,"NOTICE")) {
            query.orderBy(qNoticeArticle.noticeYn.desc(), qNoticeArticle.registrationDt.desc());
        }

        List<CustomerListDTO> customerListDTOList = ObjectMapperUtils.mapAll(
                getQuerydsl().applyPagination(pageRequest, query).fetch(), CustomerListDTO.class);

        return new PageImpl<>(customerListDTOList, pageRequest, query.fetchCount());
    }

    /**
     * Check notice yn cnt long.
     *
     * @return the long
     * @author [정주희]
     * @CreatedOn 2020. 7. 25. 오후 7:57:35
     * @Description 공지사항 등록시 상단 고정된 게시글 개수 확인
     */
    @Override
    public Long checkNoticeYnCnt() {
        log.info("NoticeRepositoryCustomImpl.checkNoticeYnCnt");

        QNoticeArticle qNoticeArticle = QNoticeArticle.noticeArticle;
        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        JPAQuery<NoticeArticle> query = queryFactory.selectFrom(qNoticeArticle)
                .where(
                        qNoticeArticle.useYn.eq("Y"),
                        qNoticeArticle.noticeArticleSectionCode.eq("NOTICE"),
                        qNoticeArticle.noticeYn.eq("Y")
                );
        return query.fetchCount();
    }
}
