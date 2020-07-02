package com.nike.dnp.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.user.UserSaveDTO;
import com.nike.dnp.dto.user.UserUpdateDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User Entity
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 23. 오후 5:26:57
 * @Description User(유저) Entity 작성
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_USER")
@DynamicUpdate
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends BaseTimeEntity {

    /**
     * 유저 시퀀스
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_SEQ")
    @ApiModelProperty(name = "userSeq", value = "유저 시퀀스", hidden = true)
    private Long userSeq;

    /**
     * 유저 ID
     *
     * @author [오지훈]
     */
    @Column(name = "USER_ID")
    @ApiModelProperty(name = "userId", value = "유저 ID", required = true)
    private String userId;

    /**
     * 비밀번호
     *
     * @author [오지훈]
     */
    @Column(name = "PASSWORD")
    @ApiModelProperty(name = "password", value = "비밀번호", hidden = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * 닉네임
     *
     * @author [오지훈]
     */
    @Column(name = "NICKNAME")
    @ApiModelProperty(name = "nickname", value = "닉네임", required = true)
    private String nickname;

    /**
     * 유저 IP
     *
     * @author [오지훈]
     */
    @Column(name = "LOGIN_IP")
    @ApiModelProperty(name = "loginIp", value = "유저 IP", hidden = true)
    private String loginIp;

    /**
     * 로그인 일시
     *
     * @author [오지훈]
     */
    @Column(name = "LOGIN_DT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(name = "loginDt", value = "로그인 일시", hidden = true)
    private LocalDateTime loginDt;

    /**
     * 유저 상태 코드
     *
     * @author [오지훈]
     */
    @Column(name = "USER_STATUS_CODE")
    @ApiModelProperty(name = "userStatusCode", value = "유저 상태 코드", hidden = true)
    private String userStatusCode;

    /**
     * 약관 동의 여부
     *
     * @author [오지훈]
     */
    @Column(name = "TERMS_AGREE_YN")
    @ApiModelProperty(name = "termsAgreeYn", value = "약관 동의 여부", required = true)
    private String termsAgreeYn;

    /**
     * 브라우저 헤더
     *
     * @author [오지훈]
     */
    @Column(name = "LOGIN_BROWSER_HEADER")
    @ApiModelProperty(name = "loginBrowserHeader", value = "브라우저 헤더", hidden = true)
    private String loginBrowserHeader;

    /**
     * 비밀번호 최종 수정 일시
     *
     * @author [오지훈]
     */
    @Column(name = "PASSWORD_LAST_UPDATE_DT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(name = "passwordLastUpdateDt", value = "비밀번호 최종 수정 일시", hidden = true)
    private LocalDateTime passwordLastUpdateDt;

    /**
     * 유저권한 맵핑
     *
     * @author [오지훈]
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<UserAuth> userAuth = new ArrayList<>();

    /**
     * 패스워드 기록 맵핑
     *
     * @author [오지훈]
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<PasswordHistory> histories = new ArrayList<>();

    /**
     * 쿼리 실행 전 기본값 설정
     *
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 5:26:57
     * @Description
     */
    @PrePersist
    public void prePersist() {
        this.termsAgreeYn = this.termsAgreeYn == null ? "N" : this.termsAgreeYn;
        this.userStatusCode = ServiceEnumCode.UserStatusEnumCode.NORMAL.toString();
    }

    /**
     * Save user.
     *
     * @param userSaveDTO the user save dto
     * @return the user
     * @author [오지훈]
     * @CreatedOn 2020. 6. 25. 오후 5:59:50
     * @Description
     */
    public User save(final UserSaveDTO userSaveDTO) {
        log.info("User.save");
        User saveUser = new User();
        saveUser.setUserId(userSaveDTO.getUserId());
        saveUser.setNickname(userSaveDTO.getNickname());
        return saveUser;
    }

    /**
     * Update.
     *
     * @param userUpdateDTO the user update dto
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 5:26:57
     * @Description 닉네임 /권한 변경
     */
    public void update(final UserUpdateDTO userUpdateDTO) {
        log.info("User.update");
        this.nickname = userUpdateDTO.getNickname();
    }

    /**
     * Update password.
     *
     * @param password the password
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 5:26:57
     * @Description 비밀번호 변경
     */
    public void updatePassword(String password) {
        log.info("User.updatePassword");
        this.password = password;
        this.passwordLastUpdateDt = LocalDateTime.now();
    }

    /**
     * Update status.
     *
     * @param userStatusCode the user status code
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 5:26:57
     * @Description 상태값 변경
     */
    public void updateStatus(String userStatusCode) {
        log.info("User.updateStatus");
        this.userStatusCode = userStatusCode;
    }

    /**
     * Delete user.
     *
     * @param userSeq the user seq
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 5:41:15
     * @Description 삭제
     */
    public void delete(Long userSeq) {
        log.info("User.delete");
        this.userId = String.valueOf(userSeq);
        this.password = String.valueOf(userSeq);
        this.nickname = String.valueOf(userSeq);
        this.userStatusCode = ServiceEnumCode.UserStatusEnumCode.OUT.toString();
    }

    /**
     * Update login dt.
     *
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 5:26:57
     * @Description 최종 로그인 일시 변경
     */
    public void updateLoginDt() {
        log.info("User.updateLoginDt");
        this.loginDt = LocalDateTime.now();
    }

}
