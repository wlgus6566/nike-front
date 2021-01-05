package com.nike.dnp.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class User dto.
 *
 * @author [이소정]
 * @since 2021. 1. 5. 오후 4:42:44
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    /**
     * 유저 시퀀스
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "userSeq", value = "유저 시퀀스", example = "4")
    private Long userSeq;

    /**
     * 유저 ID
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "userId", value = "유저 ID", example = "test@nike.co.kr")
    private String userId;

    /**
     * 닉네임
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "nickname", value = "닉네임", example = "테스트계정")
    private String nickname;
}
