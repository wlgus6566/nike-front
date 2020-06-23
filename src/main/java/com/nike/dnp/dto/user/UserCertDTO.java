package com.nike.dnp.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;


/**
 * UserCertDTO
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 12:14:01
 * @Description User(유저) 인증코드로 검색 DTO 작성
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
    @ApiModelProperty(value = "인증 코드", name = "certCode", required = true)
    private String certCode;

    /**
     * 새로운 비밀번호
     *
     * @author [오지훈]
     */
    @ApiModelProperty(value = "새로운 비밀번호", name = "newPassword", required = true)
    private String newPassword;

    /**
     * 확인 비밀번호
     *
     * @author [오지훈]
     */
    @ApiModelProperty(value = "확인 비밀번호", name = "confirmPassword", required = true)
    private String confirmPassword;

}
