package com.nike.dnp.entity.manager;

import com.nike.dnp.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TB_MANAGER_AUTH")
public class ManagerAuth extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTH_SEQ")
    private Long authSeq;

    @Column(name = "AUTH_NAME")
    private String authName;

    @Column(name = "ROLE_TYPE")
    private String roleType;

}
