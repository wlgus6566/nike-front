package com.nike.dnp.entity.report;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_REPORT_BASKET")
public class ReportBasket extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_BASKET_SEQ")
    @ApiModelProperty(name = "reportBasketSeq", value = "보고서 장바구니 시퀀스", example = "example")
    private Long reportBasketSeq;

    @Column(name = "USER_SEQ")
    @ApiModelProperty(name = "userSeq", value = "등록자 시퀀스", example = "example")
    private Long userSeq;

    @Column(name = "REPORT_FILE_SEQ")
    @ApiModelProperty(name = "reportFileSeq", value = "보고서 파일 시퀀스", example = "example")
    private Long reportFileSeq;

}
