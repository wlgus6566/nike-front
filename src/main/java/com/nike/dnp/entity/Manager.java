package com.nike.dnp.entity;

import com.nike.dnp.util.DateUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_MANAGER")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long managerSeq;

    private String managerId;
    private String password;

    //private long authSeq;
    @OneToOne
    @JoinColumn(name = "authSeq")
    private ManagerAuth managerAuth;

    private String useIp;
    private String loginDt = DateUtil.getToday("yyyy.MM.dd HH:mm:ss");

}
