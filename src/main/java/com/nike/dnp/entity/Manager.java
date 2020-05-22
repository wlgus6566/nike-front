package com.nike.dnp.entity;

import com.nike.dnp.util.DateUtil;
import lombok.*;

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
    private long authSeq;
    private String useIp;
    private String loginDt = DateUtil.getToday("yyyy.MM.dd HH:mm:ss");

    @Builder
    public Manager(String managerId, String password) {
        this.managerId = managerId;
        this.password = password;
    }

}
