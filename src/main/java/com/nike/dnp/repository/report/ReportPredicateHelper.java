package com.nike.dnp.repository.report;

import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.dto.report.ReportSearchDTO;
import com.nike.dnp.entity.contents.QContents;
import com.nike.dnp.entity.report.QReport;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

/**
 * The Class Report predicate helper.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 7. 오후 4:40:29
 * @Description
 */
@UtilityClass
public class ReportPredicateHelper {

    /**
     * Compare keyword predicate.
     *
     * @param reportSearchDTO the report search dto
     * @return the predicate
     * @author [이소정]
     * @CreatedOn 2020. 7. 7. 오후 4:42:16
     * @Description
     */
    public Predicate compareKeyword(final ReportSearchDTO reportSearchDTO) {
       final BooleanBuilder builder = new BooleanBuilder();
       final String keyword = reportSearchDTO.getKeyword();

       if(!StringUtils.isEmpty(keyword)) {
           builder.and(QReport.report.reportName.contains(keyword));
       }
       return builder;
   }

    /**
     * Eq section code predicate.
     *
     * @param reportSearchDTO the report search dto
     * @return the predicate
     * @author [이소정]
     * @CreatedOn 2020. 7. 7. 오후 4:45:54
     * @Description
     */
    public Predicate eqSectionCode(final ReportSearchDTO reportSearchDTO) {
        final BooleanBuilder builder = new BooleanBuilder();
        final String sectionCode = reportSearchDTO.getSectionCode();

       if (!StringUtils.isEmpty(sectionCode) || !"ALL".equals(sectionCode)) {
           builder.and(QReport.report.reportSectionCode.eq(sectionCode));
       }
       return builder;
   }
}
