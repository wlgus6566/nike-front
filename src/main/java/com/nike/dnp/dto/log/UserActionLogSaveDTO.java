package com.nike.dnp.dto.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;

/**
 * UserActionLogSaveDTO
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:09:08
 * @Description UserActionLog(유저_활동_로그) Save DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class UserActionLogSaveDTO {

    /**
     * 사용자 시퀀스
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "userSeq", value = "사용자 시퀀스", required = true)
    private Long userSeq;

    /**
     * URL
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "url", value = "URL", required = true)
    private String url;

    /**
     * 메소드 타입
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "methodTypeName", value = "메소드 타입", required = true)
    private String methodTypeName;

    /**
     * 호출 메소드 명
     *
     * @author [오지훈]
     */
    @Column(name = "METHOD_SIGNATURE")
    private String methodSignature;

    /**
     * Parameter
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "parameter", value = "Parameter", required = true)
    private String parameter;

}
