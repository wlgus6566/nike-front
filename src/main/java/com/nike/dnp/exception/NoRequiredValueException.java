package com.nike.dnp.exception;

import lombok.Getter;

/**
 * NoRequiredValueException<br><br>
 *     필수 값이 없을 경우
 *
 * @since 2020.06.03
 * @author [이소정]
 * @Description NoRequiredValueException 작성
 * @history [이소정] [2020.06.03] [최초 작성]
 *
 */
@Getter
public class NoRequiredValueException extends RuntimeException {

    private String code;

    public NoRequiredValueException(String code, String msg) {
        super(msg);
        this.code = code;
    }

}
