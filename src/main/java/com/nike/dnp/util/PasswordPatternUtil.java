package com.nike.dnp.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PatternUtil
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 23. 오전 9:43:19
 * @Description 패스워드 정규식
 */
@UtilityClass
public class PasswordPatternUtil {

    /**
     * 영문, 숫자, 특수문자
     *
     * @author [오지훈]
     */
    public final String pattern1 = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*?,./\\\\<>|_-[+]=\\`~\\(\\)\\[\\]\\{\\}])[A-Za-z[0-9]!@#$%^&*?,./\\\\<>|_-[+]=\\`~\\(\\)\\[\\]\\{\\}]{8,20}$";

    /**
     * 영문, 숫자
     *
     * @author [오지훈]
     */
    public final String pattern2 = "^[A-Za-z[0-9]]{8,20}$";

    /**
     * 숫자, 특수문자
     *
     * @author [오지훈]
     */
    public final String pattern3 = "^(?=.*[0-9])(?=.*[!@#$%^&*?,./\\\\<>|_-[+]='\\`\";:~\\(\\)\\[\\]\\{\\}])[[0-9]!@#$%^&*?,./\\\\<>|_-[+]='\";:\\`~\\(\\)\\[\\]\\{\\}]{8,20}$";

    /**
     * 문자, 특수문자
     *
     * @author [오지훈]
     */
    public final String pattern4 = "^(?=.*[A-Za-z])(?=.*[!@#$%^&*?,./\\\\<>|_-[+]='\\`\";:~\\(\\)\\[\\]\\{\\}])[[A-Za-z]!@#$%^&*?,./\\\\<>|_-[+]='\";:\\`~\\(\\)\\[\\]\\{\\}]{8,20}$";

    /**
     * 같은 문자, 숫자
     *
     * @author [오지훈]
     */
    public final String pattern5 = "(\\w)\\1";
    //public final String pattern5 = "(\\w)\\1\\1\\1";

    /**
     * The Match
     *
     * @author [오지훈]
     */
    Matcher match;

    /**
     * Pwd regular expression chk string.
     *
     * @param newPwd the new pwd
     * @param oldPwd the old pwd
     * @param userId the user id
     * @return string string
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오전 9:43:19
     * @Description 비밀번호 정규식 체크
     */
    public String pwdRegularExpressionChk(String newPwd, String oldPwd, String userId) {
        boolean chk = false;

        // 특수문자, 영문, 숫자 조합 (8~10 자리)
        match = Pattern.compile(pattern1).matcher(newPwd);
        if(match.find()) {
            chk = true;
        }

        // 영문, 숫자 (8~20 자리)
        match = Pattern.compile(pattern2).matcher(newPwd);
        if(match.find()) {
            chk = true;
        }

        // 특수문자, 숫자 (8~20 자리)
        match = Pattern.compile(pattern3).matcher(newPwd);
        if(match.find()) {
            chk = true;
        }

        // 영문, 특수문자 (8~20 자리)
        match = Pattern.compile(pattern4).matcher(newPwd);
        if(match.find()) {
            chk = true;
        }

        chk = Pattern.compile(pattern1).matcher(newPwd).find();

        if(chk) {
            // 같은 문자 4자리
            if(samePwd(newPwd)) {
                return "동일 문자, 숫자를 4자리 이상 사용할 수 없습니다.";
            }

            // 연속 문자 4자리
            if(continuousPwd(newPwd)) {
                return "연속된 숫자를 4자리 이상 사용할 수 없습니다.";
            }

            // 이전 비밀번호
            if(newPwd.equals(oldPwd)) {
                return "이전 비밀번호와 동일합니다.";
            }

            // 아이디와 동일 문자 4자리
            if(sameId(newPwd, userId)) {
                return "아이디와 동일문자 4자리 이상 사용할 수 없습니다.";
            }
        }

        else {
            return "영문대소문자/숫자/특수문자 3종류 이상 조합 최소 8자리";
        }

        return "정상";
    }

    /**
     * Same pwd boolean.
     *
     * @param pwd the pwd
     * @return boolean boolean
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오전 9:43:19
     * @Description 같은 문자, 숫자 4자리 체크
     */
    public boolean samePwd(String pwd) {
        return Pattern.compile(pattern5).matcher(pwd).find();
    }


    /**
     * Continuous pwd boolean.
     *
     * @param pwd the pwd
     * @return boolean boolean
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오전 9:43:19
     * @Description 연속 문자, 숫자 4자리 체크
     */
    public boolean continuousPwd(String pwd) {
        int o = 0;
        int d = 0;
        int p = 0;
        int n = 0;
        int limit = 4;
        for(int i=0; i<pwd.length(); i++) {
            char tempVal = pwd.charAt(i);
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
     * @CreatedOn 2020. 6. 23. 오전 9:43:19
     * @Description 아이디와 동일 문자 4자리 체크
     */
    public boolean sameId(String pwd, String id) {
        for(int i=0; i<pwd.length()-3; i++) {
            if(id.contains(pwd.substring(i, i+4))) {
                return true;
            }
        }
        return false;
    }
}
