package com.nike.dnp.common.variable;

/**
 * UserEnumCode
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 23. 오후 5:22:14
 * @Description
 */
public enum UserEnumCode {

    /**
     * Normal user enum code
     *
     * @author [오지훈]
     */
    NORMAL("정상"),
    /**
     * Dormant user enum code
     *
     * @author [오지훈]
     */
    DORMANT("휴면"),
    /**
     * Out user enum code
     *
     * @author [오지훈]
     */
    OUT("탈퇴");

    /**
     * 코드값
     *
     * @author [오지훈]
     */
    private final String value;

    /**
     * 생성자
     *
     * @param value - 코드값
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 5:39:17
     * @Description
     */
    UserEnumCode(final String value) {
        this.value = value;
    }

    /**
     * 코드값
     *
     * @return value - 코드값
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 5:39:17
     * @Description
     */
    public String getMessage() {
        return value;
    }

}
