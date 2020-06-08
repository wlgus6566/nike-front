package com.nike.dnp.entity.manage;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * ManagerAuth Entity
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description ManagerAuth Entity 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Getter
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

    @Column(name = "USE_YN")
    private String useYn;

    @Column(name = "REGISTER_SEQ")
    @ApiModelProperty(hidden = true)
    private Long registerSeq;

    @Column(name = "UPDATER_SEQ")
    @ApiModelProperty(hidden = true)
    private Long updaterSeq;

    @Builder
    public ManagerAuth(
            final String authName
            , final String roleType
            , final String useYn
            , final Long registerSeq
    ) {
        super();
        this.authName = authName;
        this.roleType = roleType;
        this.useYn = useYn;
        this.registerSeq = registerSeq;
        this.updaterSeq = registerSeq;
    }

    public void update(
            final String authName
            , final String roleType
            , final String useYn
            , final Long updaterSeq
    ) {
        this.authName = authName;
        this.roleType = roleType;
        this.useYn = useYn;
        this.updaterSeq = updaterSeq;
    }
}
