package com.nike.dnp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * BaseTimeEntity Entity
 *
 * @author [오지훈]
 * @Description BaseTimeEntity Entity 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

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

    /**
     * 최초 작성일
     * @author [오지훈]
     */
    @Column(name = "REGISTRATION_DT")
    @CreationTimestamp
    @ApiModelProperty(name = "registrationDt", value = "최초 작성일", hidden = true)
    private LocalDateTime registrationDt;

    /**
     * 최종 수정일
     * @author [오지훈]
     */
    @Column(name = "UPDATE_DT")
    @UpdateTimestamp
    @ApiModelProperty(name = "updateDt", value = "최종 수정일", hidden = true)
    private LocalDateTime updateDt;

}
