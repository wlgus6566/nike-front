package com.nike.dnp.dto.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * ErrorLogSaveDTO
 *
 * @author [오지훈]
 * @Description ErrorLog(오류 로그) Save DTO 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ErrorLogSaveDTO {

    /**
     * URL
     * @author [오지훈]
     */
    @ApiModelProperty(name = "url", value = "URL", required = true)
    private String url;

    /**
     * 오류 내용
     * @author [오지훈]
     */
    @ApiModelProperty(name = "errorContents", value = "오류 내용", required = true)
    private String errorContents;

    /**
     * 최초 작성자
     * @author [오지훈]
     */
    @ApiModelProperty(name = "registerSeq", value = "최초 작성자 시퀀스", hidden = true)
    private Long registerSeq;

}
