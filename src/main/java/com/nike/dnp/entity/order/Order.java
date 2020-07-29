package com.nike.dnp.entity.order;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * The Class Order.
 *
 * @author [윤태호]
 * @since 2020. 6. 26. 오후 4:16:38
 * @implNote
 */
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_ORDER")
public class Order extends BaseTimeEntity {


    /**
     * 주문 시퀀스
     *
     * @author [윤태호]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_SEQ")
    @ApiModelProperty(name = "orderSeq",value = "주문 시퀀스")
    private Long orderSeq;

    /**
     * 주문 코멘트
     *
     * @author [윤태호]
     */
    @Column(name = "ORDER_DESCRIPTION")
    @ApiModelProperty(name = "orderDescription", value = "주문 코맨트")
    private String orderDescription;

    /**
     * 총 금액
     *
     * @author [윤태호]
     */
    @Column(name = "TOTAL_AMOUNT")
    @ApiModelProperty(name = "totalAmount", value = "총 금액")
    private Long totalAmount;


}
