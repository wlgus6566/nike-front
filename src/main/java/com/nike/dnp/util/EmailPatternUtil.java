package com.nike.dnp.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class Email pattern util.
 *
 * @author [오지훈]
 * @since 2020. 7. 1. 오후 2:51:02
 * @implNote 이메일 정규식
 */
@UtilityClass
public class EmailPatternUtil {

    /**
     * Is valid email boolean.
     *
     * @param email the email
     * @return the boolean
     * @author [오지훈]
     * @since 2020. 7. 1. 오후 4:15:28
     * @implNote 정상 이메일 형식 여부
     */
    public boolean isValidEmail(final String email) {
        boolean isValid = false;
        final String regex = "^[_a-zA-Z0-9-]+(.[_a-zA-Z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(email);
        if(matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}
