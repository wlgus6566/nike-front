package com.nike.dnp.entity.code;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Code Entity
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:09:56
 * @Description Code(공통 코드) Entity 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_CODE")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Code extends BaseTimeEntity implements Serializable {

    /**
     * 코드 시퀀스
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODE_SEQ")
    @ApiModelProperty(name = "codeSeq", value = "코드 시퀀스", hidden = true)
    private Long codeSeq;

    /**
     * 코드
     *
     * @author [오지훈]
     */
    @Column(name = "CODE")
    @ApiModelProperty(name = "code", value = "코드", required = true, hidden = true)
    private String code;

    /**
     * 상위 코드
     *
     * @author [오지훈]
     */
    @Column(name = "UPPER_CODE")
    @ApiModelProperty(name = "upperCode", value = "상위 코드")
    private String upperCode;

    /**
     * 코드 명
     *
     * @author [오지훈]
     */
    @Column(name = "CODE_NAME")
    @ApiModelProperty(name = "codeName", value = "코드 명", required = true)
    private String codeName;

    /**
     * 코드 설명
     *
     * @author [오지훈]
     */
    @Column(name = "CODE_DESCRIPTION")
    @ApiModelProperty(name = "codeDescription", value = "코드 설명")
    private String codeDescription;

    /**
     * 코드 순서
     *
     * @author [오지훈]
     */
    @Column(name = "CODE_ORDER")
    @ApiModelProperty(name = "codeOrder", value = "코드 순서", required = true)
    private Long codeOrder;

    /**
     * 사용 여부
     *
     * @author [오지훈]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부", required = true)
    private String useYn;

    /**
     * 하위 코드 목록
     *
     * @author [오지훈]
     */
    @OneToMany
    @JoinColumn(name = "UPPER_CODE",
            referencedColumnName = "CODE",
            insertable = false, updatable = false)
    private List<Code> subCodes;

    /**
     * Update.
     *
     * @param codeName        the code name
     * @param codeDescription the code description
     * @param codeOrder       the code order
     * @param updaterSeq      the updater seq
     * @param upperCode       the upper code
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 6:09:56
     * @Description 하위 Update.
     */
    public void update(
            String codeName
            , String codeDescription
            , Long codeOrder
            , Long updaterSeq
            , String upperCode
    ) {
        this.upperCode = upperCode;
        this.codeName = codeName;
        this.codeDescription = codeDescription;
        this.codeOrder = codeOrder;
        setUpdaterSeq(updaterSeq);
    }

    /**
     * Update.
     *
     * @param codeName        the code name
     * @param codeDescription the code description
     * @param codeOrder       the code order
     * @param updaterSeq      the updater seq
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 6:09:56
     * @Description 상위 Update.
     */
    public void update(
            String codeName
            , String codeDescription
            , Long codeOrder
            , Long updaterSeq
    ) {
        this.codeName = codeName;
        this.codeDescription = codeDescription;
        this.codeOrder = codeOrder;
        setUpdaterSeq(updaterSeq);
    }

    /**
     * Delete.
     *
     * @param useYn      the use yn
     * @param updaterSeq the updater seq
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 6:09:56
     * @Description
     */
    public void delete(
            String useYn
            , Long updaterSeq
    ) {
        this.useYn = useYn;
        setUpdaterSeq(updaterSeq);
    }
}
