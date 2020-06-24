package com.nike.dnp.common.variable;

/**
 * UserEnumCode
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 23. 오후 5:22:14
 * @Description 회원 상태 코드 enum 작성
 */
public enum UserStatusEnumCode {

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
     * Instantiates a new User status enum code.
     *
     * @param value - 코드값
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 5:39:17
     * @Description 생성자
     */
    UserStatusEnumCode(final String value) {
        this.value = value;
    }

    /**
     * Gets message.
     *
     * @return value - 코드값
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 5:39:17
     * @Description 코드값
     */
    public String getMessage() {
        return value;
    }

}
