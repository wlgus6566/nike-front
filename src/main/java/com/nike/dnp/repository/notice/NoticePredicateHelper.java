package com.nike.dnp.repository.notice;

import com.nike.dnp.entity.notice.QNoticeArticle;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;


/**
 * The Class Notice predicate helper.
 *
 * @author [정주희]
 * @CreatedOn 2020. 7. 13. 오후 10:01:52
 * @Description 목록 조회시 where 조건 helper
 */
@UtilityClass
public class NoticePredicateHelper {

    /**
     * Eq category code predicate.
     *
     * @param categoryCode the category code
     * @return the predicate
     * @author [정주희]
     * @CreatedOn 2020. 7. 13. 오후 10:59:21
     * @Description 카테고리 검색 helper
     */
    public static Predicate eqCategoryCode(String categoryCode) {
        final BooleanBuilder builder = new BooleanBuilder();

        if (!StringUtils.isEmpty(categoryCode)) {
            builder.and(QNoticeArticle.noticeArticle.noticeArticleCategoryCode.eq(categoryCode));
        }

        return builder;
    }

    /**
     * Contains keword predicate.
     *
     * @param keyword the keyword
     * @return the predicate
     * @author [정주희]
     * @CreatedOn 2020. 7. 13. 오후 10:59:49
     * @Description 제목, 내용 keyword 검색 helper
     */
    public static Predicate containsKeword(String keyword) {
        final BooleanBuilder builder = new BooleanBuilder();

        if (!StringUtils.isEmpty(keyword)) {
            builder.and((QNoticeArticle.noticeArticle.contents.contains(keyword))
                    .or(QNoticeArticle.noticeArticle.title.contains(keyword)));
        }

        return builder;
    }

}
