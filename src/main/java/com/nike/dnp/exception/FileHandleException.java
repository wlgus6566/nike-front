package com.nike.dnp.exception;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.util.MessageUtil;
import lombok.Getter;

import java.io.Serializable;

/**
 * Status 500 FileHandleException, code, message
 *
 * @author [이소정]
 * @implNote Status 500 Exception 작성
 * @since 2020. 6. 24. 오후 6:11:52
 */
@Getter
public class FileHandleException extends RuntimeException implements Serializable {

    /**
     * 응답 코드
     *
     * @author [이소정]
     */
    private String code;

    /**
     * Instantiates a new Not found handle exception.
     *
     * @author [이소정]
     * @implNote 생성자 주입
     * @since 2020. 8. 14. 오후 4:28:57
     */
    public FileHandleException() {
        super(MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
        this.code = FailCode.ConfigureError.INVALID_FILE.name();
    }


}
