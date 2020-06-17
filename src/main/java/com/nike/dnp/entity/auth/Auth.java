package com.nike.dnp.entity.auth;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Auth Entity
 *
 * @author [오지훈]
 * @Description Auth(권한) Entity 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_AUTH")
public class Auth extends BaseTimeEntity implements Serializable {

    /**
     * 권한 시퀀스
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTH_SEQ")
    @ApiModelProperty(name = "authSeq", value = "권한 시퀀스", hidden = true)
    private Long authSeq;

    /**
     * 상위 권한 시퀀스
     * @author [오지훈]
     */
    @Column(name = "UPPER_AUTH_SEQ")
    @ApiModelProperty(name = "upperAuthSeq", value = "상위 권한 시퀀스", hidden = true)
    private Long upperAuthSeq;

    /**
     * 권한명
     * @author [오지훈]
     */
    @Column(name = "AUTH_NAME")
    @ApiModelProperty(name = "authName", value = "권한명")
    private String authName;

    /**
     * 역할 타입
     * @author [오지훈]
     */
    @Column(name = "ROLE_TYPE")
    @ApiModelProperty(name = "roleType", value = "역할 타입")
    private String roleType;

    /**
     * 사용 여부
     * @author [오지훈]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부")
    private String useYn;

    /**
     * 하위 권한 목록
     * @author [오지훈]
     */
    @OneToMany
    @JoinColumn(name = "UPPER_AUTH_SEQ",
            referencedColumnName = "AUTH_SEQ",
            insertable = false, updatable = false)
    private List<Auth> subAuths;

    /**
     * 정보 변경
     *
     * @param upperAuthSeq the upper auth seq
     * @param authName     the auth name
     * @param roleType     the role type
     * @param useYn        the use yn
     * @param updaterSeq   the updater seq
     */
    public void update(
            Long upperAuthSeq
            , String authName
            , String roleType
            , String useYn
            , Long updaterSeq
    ) {
        this.upperAuthSeq = upperAuthSeq;
        this.authName = authName;
        this.roleType = roleType;
        this.useYn = useYn;
        setUpdaterSeq(updaterSeq);
    }

}
