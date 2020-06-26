package com.nike.dnp.entity.order;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Order product mapping.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 6. 26. 오후 4:16:42
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "TB_ORDER_PRODUCT_MAPPING")
public class OrderProductMapping extends BaseTimeEntity {


    /**
     * The Order goods seq
     *
     * @author [윤태호]
     */
    @Id
    @Column(name = "ORDER_GOODS_SEQ")
    @ApiModelProperty(name="orderGoodsSeq" , value="주문 상품 시퀀스")
    private long orderGoodsSeq;


    /**
     * The Order seq
     *
     * @author [윤태호]
     */
    @Column(name = "ORDER_SEQ")
    @ApiModelProperty(name = "orderSeq", value = "주문 상품 시퀀스")
    private long orderSeq;


    /**
     * The Agency seq
     *
     * @author [윤태호]
     */
    @Column(name = "AGENCY_SEQ")
    @ApiModelProperty(name = "agencySeq", value = "주문 상품 시퀀스")
    private long agencySeq;


    /**
     * The Goods seq
     *
     * @author [윤태호]
     */
    @Column(name = "GOODS_SEQ")
    @ApiModelProperty(name = "goodsSeq", value = "주문 상품 시퀀스")
    private long goodsSeq;


    /**
     * The Order quantity
     *
     * @author [윤태호]
     */
    @Column(name = "ORDER_QUANTITY")
    @ApiModelProperty(name = "orderQuantity", value = "주문 상품 시퀀스")
    private long orderQuantity;



}
