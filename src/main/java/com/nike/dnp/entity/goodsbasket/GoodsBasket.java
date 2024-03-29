package com.nike.dnp.entity.goodsbasket;

import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.entity.product.Product;
import com.nike.dnp.util.SecurityUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;

/**
 * GoodsBasket
 *
 * @author [윤태호]
 * @since 2020. 7. 2. 오후 4:19:38
 * @implNote
 */
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "TB_GOODS_BASKET")
public class GoodsBasket extends BaseTimeEntity {


    /**
     * 장바구니 시퀀스
     *
     * @author [윤태호]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GOODS_BASKET_SEQ")
    @ApiModelProperty(name = "goodsBasketSeq", value = "장바구니 시퀀스")
    private Long goodsBasketSeq;


    /**
     * 유저 시퀀스
     *
     * @author [윤태호]
     */
    @Column(name = "USER_SEQ")
    @ApiModelProperty(name = "userSeq", value = "유저 시퀀스")
    private Long userSeq;


    /**
     * 상품 시퀀스
     *
     * @author [윤태호]
     */
    @Column(name = "GOODS_SEQ")
    @ApiModelProperty(name = "goodsSeq", value = "상품 시퀀스")
    private Long goodsSeq;


    /**
     * 주문 수량
     *
     * @author [윤태호]
     */
    @Column(name = "ORDER_QUANTITY")
    @ApiModelProperty(name = "orderQuantity", value = "주문 수량")
    private Long orderQuantity;

    /**
     * 상품 정보
     *
     * @author [윤태호]
     */
    @ManyToOne
    @JoinColumn(name = "GOODS_SEQ", insertable = false, updatable = false)
    private Product product;

    /**
     * Pre persist.
     *
     * @author [오지훈]
     * @since 2020. 7. 24. 오전 10:05:55
     * @implNote
     */
    @PrePersist
    public void prePersist(){
        if(ObjectUtils.isEmpty(this.userSeq)){
            this.userSeq = SecurityUtil.currentUser().getUserSeq();
        }
    }

}
