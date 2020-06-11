package com.nike.dnp.entity.log;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * UserLoginLog Entity
 *
 * @author [오지훈]
 * @Description UserLoginLog(유저_로그인_로그) Entity 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_USER_LOGIN_LOG")
public class UserLoginLog extends BaseTimeEntity {

    /**
     * 로그 시퀀스
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOGIN_LOG_SEQ")
    @ApiModelProperty(name = "loginLogSeq", value = "로그 시퀀스", hidden = true)
    private Long loginLogSeq;

    /**
     * 사용자 시퀀스
     * @author [오지훈]
     */
    @Column(name = "USER_SEQ")
    @ApiModelProperty(name = "userSeq", value = "사용자 시퀀스", required = true)
    private Long userSeq;

    /**
     * 로그인 일시
     * @author [오지훈]
     */
    @Column(name = "LOGIN_DT")
    @ApiModelProperty(name = "loginDt", value = "로그인 일시", hidden = true, required = true)
    @CreationTimestamp
    private LocalDateTime loginDt;

    /**
     * IP
     * @author [오지훈]
     */
    @Column(name = "LOGIN_IP")
    @ApiModelProperty(name = "loginIp", value = "IP", required = true)
    private String loginIp;

    /**
     * 최초 작성자
     * @author [오지훈]
     */
    @Column(name = "REGISTER_SEQ")
    @ApiModelProperty(name = "registerSeq", value = "최초 작성자 시퀀스", hidden = true, required = true)
    private Long registerSeq;

    /**
     * 최종 수정자
     * @author [오지훈]
     */
    @Column(name = "UPDATER_SEQ")
    @ApiModelProperty(name = "updaterSeq", value = "최종 수정자 시퀀스", hidden = true, required = true)
    private Long updaterSeq;

}
