package com.nike.dnp.entity.example;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
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
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_MANAGER")
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Manager extends BaseTimeEntity {

    /**
     * 사용자시퀀스
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MANAGER_SEQ")
    @ApiModelProperty(value = "사용자시퀀스", hidden = true, name = "managerSeq")
    private Long managerSeq;

    /**
     * 사용자ID
     * @author [오지훈]
     */
    @Column(name = "MANAGER_ID")
    @ApiModelProperty(value = "사용자ID", name = "managerId")
    private String managerId;

    /**
     * 사용자명
     * @author [오지훈]
     */
    @Column(name = "MANAGER_NAME")
    @ApiModelProperty(value = "사용자명", name = "managerName")
    private String managerName;

    /**
     * 비밀번호
     * @author [오지훈]
     */
    @Column(name = "PASSWORD")
    @ApiModelProperty(value = "비밀번호", name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * 접속IP
     * @author [오지훈]
     */
    @Column(name = "USE_IP")
    @ApiModelProperty(value = "접속IP", hidden = true, name = "useIp")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String useIp;

    /**
     * 최종로그인일자
     * @author [오지훈]
     */
    @Column(name = "LOGIN_DT")
    @ApiModelProperty(value = "최종로그인일자", hidden = true, name = "loginDt")
    private LocalDateTime loginDt;

    /**
     * 최초작성자
     * @author [오지훈]
     */
    @Column(name = "REGISTER_SEQ")
    @ApiModelProperty(value = "최초작성자", hidden = true, name = "registerSeq")
    private Long registerSeq;

    /**
     * 최종수정자
     * @author [오지훈]
     */
    @Column(name = "UPDATER_SEQ")
    @ApiModelProperty(value = "최종수정자", hidden = true, name = "updaterSeq")
    private Long updaterSeq;

    /**
     * @author [오지훈]
     */
    @ManyToOne
    @JoinColumn(name = "AUTH_SEQ")
    @ApiModelProperty(value = "권한정보", hidden = true, name = "managerAuth")
    private ManagerAuth managerAuth;

    /**
     * Instantiates a new Manager.
     *
     * @param managerId   the manager id
     * @param password    the password
     * @param managerName the manager name
     * @param managerAuth the manager auth
     * @param registerSeq the register seq
     */
    @Builder
    public Manager(
            final String managerId
            , final String password
            , final String managerName
            , final ManagerAuth managerAuth
            , final Long registerSeq
    ) {
        super();
        this.managerId = managerId;
        this.password = password;
        this.managerName = managerName;
        this.managerAuth = managerAuth;
        this.registerSeq = registerSeq;
        this.updaterSeq = registerSeq;
    }

    /**
     * Update.
     *
     * @param managerName the manager name
     * @param password    the password
     * @param managerAuth the manager auth
     * @param updaterSeq  the updater seq
     */
    public void update(
            final String managerName
            , final String password
            , final ManagerAuth managerAuth
            , final Long updaterSeq
    ) {
        this.managerName = managerName;
        this.password = password;
        this.managerAuth = managerAuth;
        this.updaterSeq = updaterSeq;
    }

    /**
     * Login dt update.
     */
    public void loginDtUpdate() {
        this.loginDt = LocalDateTime.now();
    }
}
