package com.nike.dnp.entity.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * The Class Order mail.
 *
 * @author [이소정]
 * @since 2021. 1. 6. 오후 8:16:36
 */
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_ORDER_MAIL")
public class OrderMail extends BaseTimeEntity {


    /**
     * The Order email seq
     *
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_EMAIL_SEQ")
    @ApiModelProperty(name = "orderEmailSeq",value = "주문_메일_시퀀스")
    private Long orderEmailSeq;

    /**
     * The Order seq
     *
     * @author [이소정]
     */
    @Column(name = "ORDER_SEQ")
    @ApiModelProperty(name = "orderSeq",value = "주문 시퀀스")
    private Long orderSeq;

    /**
     * The User seq
     *
     * @author [이소정]
     */
    @Column(name = "USER_SEQ")
    @ApiModelProperty(name = "userSeq", value ="유저 시퀀스", example = "example")
    private Long userSeq;

    /**
     * 유저 ID
     *
     * @author [이소정]
     */
    @Column(name = "USER_ID")
    @ApiModelProperty(name = "userId", value = "유저 ID", required = true, example = "test@nike.co.kr")
    private String userId;

    /**
     * 닉네임
     *
     * @author [이소정]
     */
    @Column(name = "NICKNAME")
    @ApiModelProperty(name = "nickname", value = "닉네임", required = true, example = "Nike이모션점")
    private String nickname;

    /**
     * 주문 정보
     *
     * @author [이소정]
     */
    @ManyToOne
    @JoinColumn(name="ORDER_SEQ", insertable = false, updatable = false)
    @JsonBackReference
    @ApiModelProperty(name = "order", value = "주문 정보")
    private OrderEntity orderEntity;

}
