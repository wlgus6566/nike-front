package com.nike.dnp.common.variable;

/**
 * CommonResponse
 *
 * @author [오지훈]
 * @Description enum으로 api 요청 결과에 대한 code, message를 정의합니다.
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
public enum CommonResponse {

    SUCCESS("SUC", "성공"),
    FAIL("ERR", "에러발생");

    /**
     *
     */
    String code;

    /**
     *
     */
    String msg;

    CommonResponse(final String code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
