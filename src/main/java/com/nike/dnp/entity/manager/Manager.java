package com.nike.dnp.entity.manager;

import com.nike.dnp.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_MANAGER")
public class Manager extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MANAGER_SEQ")
    private Long managerSeq;

    @Column(name = "MANAGER_ID")
    private String managerId;

    @Column(name = "MANAGER_NAME")
    private String managerName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USE_IP")
    private String useIp;

    @Column(name = "LOGIN_DT")
    private LocalDateTime loginDt;

    @Column(name = "REGISTER_SEQ")
    private Long registerSeq;

    @Column(name = "UPDATER_SEQ")
    private Long updaterSeq;

    @OneToOne
    @JoinColumn(name = "AUTH_SEQ")
    private ManagerAuth managerAuth;

    @Builder
    public Manager(String managerId
            , String password
            , String managerName
            , ManagerAuth managerAuth
    ) {
        this.managerId = managerId;
        this.password = password;
        this.managerName = managerName;
        this.managerAuth = managerAuth;
    }

    public void managerNameUpdate(String managerName
            ,String password
            ,ManagerAuth managerAuth) {
        this.managerName = managerName;
        this.password = password;
        this.managerAuth = managerAuth;
    }

    public void loginDtUpdate() {
        this.loginDt = LocalDateTime.now();
    }
}
