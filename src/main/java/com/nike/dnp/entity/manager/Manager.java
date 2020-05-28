package com.nike.dnp.entity.manager;

import com.nike.dnp.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Manager Entity
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description Manager Entity 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    public Manager(
            String managerId
            , String password
            , String managerName
            , ManagerAuth managerAuth
            , Long registerSeq
    ) {
        this.managerId = managerId;
        this.password = password;
        this.managerName = managerName;
        this.managerAuth = managerAuth;
        this.registerSeq = registerSeq;
        this.updaterSeq = registerSeq;
    }

    public void update(
            String managerName
            , String password
            , ManagerAuth managerAuth
            , Long updaterSeq
    ) {
        this.managerName = managerName;
        this.password = password;
        this.managerAuth = managerAuth;
        this.updaterSeq = updaterSeq;
    }

    public void loginDtUpdate() {
        this.loginDt = LocalDateTime.now();
    }
}
