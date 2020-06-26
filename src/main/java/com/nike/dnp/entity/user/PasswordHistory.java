package com.nike.dnp.entity.user;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * PasswordHistory Entity
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:11:14
 * @Description PasswordHistory(비밀번호 이력) Entity 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_PASSWORD_HISTORY")
public class PasswordHistory extends BaseTimeEntity implements Serializable {

    /**
     * 비밀번호 이력 시퀀스
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PASSWORD_HISTORY_SEQ")
    @ApiModelProperty(name = "passwordHistorySeq", value = "비밀번호 이력 시퀀스", hidden = true)
    private Long passwordHistorySeq;

    /**
     * 유저 시퀀스
     *
     * @author [오지훈]
     */
    @Column(name = "USER_SEQ")
    @ApiModelProperty(name = "userSeq", value = "유저 시퀀스", hidden = true)
    private Long userSeq;

    /**
     * 비밀번호
     *
     * @author [오지훈]
     */
    @Column(name = "PASSWORD")
    @ApiModelProperty(name = "password", value = "비밀번호", hidden = true)
    private String password;

    /**
     * Instantiates a new Password history.
     *
     * @param userSeq  the user seq
     * @param password the password
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 6:11:14
     * @Description 등록
     */
    @Builder
    public PasswordHistory(
            Long userSeq
            , String password
    ) {
        this.userSeq = userSeq;
        this.password = password;
        this.setRegisterSeq(userSeq);
        this.setUpdaterSeq(userSeq);
    }
}
