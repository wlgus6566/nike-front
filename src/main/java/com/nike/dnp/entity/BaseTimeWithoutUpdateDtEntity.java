package com.nike.dnp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * BaseTimeEntity Entity
 *
 * @author [오지훈]
 * @since 2020. 6. 19. 오후 4:52:28
 * @implNote BaseTimeEntity Entity 작성
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeWithoutUpdateDtEntity {

    /**
     * 최초 작성자
     *
     * @author [오지훈]
     */
    @CreatedBy
    @Column(name = "REGISTER_SEQ")
    @ApiModelProperty(name = "registerSeq", value = "최초 작성자 시퀀스", hidden = true, required = true)
    private Long registerSeq;

    /**
     * 최종 수정자
     *
     * @author [오지훈]
     */
    @LastModifiedBy
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
}
