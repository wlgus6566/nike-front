package com.nike.dnp.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * UserUpdateStatusDTO
 *
 * @author [오지훈]
 * @Description User(유저) Update Status DTO 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class UserUpdateStatusDTO {

    /**
     * 유저 상태 코드
     * @author [오지훈]
     */
    @ApiModelProperty(name = "userStatusCode", value = "유저 상태 코드", required = true)
    private String userStatusCode;

}
