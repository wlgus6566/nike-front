package com.nike.dnp.dto.user;

import lombok.*;


/**
 * UserPasswordDTO
 *
 * @author [오지훈]
 * @implNote User(유저) 패스워드 설정 DTO 작성
 * @since 2020. 6. 22. 오후 12:14:01
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserPasswordDTO {

    /**
     * The User seq
     *
     * @author [오지훈]
     */
    private Long userSeq;

    /**
     * The User id
     *
     * @author [오지훈]
     */
    private String userId;

    /**
     * The User password
     *
     * @author [오지훈]
     */
    private String userPassword;

    /**
     * The Password
     *
     * @author [오지훈]
     */
    private String password;

    /**
     * The New password
     *
     * @author [오지훈]
     */
    private String newPassword;

    /**
     * The Confirm password
     *
     * @author [오지훈]
     */
    private String confirmPassword;

    /**
     * Instantiates a new User password dto.
     *
     * @param userSeq         the user seq
     * @param userId          the user id
     * @param userPassword    the user password
     * @param password        the password
     * @param newPassword     the new password
     * @param confirmPassword the confirm password
     * @author [오지훈]
     * @implNote 생성자 주입 *
     * @since 2020. 7. 30. 오후 5:05:28
     */
    @Builder
    public UserPasswordDTO (
            final Long userSeq
            , final String userId
            , final String userPassword
            , final String password
            , final String newPassword
            , final String confirmPassword
    ) {
        this.userSeq = userSeq;
        this.userId = userId;
        this.userPassword = userPassword;
        this.password = password;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

}
