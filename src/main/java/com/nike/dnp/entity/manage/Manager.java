package com.nike.dnp.entity.manage;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TB_MANAGER")
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Manager extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MANAGER_SEQ")
    @ApiModelProperty(value = "사용자시퀀스", hidden = true)
    private Long managerSeq;

    @Column(name = "MANAGER_ID")
    @ApiModelProperty(value = "사용자ID")
    private String managerId;

    @Column(name = "MANAGER_NAME")
    @ApiModelProperty(value = "사용자명")
    private String managerName;

    @Column(name = "PASSWORD")
    @ApiModelProperty(value = "비밀번호")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "USE_IP")
    @ApiModelProperty(value = "접속IP", hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String useIp;

    @Column(name = "LOGIN_DT")
    @ApiModelProperty(value = "최종로그인일자", hidden = true)
    private LocalDateTime loginDt;

    @Column(name = "REGISTER_SEQ")
    @ApiModelProperty(value = "최초작성자", hidden = true)
    private Long registerSeq;

    @Column(name = "UPDATER_SEQ")
    @ApiModelProperty(value = "최종수정자", hidden = true)
    private Long updaterSeq;

    @OneToOne
    @JoinColumn(name = "AUTH_SEQ")
    @ApiModelProperty(value = "권한정보", hidden = true)
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
