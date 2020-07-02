package com.nike.dnp.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class Email pattern util.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 1. 오후 2:51:02
 * @Description 이메일 정규식
 */
@UtilityClass
public class EmailPatternUtil {

    /**
     * Is email boolean.
     *
     * @param email the email
     * @return the boolean
     * @author [오지훈]
     * @CreatedOn 2020. 7. 1. 오후 2:51:03
     * @Description 정상 이메일 형식 여부
     */
    public static boolean isEmail(String email) {
        if (email.isEmpty()) {
            return false;
        }
        return Pattern.matches(
                "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+",
                email.trim());
    }

    /**
     * Is valid email boolean.
     *
     * @param email the email
     * @return the boolean
     * @author [오지훈]
     * @CreatedOn 2020. 7. 1. 오후 4:15:28
     * @Description 정상 이메일 형식 여부
     */
    public static boolean isValidEmail(String email) {
        boolean isValid = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}
