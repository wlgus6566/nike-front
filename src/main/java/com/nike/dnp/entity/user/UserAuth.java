package com.nike.dnp.entity.user;

import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.entity.auth.Auth;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * UserAuth Entity
 *
 * @author [오지훈]
 * @Description UserAuth(유저 권한) Entity 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_USER_AUTH")
public class UserAuth extends BaseTimeEntity implements Serializable {

    /**
     * 유저 권한 시퀀스
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_AUTH_SEQ")
    @ApiModelProperty(name = "userAuthSeq", value = "유저 권한 시퀀스", hidden = true)
    private Long userAuthSeq;

    /**
     * 유저 시퀀스
     * @author [오지훈]
     */
    @Column(name = "USER_SEQ")
    @ApiModelProperty(name = "userSeq", value = "유저 시퀀스", hidden = true)
    private Long userSeq;

    /**
     * 권한 시퀀스
     * @author [오지훈]
     */
    @Column(name = "AUTH_SEQ")
    @ApiModelProperty(name = "authSeq", value = "권한 시퀀스", hidden = true)
    private Long authSeq;

    /**
     * 유저
     * @author [오지훈]
     */
    /*@ManyToOne
    @JoinColumn(name = "USER_SEQ", insertable = false, updatable = false)
    private User user;*/

    /**
     * 권한
     * @author [오지훈]
     */
    @ManyToOne
    @JoinColumn(name = "AUTH_SEQ", insertable = false, updatable = false)
    private Auth auth;

    /**
     * 등록
     *
     * @param userSeq     the user seq
     * @param authSeq     the auth seq
     * @param registerSeq the register seq
     */
    @Builder
    public UserAuth(
            Long userSeq
            , Long authSeq
            , Long registerSeq
    ) {
        this.userSeq = userSeq;
        this.authSeq = authSeq;
        this.setRegisterSeq(registerSeq);
        this.setUpdaterSeq(registerSeq);
    }
}
