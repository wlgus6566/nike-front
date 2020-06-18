package com.nike.dnp.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * UserUpdateDTO
 *
 * @author [오지훈]
 * @Description User(유저) Update DTO 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class UserSaveDTO {

    /**
     * 유저 ID
     * @author [오지훈]
     */
    @ApiModelProperty(name = "userId", value = "유저 ID", required = true)
    private String userId;

    /**
     * 닉네임
     * @author [오지훈]
     */
    @ApiModelProperty(name = "nickname", value = "닉네임", required = true)
    private String nickname;

    /**
     * 권한 시퀀스
     * @author [오지훈]
     */
    @ApiModelProperty(name = "authSeq", value = "권한 시퀀스", required = true)
    private Long authSeq;

}
