package com.nike.dnp.dto.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * ErrorLogSaveDTO
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:09:06
 * @Description ErrorLog(오류 로그) Save DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ErrorLogSaveDTO {

    /**
     * URL
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "url", value = "URL", required = true)
    private String url;

    /**
     * 오류 내용
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "errorContents", value = "오류 내용", required = true)
    private String errorContents;

}
