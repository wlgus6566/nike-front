package com.nike.dnp.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;


/**
 * UserCertDTO
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 12:14:01
 * @Description User(유저) 패스워드 설정 DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class UserCertDTO {

    /**
     * 인증 코드
     *
     * @author [오지훈]
     */
    @ApiModelProperty(value = "인증 코드", name = "certCode", required = true
            , example = "y1v0LCq93KX05pR%2FWw3zF65hK%2FCOqYTZDdIXzM0BsC97m%2Fg1QcY1sCZAEvuTFgmcVg3a8J6xDFalUNjUfmmtu5sWZuI%3D")
    private String certCode;

    /**
     * 기존 비밀번호
     *
     * @author [오지훈]
     */
    @ApiModelProperty(value = "기존 비밀번호", name = "password")
    private String password;

    /**
     * 새로운 비밀번호
     *
     * @author [오지훈]
     */
    @ApiModelProperty(value = "새로운 비밀번호", name = "newPassword", required = true, example = "Emotion1!@")
    private String newPassword;

    /**
     * 확인 비밀번호
     *
     * @author [오지훈]
     */
    @ApiModelProperty(value = "확인 비밀번호", name = "confirmPassword", required = true, example = "Emotion1!@")
    private String confirmPassword;

}
