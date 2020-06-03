package com.nike.dnp.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    @ApiModelProperty(value = "응답 성공여부 : true/false")
    private boolean success;

    @ApiModelProperty(value = "S0 : 정상 / 그 외 오류 (기본 E0, 각 서비스에 맞는 오류)")
    private String code;

    @ApiModelProperty(value = "응답 메시지")
    private String msg;

}

