package com.nike.dnp.util;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class DateUtil {

    public DateTemplate<LocalDateTime> formatDate(String date, String pattern) {
        return Expressions.dateTemplate(LocalDateTime.class, "DATE_FORMAT({0},{1})", date, ConstantImpl.create(pattern));
    }

}
