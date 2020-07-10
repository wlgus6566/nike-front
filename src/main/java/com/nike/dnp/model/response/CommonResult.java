package com.nike.dnp.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.HashMap;

/**
 * CommonResult
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:11:42
 * @Description CommonResult 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommonResult {

    /**
     * 응답 성공 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(value = "응답 성공 여부 : true/false", name = "isOK", allowableValues = "true")
    private boolean success;

    /**
     * 메시지 리턴 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(value = "메시지 리턴 여부 : true/false", name = "existMsg", required = true, allowableValues = "false")
    private boolean existMsg = false;

    /**
     * 응답 코드
     *
     * @author [오지훈]
     */
    @ApiModelProperty(value = "S0 : 정상 / 그 외 오류 (기본 E0, 각 서비스에 맞는 오류)", name = "code", example = "SUC")
    private String code = "";

    /**
     * 응답 메시지
     *
     * @author [오지훈]
     */
    @ApiModelProperty(value = "응답 메시지", name = "msg", example = "성공")
    private String msg = "";

    /**
     * 응답 데이터
     *
     * @author [오지훈]
     */
    @ApiModelProperty(value = "추가 응답 데이터", name = "payload", allowableValues = "{}")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private HashMap<String, Object> payload;

}

