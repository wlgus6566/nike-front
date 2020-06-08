package com.nike.dnp.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * CommonResult
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description CommonResult 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 *
 */

@Getter
@Setter
public class CommonResult {

    /**
     * @author [오지훈]
     */
    @ApiModelProperty(value = "응답 성공여부 : true/false")
    private boolean success;

    /**
     * @author [오지훈]
     */
    @ApiModelProperty(value = "S0 : 정상 / 그 외 오류 (기본 E0, 각 서비스에 맞는 오류)")
    private String code;

    /**
     * @author [오지훈]
     */
    @ApiModelProperty(value = "응답 메시지")
    private String msg;

}

