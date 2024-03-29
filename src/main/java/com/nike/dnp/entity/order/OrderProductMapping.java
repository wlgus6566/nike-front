package com.nike.dnp.entity.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.entity.product.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * The Class Order product mapping.
 *
 * @author [윤태호]
 * @implNote
 * @since 2020. 6. 26. 오후 4:16:42
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
     * 주문 상품 시퀀스
     *
     * @author [윤태호]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_GOODS_SEQ")
    @ApiModelProperty(name = "orderGoodsSeq" , value = "주문 상품 시퀀스")
    private Long orderGoodsSeq;


    /**
     * 주문 시퀀스
     *
     * @author [윤태호]
     */
    @Column(name = "ORDER_SEQ")
    @ApiModelProperty(name = "orderSeq", value = "주문 시퀀스")
    private Long orderSeq;

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
     * 제품 정보
     *
     * @author [윤태호]
     */
    @ManyToOne
    @JoinColumn(name="GOODS_SEQ",insertable = false,updatable = false)
    @ApiModelProperty(name = "product", value = "제품 정보")
    private Product product;

    /**
     * 주문 정보
     *
     * @author [윤태호]
     */
    @ManyToOne
    @JoinColumn(name="ORDER_SEQ",insertable = false,updatable = false)
    @JsonBackReference
    @ApiModelProperty(name = "order", value = "주문 정보")
    private OrderEntity orderEntity;

    /**
     * 상품 설명
     *
     * @author [이소정]
     */
    @Column(name = "PRODUCT_DESCRIPTION")
    @ApiModelProperty(name = "productDescription", value = "상품 설명")
    private String productDescription;

    /**
     * The Order product file list
     *
     * @author [이소정]
     */
    @OneToMany(mappedBy = "orderProduct")
    @ApiModelProperty(name = "orderProductFileList", value = "주문 상품 파일 목록")
    @JsonManagedReference
    private List<OrderProductFile> orderProductFileList;

}
