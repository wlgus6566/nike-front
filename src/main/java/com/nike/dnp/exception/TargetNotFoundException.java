package com.nike.dnp.exception;

import lombok.Getter;

/**
 * TargetNotFoundException<br><br>
 *     조회한 값이 없을 경우 (목록 제외, ex_상세 페이지 등)
 *
 * @since 2020.06.03
 * @author [이소정]
 * @Description TargetNotFoundException 작성
 * @history [이소정] [2020.06.03] [최초 작성]
 *
 */
@Getter
public class TargetNotFoundException extends RuntimeException {

    private String code;

    public TargetNotFoundException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}
