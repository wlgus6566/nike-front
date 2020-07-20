package com.nike.dnp.entity.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.dto.auth.AuthSaveDTO;
import com.nike.dnp.dto.auth.AuthUpdateDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Auth Entity
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 3:41:36
 * @Description Auth(권한) Entity 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_AUTH")
@DynamicUpdate
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Auth extends BaseTimeEntity implements Serializable {

    /**
     * 권한 시퀀스
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTH_SEQ")
    @ApiModelProperty(name = "authSeq", value = "권한 시퀀스", hidden = true)
    private Long authSeq;

    /**
     * 상위 권한 시퀀스
     *
     * @author [오지훈]
     */
    @Column(name = "UPPER_AUTH_SEQ")
    @ApiModelProperty(name = "upperAuthSeq", value = "상위 권한 시퀀스", hidden = true)
    private Long upperAuthSeq;

    /**
     * 권한명
     *
     * @author [오지훈]
     */
    @Column(name = "AUTH_NAME")
    @ApiModelProperty(name = "authName", value = "권한명")
    private String authName;

    /**
     * 역할 타입
     *
     * @author [오지훈]
     */
    @Column(name = "ROLE_TYPE")
    @ApiModelProperty(name = "roleType", value = "역할 타입")
    private String roleType;

    /**
     * 사용 여부
     *
     * @author [오지훈]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부")
    private String useYn;

    /**
     * 하위 권한 목록
     *
     * @author [오지훈]
     */
    @OneToMany
    @JoinColumn(name = "UPPER_AUTH_SEQ",
            referencedColumnName = "AUTH_SEQ",
            insertable = false, updatable = false)
    @ApiModelProperty(name = "subAuths", value = "하위 권한")
    private List<Auth> subAuths;

    /**
     * Instantiates a new Auth.
     *
     * @param authSaveDTO the auth save dto
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 4:35:39
     * @Description 그룹(권한) 생성
     */
    @Builder
    public Auth(final AuthSaveDTO authSaveDTO) {
        this.upperAuthSeq = authSaveDTO.getUpperAuthSeq();
        this.authName = authSaveDTO.getAuthName();
        this.roleType = "ROLE_" + authSaveDTO.getUpperAuthSeq() + "_" + System.currentTimeMillis();
        this.useYn = "Y";
    }

    /**
     * Update.
     *
     * @param authUpdateDTO the auth update dto
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 3:41:36
     * @Description 그룹(권한) 정보 수정
     */
    public void update(final AuthUpdateDTO authUpdateDTO) {
        this.authName = authUpdateDTO.getAuthName();
    }

    /**
     * Delete.
     *
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 4:35:37
     * @Description 그룹(권한) 삭제
     */
    public void delete() {
        this.useYn = "N";
    }

}
