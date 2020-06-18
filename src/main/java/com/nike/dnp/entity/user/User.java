package com.nike.dnp.entity.user;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * User Entity
 *
 * @author [오지훈]
 * @Description User(유저) Entity 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_USER")
@DynamicUpdate
public class User extends BaseTimeEntity implements Serializable {

    /**
     * 유저 시퀀스
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_SEQ")
    @ApiModelProperty(name = "userSeq", value = "유저 시퀀스", hidden = true)
    private Long userSeq;

    /**
     * 유저 ID
     * @author [오지훈]
     */
    @Column(name = "USER_ID")
    @ApiModelProperty(name = "userId", value = "유저 ID", required = true)
    private String userId;

    /**
     * 비밀번호
     * @author [오지훈]
     */
    @Column(name = "PASSWORD")
    @ApiModelProperty(name = "password", value = "비밀번호", hidden = true)
    private String password;

    /**
     * 닉네임
     * @author [오지훈]
     */
    @Column(name = "NICKNAME")
    @ApiModelProperty(name = "nickname", value = "닉네임", required = true)
    private String nickname;

    /**
     * 유저 IP
     * @author [오지훈]
     */
    @Column(name = "LOGIN_IP")
    @ApiModelProperty(name = "loginIp", value = "유저 IP", hidden = true)
    private String loginIp;

    /**
     * 로그인 일시
     * @author [오지훈]
     */
    @Column(name = "LOGIN_DT")
    @ApiModelProperty(name = "loginDt", value = "로그인 일시", hidden = true)
    private LocalDateTime loginDt;

    /**
     * 유저 상태 코드
     * @author [오지훈]
     */
    @Column(name = "USER_STATUS_CODE")
    @ApiModelProperty(name = "userStatusCode", value = "유저 상태 코드", hidden = true)
    private String userStatusCode;

    /**
     * 약관 동의 여부
     * @author [오지훈]
     */
    @Column(name = "TERMS_AGREE_YN")
    @ApiModelProperty(name = "termsAgreeYn", value = "약관 동의 여부", required = true)
    private String termsAgreeYn;

    /**
     * 브라우저 헤더
     * @author [오지훈]
     */
    @Column(name = "LOGIN_BROWSER_HEADER")
    @ApiModelProperty(name = "loginBrowserHeader", value = "브라우저 헤더", hidden = true)
    private String loginBrowserHeader;

    /**
     * 비밀번호 최종 수정 일시
     * @author [오지훈]
     */
    @Column(name = "PASSWORD_LAST_UPDATE_DT")
    @ApiModelProperty(name = "passwordLastUpdateDt", value = "비밀번호 최종 수정 일시", hidden = true)
    private LocalDateTime passwordLastUpdateDt;

    /**
     * 인증 코드
     * @author [오지훈]
     */
    @Column(name = "CERT_CODE")
    @ApiModelProperty(name = "certCode", value = "인증 코드", hidden = true)
    private String certCode;

    /**
     * 쿼리 실행 전 기본값 설정
     */
    @PrePersist
    public void prePersist() {
        this.termsAgreeYn = this.termsAgreeYn == null ? "N" : this.termsAgreeYn;
    }

    /**
     * 등록
     *
     * @param userId      the user id
     * @param nickname    the nickname
     * @param registerSeq the register seq
     */
    @Builder
    public User(
            String userId
            , String nickname
            , Long registerSeq
    ) {
        this.userId = userId;
        this.nickname = nickname;
        this.setRegisterSeq(registerSeq);
        this.setUpdaterSeq(registerSeq);
    }

    /**
     * 닉네임/권한 변경
     *
     * @param nickname the nickname
     * @param userSeq  the user seq
     */
    public void update(
            String nickname
            , Long userSeq
    ) {
        this.nickname = nickname;
        setUpdaterSeq(userSeq);
    }

    /**
     * 비밀번호 변경
     *
     * @param password the password
     * @param userSeq  the user seq
     */
    public void updatePassword(
            String password
            , Long userSeq
    ) {
        this.password = password;
        this.passwordLastUpdateDt = LocalDateTime.now();
        setUpdaterSeq(userSeq);
    }

    /**
     * 상태값 변경
     *
     * @param userStatusCode the user status code
     * @param userSeq        the user seq
     */
    public void updateStatus(
            String userStatusCode
            , Long userSeq
    ) {
        this.userStatusCode = userStatusCode;
        setUpdaterSeq(userSeq);
    }

    /**
     * 최종 로그인 일시 변경
     */
    public void updateLoginDt() {
        this.loginDt = LocalDateTime.now();
    }

}
