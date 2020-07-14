package com.nike.dnp.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * LocalDateTime Util
 *
 * @author [김형욱]
 * @CreatedOn 2020. 7. 13. 오후 4:53:33
 * @Description LocalDateUtil 작성
 */
@Slf4j
@UtilityClass
public class LocalDateUtil {

    /**
     * String to localDateTime
     *
     * @param strDate the str date
     * @param format  the format
     * @return the string
     */
    public static LocalDateTime strToLocalDateTime(String strDate, String format) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime date = LocalDateTime.parse(strDate, formatter);

        return date;
    }

}
