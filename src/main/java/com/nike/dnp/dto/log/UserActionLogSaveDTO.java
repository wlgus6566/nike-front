package com.nike.dnp.dto.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * UserActionLogSaveDTO
 *
 * @author [오지훈]
 * @Description UserActionLog(유저_활동_로그) Save DTO 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class UserActionLogSaveDTO {

    /**
     * 사용자 시퀀스
     * @author [오지훈]
     */
    @ApiModelProperty(name = "userSeq", value = "사용자 시퀀스", required = true)
    private Long userSeq;

    /**
     * URL
     * @author [오지훈]
     */
    @ApiModelProperty(name = "url", value = "URL", required = true)
    private String url;

    /**
     * Parameter
     * @author [오지훈]
     */
    @ApiModelProperty(name = "parameter", value = "Parameter", required = true)
    private String parameter;

    /**
     * 최초 작성자
     * @author [오지훈]
     */
    @ApiModelProperty(name = "registerSeq", value = "최초 작성자 시퀀스", hidden = true)
    private Long registerSeq;

}
