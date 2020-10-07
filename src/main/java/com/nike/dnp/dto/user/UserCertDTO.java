package com.nike.dnp.dto.user;

import com.nike.dnp.common.validation.ValidationGroups;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * UserCertDTO
 *
 * @author [오지훈]
 * @implNote User(유저) 패스워드 설정 DTO 작성
 * @since 2020. 6. 22. 오후 12:14:01
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
    @NotBlank(message = "userCert.certCode", groups = {ValidationGroups.Group1.class})
    @ApiModelProperty(value = "인증 코드", name = "certCode")
    private String certCode;

    /**
     * 기존 비밀번호
     *
     * @author [오지훈]
     */
    @NotNull(message = "userCert.password", groups = {ValidationGroups.Group2.class})
    @ApiModelProperty(value = "기존 비밀번호", name = "password")
    private char[] password;

    /**
     * 새로운 비밀번호
     *
     * @author [오지훈]
     */
    @NotNull(message = "userCert.newPassword", groups = {ValidationGroups.Group3.class})
    @ApiModelProperty(value = "새 비밀번호", name = "newPassword")
    private char[] newPassword;

    /**
     * 확인 비밀번호
     *
     * @author [오지훈]
     */
    @NotNull(message = "userCert.confirmPassword", groups = {ValidationGroups.Group4.class})
    @ApiModelProperty(value = "새 비밀번호(확인)", name = "confirmPassword")
    private char[] confirmPassword;

    /**
     * The interface Set password.
     *
     * @author [오지훈]
     * @implNote Login 비밀번호 설정 Validation Group
     * @since 2020. 8. 14. 오후 5:23:08
     */
    @GroupSequence({ValidationGroups.Group1.class, ValidationGroups.Group3.class, ValidationGroups.Group4.class})
    public interface SetPassword {}

    /**
     * The interface Change password.
     *
     * @author [오지훈]
     * @implNote Login 비밀번호 변경 Validation Group
     * @since 2020. 8. 14. 오후 5:23:08
     */
    @GroupSequence({ValidationGroups.Group1.class, ValidationGroups.Group2.class, ValidationGroups.Group3.class, ValidationGroups.Group4.class})
    public interface ChangePassword {}

    /**
     * The interface Check cert.
     *
     * @author [오지훈]
     * @implNote Login 인증코드 검증 Validation Group
     * @since 2020. 8. 14. 오후 5:23:08
     */
    @GroupSequence({ValidationGroups.Group1.class})
    public interface CheckCert {}

    /**
     * The interface My change password.
     *
     * @author [오지훈]
     * @implNote Mypage 비밀번호 변경 Validation Group
     * @since 2020. 8. 14. 오후 5:23:08
     */
    @GroupSequence({ValidationGroups.Group2.class, ValidationGroups.Group3.class, ValidationGroups.Group4.class})
    public interface MyChangePassword {}

}
