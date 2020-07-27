package com.nike.dnp.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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
public class PasswordHistory implements Serializable {

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * 유저 맵핑
     *
     * @author [오지훈]
     */
    @ManyToOne
    @JoinColumn(name = "USER_SEQ", insertable = false, updatable = false)
    @JsonBackReference
    @ApiModelProperty(name = "user", value = "유저")
    private User user;

    /**
     * 최초 작성자
     *
     * @author [오지훈]
     */
    @Column(name = "REGISTER_SEQ")
    @ApiModelProperty(name = "registerSeq", value = "최초 작성자 시퀀스", hidden = true, required = true)
    private Long registerSeq;

    /**
     * 최종 수정자
     *
     * @author [오지훈]
     */
    @Column(name = "UPDATER_SEQ")
    @ApiModelProperty(name = "updaterSeq", value = "최종 수정자 시퀀스", hidden = true, required = true)
    private Long updaterSeq;

    /**
     * 최초 작성일
     *
     * @author [오지훈]
     */
    @Column(name = "REGISTRATION_DT")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    @CreationTimestamp
    @ApiModelProperty(name = "registrationDt", value = "최초 작성일", hidden = true)
    private LocalDateTime registrationDt;

    /**
     * 최종 수정일
     *
     * @author [오지훈]
     */
    @Column(name = "UPDATE_DT")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    @UpdateTimestamp
    @ApiModelProperty(name = "updateDt", value = "최종 수정일", hidden = true)
    private LocalDateTime updateDt;

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
            final Long userSeq
            , final String password
    ) {
        super();
        this.userSeq = userSeq;
        this.password = password;
        this.registerSeq = userSeq;
        this.updaterSeq = userSeq;
        this.registrationDt = LocalDateTime.now();
        this.updateDt = LocalDateTime.now();
    }
}
