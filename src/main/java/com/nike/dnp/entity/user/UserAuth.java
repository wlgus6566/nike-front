package com.nike.dnp.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.user.UserUpdateDTO;
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
 * @CreatedOn 2020. 6. 24. 오후 6:11:14
 * @Description UserAuth(유저 권한) Entity 작성
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
    @ManyToOne
    @JoinColumn(name = "USER_SEQ", insertable = false, updatable = false)
    @JsonManagedReference
    private User user;

    /**
     * 권한 맵핑
     *
     * @author [오지훈]
     */
    @ManyToOne
    @JoinColumn(name = "AUTH_SEQ", insertable = false, updatable = false)
    private Auth auth;

    public void authUpdate(
            final UserUpdateDTO userUpdateDTO
            , final AuthUserDTO authUserDTO
    ) {
        this.authSeq = userUpdateDTO.getAuthSeq();
        this.setUpdaterSeq(authUserDTO.getUserSeq());
    }

}
