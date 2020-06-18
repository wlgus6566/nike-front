package com.nike.dnp.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * CommonResult
 *
 * @author [오지훈]
 * @Description CommonResult 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * @since 2020.05.21
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class CommonResult {

    /**
     * 응답 성공 여부
     * @author [오지훈]
     */
    @ApiModelProperty(value = "응답 성공 여부 : true/false", name = "success")
    private boolean success;

    /**
     * 응답 코드
     * @author [오지훈]
     */
    @ApiModelProperty(value = "S0 : 정상 / 그 외 오류 (기본 E0, 각 서비스에 맞는 오류)", name = "code")
    private String code;

    /**
     * 응답 메시지
     * @author [오지훈]
     */
    @ApiModelProperty(value = "응답 메시지", name = "msg")
    private String msg;

}

