package com.nike.dnp.util;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;


/**
 * The Class Date expression.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 2. 오후 12:24:28
 * @Description
 */
@UtilityClass
public class CustomExpression {

    /**
     * Format date date template.
     *
     * @param date    the date
     * @param pattern the pattern
     * @return the date template
     * @author [오지훈]
     * @CreatedOn 2020. 7. 2. 오후 12:24:16
     * @Description Expressions LocalDateTime formatDate
     */
    public DateTemplate<LocalDateTime> formatDate(String date, String pattern) {
        return Expressions.dateTemplate(LocalDateTime.class, "DATE_FORMAT({0},{1})", date, ConstantImpl.create(pattern));
    }

    /**
     * Date diff number expression.
     *
     * @param date the date
     * @return the number expression
     * @author [오지훈]
     * @CreatedOn 2020. 7. 2. 오후 2:37:40
     * @Description Expressions DateDiff
     */
    public NumberExpression<Integer> dateDiff(DateTimePath<LocalDateTime> date) {
        return Expressions.numberTemplate(Integer.class, "DATEDIFF(SYSDATE(), {0})", date);
    }

}
