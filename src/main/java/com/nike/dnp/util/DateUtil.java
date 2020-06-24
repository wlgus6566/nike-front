package com.nike.dnp.util;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

/**
 * The Class Date util.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:03:22
 * @Description DateUtil 작성
 */
@UtilityClass
public class DateUtil {

    /**
     * Format date date template.
     *
     * @param date    the date
     * @param pattern the pattern
     * @return the date template
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 6:03:22
     * @Description Expression LocalDateTime 포맷
     */
    public DateTemplate<LocalDateTime> formatDate(String date, String pattern) {
        return Expressions.dateTemplate(LocalDateTime.class, "DATE_FORMAT({0},{1})", date, ConstantImpl.create(pattern));
    }

}
