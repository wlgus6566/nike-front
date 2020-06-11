package com.nike.dnp.dto.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * UserLoginLogSaveDTO
 *
 * @author [오지훈]
 * @Description UserLoginLog(유저_로그인_로그) Save DTO 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class UserLoginLogSaveDTO {

    /**
     * 사용자 시퀀스
     * @author [오지훈]
     */
    @ApiModelProperty(name = "userSeq", value = "사용자 시퀀스", required = true)
    private Long userSeq;

    /**
     * IP
     * @author [오지훈]
     */
    @ApiModelProperty(name = "loginIp", value = "IP", required = true)
    private String loginIp;

    /**
     * Method to String
     * @return String
     */
    public String toString() {
        return "ErrorLogSaveDTO{"
                + "userSeq=" + userSeq
                + ", loginIp=" + loginIp + '}';
    }

}
