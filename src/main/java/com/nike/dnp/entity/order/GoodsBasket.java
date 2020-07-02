package com.nike.dnp.entity.order;

import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.entity.user.User;
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
 * @CreatedOn 2020. 7. 2. 오후 4:19:38
 * @Description
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
     * 유저 정보
     *
     * @author [윤태호]
     */
    @ManyToOne
    @JoinColumn(name="USER_SEQ",insertable = false,updatable = false)
    private User user;


    /**
     * 상품 정보
     *
     * @author [윤태호]
     */
    @ManyToOne
    @JoinColumn(name = "GOODS_SEQ", insertable = false, updatable = false)
    private Product product;


    @PrePersist
    public void prePersist(){
        if(ObjectUtils.isEmpty(this.userSeq)){
            this.userSeq = SecurityUtil.currentUser().getUserSeq();
        }
    }

}
