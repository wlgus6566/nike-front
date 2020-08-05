package com.nike.dnp.entity.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * The Class Order.
 *
 * @author [윤태호]
 * @implNote
 * @since 2020. 6. 26. 오후 4:16:38
 */
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_ORDER")
public class OrderEntity extends BaseTimeEntity {


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

    /**
     * 사용여부
     *
     * @author [윤태호]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용여부")
    private String useYn;


    @OneToMany(mappedBy = "orderEntity")
    @JsonManagedReference
    private List<OrderProductMapping> orderProductMapping;

}
