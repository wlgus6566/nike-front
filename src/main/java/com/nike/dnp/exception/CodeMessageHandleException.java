package com.nike.dnp.exception;

import lombok.Getter;

/**
 * Status 500 Exception, code, message
 *
 * @author [이소정]
 * @Description Status 500 Exception 작성
 * @history [이소정] [2020.06.03] [최초 작성]
 * @since 2020.06.03
 */
@Getter
public class CodeMessageHandleException extends RuntimeException {

    /**
     *
     */
    /* default */ static final long serialVersionUID = 100L;

    /**
     *
     */
    private String code;

    /**
     * Instantiates a new Code message handle exception.
     *
     * @param code the code
     * @param msg  the msg
     */
    public CodeMessageHandleException(final String code, final String msg) {
        super(msg);
        this.code = code;
    }
}
