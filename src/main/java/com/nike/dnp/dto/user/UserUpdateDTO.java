package com.nike.dnp.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * UserUpdateDTO
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:09:30
 * @Description User(유저) Update DTO 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class UserUpdateDTO {

    /**
     * 닉네임
     *
     * @author [오지훈]
     */
    @NotBlank(message = "user.nickname")
    @ApiModelProperty(name = "nickname", value = "닉네임", required = true, example = "NIKE학동점")
    private String nickname;

    /**
     * 권한 시퀀스
     *
     * @author [오지훈]
     */
    @NotNull(message = "user.authSeq")
    @ApiModelProperty(name = "authSeq", value = "권한 시퀀스", required = true, example = "1")
    private Long authSeq;

}
