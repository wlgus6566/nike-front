package com.nike.dnp.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * SingleResult
 *
 * @param <T> the type parameter
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:11:45
 * @Description SingleResult 작성
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

    public SingleResult(
            final String code
            ,final String msg
            ,final Boolean success
            ,final Boolean existMsg
    ) {
        this.setCode(code);
        this.setMsg(msg);
        this.setSuccess(success);
        this.setExistMsg(existMsg);
    }

}
