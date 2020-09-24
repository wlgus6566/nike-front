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
        //final String regex = "^[_a-zA-Z0-9-]+(.[_a-zA-Z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        final String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(email);
        if(matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Masking email string.
     *
     * @param email the email
     * @return the string
     * @author [오지훈]
     * @implNote 뒤 3자리 마스킹 처리, @뒷부분 유지
     * @since 2020. 9. 7. 오후 12:09:29
     */
    public String maskingEmail(final String email) {
        String returnEmail = email;
        if (email.length() > 3) {
            if (email.substring(0, email.indexOf("@")).length() > 3) {
                returnEmail = email.substring(0, email.indexOf("@") - 3) + "***" + email.substring(email.indexOf("@"));
            }
        }
        return returnEmail;
    }

    /**
     * Masking email 2 string.
     *
     * @param email the email
     * @return the string
     */
    public String maskingEmail2(final String email) {
        String returnEmail = email;
        if (email.length() > 2) {
            StringBuilder strBuilder = new StringBuilder();
            for (int i=0; i<email.substring(0, email.indexOf("@")).length()-2; i++) {
                strBuilder.append("*");
            }
            returnEmail = email.substring(0,2)+strBuilder.toString()+email.substring(email.indexOf("@"));
        }
        return returnEmail;
    }
}
