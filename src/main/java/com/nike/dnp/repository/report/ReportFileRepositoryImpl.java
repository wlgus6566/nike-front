package com.nike.dnp.repository.report;

import com.nike.dnp.dto.contents.ContentsFileResultDTO;
import com.nike.dnp.dto.contents.ContentsFileSearchDTO;
import com.nike.dnp.dto.report.ReportFileResultDTO;
import com.nike.dnp.dto.report.ReportFileSearchDTO;
import com.nike.dnp.entity.contents.ContentsFile;
import com.nike.dnp.entity.contents.QContentsFile;
import com.nike.dnp.entity.report.QReportFile;
import com.nike.dnp.entity.report.ReportFile;
import com.nike.dnp.repository.contents.ContentsFilePredicateHelper;
import com.nike.dnp.repository.contents.ContentsFileRepositoryCustom;
import com.nike.dnp.util.ObjectMapperUtil;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The Class Report file repository.
 *
 * @author [이소정]
 * @since 2020. 8. 13. 오후 7:43:28
 */
@Repository
public class ReportFileRepositoryImpl extends QuerydslRepositorySupport implements ReportFileRepositoryCustom {


    /**
     * 생성자 주입
     * Instantiates a new Report File repository.
     *
     * @author [이소정]
     * @implNote 생서자 주입
     * @since 2020. 6. 19. 오후 6:15:29
     */
    public ReportFileRepositoryImpl() {
        super(ReportFile.class);
    }

    /**
     * Find all report file paging page.
     *
     * @param reportFileSearchDTO the report file search dto
     * @param pageRequest         the page request
     * @return the page
     * @author [이소정]
     * @implNote 보고서 파일 페이징 처리 후 목록 조회
     * @since 2020. 8. 13. 오후 7:49:19
     */
    @Override
    public Page<ReportFileResultDTO> findAllReportFilePaging(final ReportFileSearchDTO reportFileSearchDTO, PageRequest pageRequest) {
        final QReportFile qReportFile = QReportFile.reportFile;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final JPAQuery<ReportFile> query = queryFactory.selectFrom(qReportFile)
                .where(
                        qReportFile.reportSeq.eq(reportFileSearchDTO.getReportSeq())
                        , qReportFile.useYn.eq("Y")
                );
        final List<ReportFileResultDTO> contentsFileResultDTOList = ObjectMapperUtil.mapAll(getQuerydsl().applyPagination(pageRequest, query).fetch(), ReportFileResultDTO.class);
        return new PageImpl<>(contentsFileResultDTOList, pageRequest, query.fetchCount());
    }
}

