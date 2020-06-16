package com.nike.dnp.entity.code;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_CODE")
public class Code extends BaseTimeEntity {

    /**
     * 코드 시퀀스
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODE_SEQ")
    @ApiModelProperty(name = "codeSeq", value = "코드 시퀀스", hidden = true)
    private Long codeSeq;

    /**
     * 코드
     * @author [오지훈]
     */
    @Column(name = "CODE")
    @ApiModelProperty(name = "code", value = "코드", required = true, hidden = true)
    private String code;

    /**
     * 상위 코드
     * @author [오지훈]
     */
    @Column(name = "UPPER_CODE")
    @ApiModelProperty(name = "upperCode", value = "상위 코드")
    private String upperCode;

    /**
     *
     */
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPPER_CODE")
    private Code upperCode;*/

    /**
     *
     *
    //@OneToMany(mappedBy = "upperCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private List<Code> subCodes;

    /**
     * 코드 명
     * @author [오지훈]
     */
    @Column(name = "CODE_NAME")
    @ApiModelProperty(name = "codeName", value = "코드 명", required = true)
    private String codeName;

    /**
     * 코드 설명
     * @author [오지훈]
     */
    @Column(name = "CODE_DESCRIPTION")
    @ApiModelProperty(name = "codeDescription", value = "코드 설명")
    private String codeDescription;

    /**
     * 코드 순서
     * @author [오지훈]
     */
    @Column(name = "CODE_ORDER")
    @ApiModelProperty(name = "codeOrder", value = "코드 순서", required = true)
    private Long codeOrder;

    /**
     * 사용 여부
     * @author [오지훈]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부", required = true)
    private String useYn;

    /**
     * Update.
     *
     * @param upperCode       the upper code
     * @param codeName        the code name
     * @param codeDescription the code description
     * @param codeOrder       the code order
     * @param useYn           the use yn
     * @param updaterSeq      the updater seq
     */
    public void update(
            String upperCode
            , String codeName
            , String codeDescription
            , Long codeOrder
            , String useYn
            , Long updaterSeq
    ) {
        this.upperCode = upperCode;
        this.codeName = codeName;
        this.codeDescription = codeDescription;
        this.codeOrder = codeOrder;
        this.useYn = useYn;
        setUpdaterSeq(updaterSeq);
    }

    /**
     * Delete.
     *
     * @param useYn      the use yn
     * @param updaterSeq the updater seq
     */
    public void delete(
            String useYn
            , Long updaterSeq
    ) {
        this.useYn = useYn;
        setUpdaterSeq(updaterSeq);
    }
}
