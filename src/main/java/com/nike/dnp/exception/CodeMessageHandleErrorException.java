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
public class CodeMessageHandleErrorException extends RuntimeException implements Serializable {

    /**
     * Instantiates a new Code message handle exception.
     *
     * @author [이소정]
     * @implNote 생성자 주입
     * @since 2020. 6. 24. 오후 6:11:52
     */
    public CodeMessageHandleErrorException() {}
}
