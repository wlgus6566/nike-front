package com.nike.dnp.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ResultRestDialog Util
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description ResultRestDialog 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */

@JsonInclude(JsonInclude.Include.NON_NULL) // null 일경우 제외
@Data
public class ResultRestDialog {

    /**
     * 결과 코드 (SUCCESS / ERROR)
     */
    @ApiModelProperty(position = 1, notes = "결과 코드")
    private String result;

    /**
     * 결과 데이터 VO, LIST 포함
     */
    @ApiModelProperty(position = 2, notes = "결과 데이터 VO, LIST 포함")
    private Object dialog;

}
