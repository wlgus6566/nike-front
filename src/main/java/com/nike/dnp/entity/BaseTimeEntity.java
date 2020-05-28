package com.nike.dnp.entity;

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
 * @since 2020.05.22
 * @author [오지훈]
 * @Description BaseTimeEntity Entity 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @Column(name = "REGISTRATION_DT")
    //@CreatedDate
    @CreationTimestamp
    private LocalDateTime registrationDt;

    @Column(name = "UPDATE_DT")
    //@LastModifiedDate
    @UpdateTimestamp
    private LocalDateTime updateDt;

}
