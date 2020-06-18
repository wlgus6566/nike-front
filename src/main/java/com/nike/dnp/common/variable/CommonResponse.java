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
     * 응답 코드
     * @author [오지훈]
     */
    String code;

    /**
     * 응답 메시지
     * @author [오지훈]
     */
    String msg;

    /**
     * 생성자 주입
     * @param code
     * @param msg
     * @author [오지훈]
     */
    CommonResponse(final String code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Gets code.
     *
     * @return the code
     * @author [오지훈]
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets msg.
     *
     * @return the msg
     * @author [오지훈]
     */
    public String getMsg() {
        return msg;
    }

}
