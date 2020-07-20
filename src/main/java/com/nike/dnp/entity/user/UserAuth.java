package com.nike.dnp.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nike.dnp.dto.user.UserUpdateDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.entity.auth.Auth;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

/**
 * UserAuth Entity
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:11:14
 * @Description UserAuth(유저 권한) Entity 작성
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_USER_AUTH")
public class UserAuth extends BaseTimeEntity implements Serializable {

    /**
     * 유저 권한 시퀀스
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_AUTH_SEQ")
    @ApiModelProperty(name = "userAuthSeq", value = "유저 권한 시퀀스", hidden = true)
    private Long userAuthSeq;

    /**
     * 유저 시퀀스
     *
     * @author [오지훈]
     */
    @Column(name = "USER_SEQ")
    @ApiModelProperty(name = "userSeq", value = "유저 시퀀스", hidden = true)
    private Long userSeq;

    /**
     * 권한 시퀀스
     *
     * @author [오지훈]
     */
    @Column(name = "AUTH_SEQ")
    @ApiModelProperty(name = "authSeq", value = "권한 시퀀스", hidden = true)
    private Long authSeq;

    /**
     * 유저 맵핑
     *
     * @author [오지훈]
     */
    @OneToOne
    @JoinColumn(name = "USER_SEQ", insertable = false, updatable = false)
    @JsonBackReference
    @ApiModelProperty(name = "user", value = "유저")
    private User user;

    /**
     * 권한 맵핑
     *
     * @author [오지훈]
     */
    @ManyToOne
    @JoinColumn(name = "AUTH_SEQ", insertable = false, updatable = false)
    //@JsonBackReference 단방향 시 사용 안함
    @ApiModelProperty(name = "auth", value = "권한")
    private Auth auth;

    /**
     * Save user auth.
     *
     * @param user the user
     * @param auth the auth
     * @return the user auth
     * @author [오지훈]
     * @CreatedOn 2020. 6. 30. 오후 4:38:13
     * @Description 유저권한 저장
     */
    public UserAuth save(final User user, final Auth auth) {
        log.info("UserAuth.save");
        final UserAuth userAuth = new UserAuth();
        userAuth.setUser(user);
        userAuth.setAuth(auth);
        return userAuth;
    }

    /**
     * Auth update.
     *
     * @param userUpdateDTO the user update dto
     * @author [오지훈]
     * @CreatedOn 2020. 6. 30. 오후 4:38:14
     * @Description 유저권한 업데이트
     */
    public void update(final UserUpdateDTO userUpdateDTO) {
        log.info("UserAuth.authUpdate");
        this.authSeq = userUpdateDTO.getAuthSeq();
    }

}
