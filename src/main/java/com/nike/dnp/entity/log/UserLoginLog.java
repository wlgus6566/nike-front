package com.nike.dnp.entity.log;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2020. 6. 24. 오후 6:10:27
 * @implNote UserLoginLog(유저_로그인_로그) Entity 작성
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
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOGIN_LOG_SEQ")
    @ApiModelProperty(name = "loginLogSeq", value = "로그 시퀀스", hidden = true)
    private Long loginLogSeq;

    /**
     * 사용자 시퀀스
     *
     * @author [오지훈]
     */
    @Column(name = "USER_SEQ")
    @ApiModelProperty(name = "userSeq", value = "사용자 시퀀스", required = true)
    private Long userSeq;

    /**
     * 로그인 일시
     *
     * @author [오지훈]
     */
    @Column(name = "LOGIN_DT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(name = "loginDt", value = "로그인 일시", hidden = true, required = true)
    @CreationTimestamp
    private LocalDateTime loginDt;

    /**
     * IP
     *
     * @author [오지훈]
     */
    @Column(name = "LOGIN_IP")
    @ApiModelProperty(name = "loginIp", value = "IP", required = true)
    private String loginIp;

    /**
     * The DEVICE
     *
     * @author [이소정]
     */
    @Column(name = "DEVICE")
    @ApiModelProperty(name = "device", value = "디바이스 정보", example = "PC", hidden = true)
    private String device;

}
