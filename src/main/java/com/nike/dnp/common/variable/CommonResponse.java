package com.nike.dnp.common.variable;

import lombok.Getter;

/**
 * CommonResponse
 *
 * @author [오지훈]
 * @Description enum으로 api 요청 결과에 대한 code, message를 정의합니다.
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
public enum CommonResponse {

    /**
     * Success common response
     *
     * @author [오지훈]
     */
    SUCCESS("SUC", "성공"),
    /**
     * Fail common response
     *
     * @author [오지훈]
     */
    FAIL("ERR", "에러발생");

    /**
     * 응답 코드
     *
     * @author [오지훈]
     */
    String code;

    /**
     * 응답 메시지
     *
     * @author [오지훈]
     */
    String msg;

    /**
     * Instantiates a new Common response.
     *
     * @param code the code
     * @param msg  the msg
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 6:05:59
     * @Description 생성자 주입
     */
    CommonResponse(final String code, final String msg) {
        this.code = code;
        this.msg = msg;
    }
}
