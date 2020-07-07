package com.nike.dnp.entity.order;

import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.entity.product.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_GOODS_SEQ")
    @ApiModelProperty(name="orderGoodsSeq" , value="주문 상품 시퀀스")
    private Long orderGoodsSeq;


    /**
     * The Order seq
     *
     * @author [윤태호]
     */
    @Column(name = "ORDER_SEQ")
    @ApiModelProperty(name = "orderSeq", value = "주문 상품 시퀀스")
    private Long orderSeq;
    /**
     * The Goods seq
     *
     * @author [윤태호]
     */
    @Column(name = "GOODS_SEQ")
    @ApiModelProperty(name = "goodsSeq", value = "주문 상품 시퀀스")
    private Long goodsSeq;


    /**
     * The Order quantity
     *
     * @author [윤태호]
     */
    @Column(name = "ORDER_QUANTITY")
    @ApiModelProperty(name = "orderQuantity", value = "주문 상품 시퀀스")
    private Long orderQuantity;


    /**
     * The Product
     *
     * @author [윤태호]
     */
    @ManyToOne
    @JoinColumn(name="GOODS_SEQ",insertable = false,updatable = false)
    @ApiModelProperty(name = "product", value = "제품 정보")
    private Product product;

    /**
     * The Order
     *
     * @author [윤태호]
     */
    @ManyToOne
    @JoinColumn(name="ORDER_SEQ",insertable = false,updatable = false)
    @ApiModelProperty(name = "product", value = "주문 정보")
    private Order order;

}
