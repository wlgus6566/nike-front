package com.nike.dnp.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * SingleResult
 *
 * @param <T> the type parameter
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 6:11:45
 * @implNote SingleResult 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class SingleResult<T> extends CommonResult {

    /**
     * T
     *
     * @author [오지훈]
     */
    @ApiModelProperty(value = "응답 데이터", name = "data")
    private T data;

    /**
     * Instantiates a new Single result.
     *
     * @param code     the code
     * @param msg      the msg
     * @param success  the success
     * @param existMsg the exist msg
     * @author [오지훈]
     * @implNote 생성자 주입 *
     * @since 2020. 8. 3. 오전 11:24:06
     */
    public SingleResult(
            final String code
            ,final String msg
            ,final Boolean success
            ,final Boolean existMsg
    ) {
        super();
        this.setCode(code);
        this.setMsg(msg);
        this.setSuccess(success);
        this.setExistMsg(existMsg);
    }

}
