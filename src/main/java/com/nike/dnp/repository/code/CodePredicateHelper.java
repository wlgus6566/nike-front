package com.nike.dnp.repository.code;

import com.nike.dnp.dto.code.CodeSearchDTO;
import com.nike.dnp.entity.code.QCode;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;

/**
 * CodePredicateHelper
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:51:00
 * @implNote Code(공통코드) Predicate Helper 작성
 */
@UtilityClass
public class CodePredicateHelper {

    /**
     * Compare keyword predicate.
     *
     * @param codeSearchDTO the code search dto
     * @return the predicate
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:51:00
     * @implNote 검색 조건
     */
    public static Predicate compareKeyword(final CodeSearchDTO codeSearchDTO) {
        final BooleanBuilder builder = new BooleanBuilder();
        final String keyword = codeSearchDTO.getKeyword();

        if (!keyword.isEmpty()) {
            builder
                .or(QCode.code1.code.eq(keyword))
                .or(QCode.code1.codeName.eq(keyword));
        }

        return builder;
    }

}
