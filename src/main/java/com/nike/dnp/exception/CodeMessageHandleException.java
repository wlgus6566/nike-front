package com.nike.dnp.exception;

import lombok.Getter;

import java.io.Serializable;

/**
 * Status 500 Exception, code, message
 *
 * @author [이소정]
 * @implNote Status 500 Exception 작성
 * @since 2020. 6. 24. 오후 6:11:52
 */
@Getter
public class CodeMessageHandleException extends RuntimeException implements Serializable {

    /**
     * 응답 코드
     *
     * @author [이소정]
     */
    private String code;

    /**
     * 응답 내용
     *
     * @author [이소정]
     */
    private String description;

    /**
     * Instantiates a new Code message handle exception.
     *
     * @param code 코드
     * @param msg  메세지
     * @author [이소정]
     * @implNote 생성자 주입
     * @since 2020. 6. 24. 오후 6:11:52
     */
    public CodeMessageHandleException(final String code, final String msg) {
        super(msg);
        this.code = code;
        this.description = msg;
    }
}
