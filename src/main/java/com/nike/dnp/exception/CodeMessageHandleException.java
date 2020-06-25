package com.nike.dnp.exception;

import lombok.Getter;

import java.io.Serializable;

/**
 * Status 500 Exception, code, message
 *
 * @author [이소정]
 * @CreatedOn 2020. 6. 24. 오후 6:11:52
 * @Description Status 500 Exception 작성
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
     * Instantiates a new Code message handle exception.
     *
     * @param code the code
     * @param msg  the msg
     * @author [이소정]
     * @CreatedOn 2020. 6. 24. 오후 6:11:52
     * @Description
     */
    public CodeMessageHandleException(final String code, final String msg) {
        super(msg);
        this.code = code;
    }
}
