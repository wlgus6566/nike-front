package com.nike.dnp.entity.order;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * The Class Order.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 6. 26. 오후 4:16:38
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_ORDER")
public class Order extends BaseTimeEntity {


    /**
     * The Order seq
     *
     * @author [윤태호]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_SEQ")
    @ApiModelProperty(name = "orderSeq",value = "주문 시퀀스")
    private Long orderSeq;

    /**
     * The Order description
     *
     * @author [윤태호]
     */
    @Column(name = "ORDER_DESCRIPTION")
    @ApiModelProperty(name = "orderDescription", value = "주문 코맨트")
    private String orderDescription;

    /**
     * The Total amount
     *
     * @author [윤태호]
     */
    @Column(name = "TOTAL_AMOUNT")
    @ApiModelProperty(name = "totalAmount", value = "총 금액")
    private Long totalAmount;


}
