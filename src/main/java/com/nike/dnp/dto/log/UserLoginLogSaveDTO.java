package com.nike.dnp.dto.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * UserLoginLogSaveDTO
 *
 * @author [오지훈]
 * @implNote UserLoginLog(유저_로그인_로그) Save DTO 작성
 * @since 2020. 6. 24. 오후 6:09:11
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class UserLoginLogSaveDTO {

    /**
     * 사용자 시퀀스
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "userSeq", value = "사용자 시퀀스", required = true)
    private Long userSeq;

    /**
     * IP
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "loginIp", value = "IP", required = true)
    private String loginIp;

    /**
     * The Device
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "device", value = "디바이스 정보", example = "PC", hidden = true)
    private String device;

}
