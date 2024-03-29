package com.nike.dnp.entity.wishlist;

import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.entity.product.Product;
import com.nike.dnp.util.SecurityUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;

/**
 * WishList
 *
 * @author [윤태호]
 * @since 2020. 7. 2. 오후 4:18:55
 * @implNote
 */
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_WISH_LIST")
public class WishList extends BaseTimeEntity {

    /**
     * 위시 리스트 시퀀스
     *
     * @author [윤태호]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WISH_LIST_SEQ")
    @ApiModelProperty(name = "wishListSeq", value = "위시 리스트 시퀀스")
    private Long wishListSeq;

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
     * 상품 정보
     * @author [윤태호]
     */
    @ManyToOne
    @JoinColumn(name = "GOODS_SEQ", insertable = false, updatable = false)
    private Product product;

    /**
     * Pre persist.
     *
     * @author [오지훈]
     * @since 2020. 7. 24. 오전 10:02:38
     * @implNote
     */
    @PrePersist
    private void prePersist () {
        if(ObjectUtils.isEmpty(this.userSeq)){
            this.userSeq = SecurityUtil.currentUser().getUserSeq();
        }
    }

}
