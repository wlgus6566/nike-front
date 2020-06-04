package com.nike.dnp.exception;

import lombok.Getter;

/**
 * Status 200 Exception, code, message
 *
 * @since 2020.06.03
 * @author [이소정]
 * @Description Status 200 Exception 작성
 * @history [이소정] [2020.06.03] [최초 작성]
 *
 */
@Getter
public class Status200Exception extends RuntimeException {

    private String code;

    public Status200Exception(String code, String msg) {
        super(msg);
        this.code = code;
    }
}
