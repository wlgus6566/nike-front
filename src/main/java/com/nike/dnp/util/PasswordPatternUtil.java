package com.nike.dnp.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

/**
 * PatternUtil
 *
 * @author [오지훈]
 * @since 2020. 6. 23. 오전 9:43:19
 * @implNote 패스워드 정규식
 */
@UtilityClass
public class PasswordPatternUtil {

    /**
     * 영문, 숫자, 특수문자
     *
     * @author [오지훈]
     */
    public transient String PATTERN = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*?,./\\\\<>|_-[+]=\\`~\\(\\)\\[\\]\\{\\}])[A-Za-z[0-9]!@#$%^&*?,./\\\\<>|_-[+]=\\`~\\(\\)\\[\\]\\{\\}]{8,16}$";

    /**
     * 영문, 숫자
     *
     * @author [오지훈]
     */
    public transient String pattern2 = "^[A-Za-z[0-9]]{8,16}$";

    /**
     * 숫자, 특수문자
     *
     * @author [오지훈]
     */
    public transient String pattern3 = "^(?=.*[0-9])(?=.*[!@#$%^&*?,./\\\\<>|_-[+]='\\`\";:~\\(\\)\\[\\]\\{\\}])[[0-9]!@#$%^&*?,./\\\\<>|_-[+]='\";:\\`~\\(\\)\\[\\]\\{\\}]{8,16}$";

    /**
     * 문자, 특수문자
     *
     * @author [오지훈]
     */
    public transient String pattern4 = "^(?=.*[A-Za-z])(?=.*[!@#$%^&*?,./\\\\<>|_-[+]='\\`\";:~\\(\\)\\[\\]\\{\\}])[[A-Za-z]!@#$%^&*?,./\\\\<>|_-[+]='\";:\\`~\\(\\)\\[\\]\\{\\}]{8,16}$";

    /**
     * 같은 문자, 숫자
     *
     * @author [오지훈]
     */
    public transient String PATTERN_5 = "(\\w)\\1";

    /**
     * Invalid password boolean.
     *
     * @param newPwd the new pwd
     * @return the boolean
     * @author [오지훈]
     * @since 2020. 6. 23. 오전 9:43:19
     * @implNote 비밀번호 정규식 체크
     */
    public Boolean invalidPassword(final String newPwd) {
        return !(Pattern.compile(PATTERN).matcher(newPwd).find()
                && !Pattern.compile(PATTERN_5).matcher(newPwd).find()
                && !continuousPassword(newPwd));
    }

    /**
     * Continuous pwd boolean.
     *
     * @param pwd the pwd
     * @return boolean boolean
     * @author [오지훈]
     * @since 2020. 6. 23. 오전 9:43:19
     * @implNote 연속 문자, 숫자 4자리 체크
     */
    public boolean continuousPassword(final String pwd) {
        int o = 0;
        int d = 0;
        int p = 0;
        int n = 0;
        final int limit = 4;
        for(int i=0; i<pwd.length(); i++) {
            final char tempVal = pwd.charAt(i);
            if(i > 0 && (p = o - tempVal) > -2 && (n = p == d ? n + 1 :0) > limit -3) {
                return true;
            }
            d = p;
            o = tempVal;
        }
        return false;
    }


    /**
     * Same id boolean.
     *
     * @param pwd the pwd
     * @param id  the id
     * @return boolean boolean
     * @author [오지훈]
     * @since 2020. 6. 23. 오전 9:43:19
     * @implNote 아이디와 동일체크
     */
    public boolean sameId(final String pwd, final String id) {
        return pwd.equals(id);
    }

    /**
     * Same length id boolean.
     *
     * @param pwd the pwd
     * @param id  the id
     * @return the boolean
     * @author [오지훈]
     * @since 2020. 7. 2. 오전 11:04:47
     * @implNote 아이디와 동일 문자 4자리 체크
     */
    public boolean sameLengthId(final String pwd, final String id) {
        for(int i=0; i<pwd.length()-3; i++) {
            if(id.contains(pwd.substring(i, i+4))) {
                return true;
            }
        }

        return false;
    }
}
