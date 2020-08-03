package com.nike.dnp.entity.report;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * The Class Report basket.
 *
 * @author [이소정]
 * @implNote 보고서 장바구니 entity
 * @since 2020. 7. 17. 오후 5:53:48
 */
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_REPORT_BASKET")
public class ReportBasket extends BaseTimeEntity {

    /**
     * The Report basket seq
     *
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_BASKET_SEQ")
    @ApiModelProperty(name = "reportBasketSeq", value = "보고서 장바구니 시퀀스", example = "1")
    private Long reportBasketSeq;

    /**
     * The User seq
     *
     * @author [이소정]
     */
    @Column(name = "USER_SEQ")
    @ApiModelProperty(name = "userSeq", value = "등록자 시퀀스", example = "1")
    private Long userSeq;

    /**
     * The Report file seq
     *
     * @author [이소정]
     */
    @Column(name = "REPORT_FILE_SEQ")
    @ApiModelProperty(name = "reportFileSeq", value = "보고서 파일 시퀀스", example = "1")
    private Long reportFileSeq;

    /**
     * Save report basket.
     *
     * @param reportFileSeq the report file seq
     * @param userSeq       the user seq
     * @return the report basket
     * @author [이소정]
     * @implNote 보고서 장바구니 저장
     * @since 2020. 7. 17. 오후 7:04:39
     */
    public ReportBasket save(final Long reportFileSeq, final Long userSeq) {
        final ReportBasket reportBasket = new ReportBasket();
        reportBasket.setUserSeq(userSeq);
        reportBasket.setReportFileSeq(reportFileSeq);
        return reportBasket;
    }

}
