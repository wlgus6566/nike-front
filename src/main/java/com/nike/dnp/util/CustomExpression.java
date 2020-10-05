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
 * @since 2020. 7. 2. 오후 12:24:28
 * @implNote
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
     * @since 2020. 7. 2. 오후 12:24:16
     * @implNote Expressions LocalDateTime formatDate
     */
    public DateTemplate<LocalDateTime> formatDate(final String date, final String pattern) {
        return Expressions.dateTemplate(LocalDateTime.class, "DATE_FORMAT({0},{1})", date, ConstantImpl.create(pattern));
    }

    /**
     * Date diff number expression.
     *
     * @param date the date
     * @return the number expression
     * @author [오지훈]
     * @since 2020. 7. 2. 오후 2:37:40
     * @implNote Expressions DateDiff
     */
    public NumberExpression<Integer> dateDiff(final DateTimePath<LocalDateTime> date) {
        return Expressions.numberTemplate(
                Integer.class
                , "DATEDIFF(SYSDATE(), {0})"
                , date);
    }

    /**
     * Date add date template.
     *
     * @param date     the date
     * @param interval the interval
     * @return the date template
     * @author [오지훈]
     * @implNote Expressions ADDDATE
     * @since 2020. 10. 5. 오후 4:26:51
     */
    public DateTemplate<LocalDateTime> dateAdd(final DateTimePath<LocalDateTime> date, final int interval) {
        return Expressions.dateTemplate(
                LocalDateTime.class
                , "ADDDATE({0}, {1})"
                , date
                , interval);
    }

    /**
     * Date diff and add number expression.
     *
     * @param date     the date
     * @param interval the interval
     * @return the number expression
     * @author [오지훈]
     * @implNote Expressions DATEDIFF & ADDDATE
     * @since 2020. 10. 5. 오후 4:26:52
     */
    public NumberExpression<Integer> dateDiffAndAdd(final DateTimePath<LocalDateTime> date, final int interval) {
        return Expressions.numberTemplate(
                Integer.class
                , "DATEDIFF(ADDDATE({0}, {1}), NOW())"
                , date
                , interval);
    }

}
