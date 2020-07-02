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

    /**
     * Instantiates a new Code message handle exception.
     *
     * @param code      the code
     * @param exception the exception
     * @author [오지훈]
     * @CreatedOn 2020. 7. 2. 오전 11:05:44
     * @Description
     */
    public CodeMessageHandleException(final String code, final Exception exception) {
        super(exception);
        this.code = code;
    }

    /**
     * Instantiates a new Code message handle exception.
     *
     * @param code      the code
     * @param throwable the throwable
     * @author [오지훈]
     * @CreatedOn 2020. 7. 2. 오후 12:03:43
     * @Description
     */
    public CodeMessageHandleException(final String code, final Throwable throwable) {
        super(throwable);
        this.code = code;
    }
}
