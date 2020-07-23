package com.nike.dnp.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * The Class Order product result dto.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 21. 오후 3:54:59
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class OrderProductResultDTO {

	/**
	 * The Order seq
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "orderSeq", value = "주문 상품 시퀀스")
	private Long orderSeq;

	/**
	 * The Registration dt
	 *
	 * @author [오지훈]
	 */
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
	@ApiModelProperty(name = "registrationDt", value = "최초 작성일", hidden = true)
	private LocalDateTime registrationDt;

	/**
	 * The Order quantity
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "orderQuantity", value = "주문 상품 시퀀스")
	private Long orderQuantity;

	/**
	 * The Goods name
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "goodsName", value = "상품명", required = true)
	private String goodsName;

	/**
	 * The Goods description
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "goodsDescription", value = "상품 설명")
	private String goodsDescription;

	/**
	 * The Agency name
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "agencyName", value = "에이젼시 이름")
	private String agencyName;

	/**
	 * The Agency seq
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "agencySeq", value = "에이전시 시퀀스")
	private long agencySeq;

	/**
	 * The Email
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "email", value = "이메일")
	private String email;

	/**
	 * The Order description
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "orderDescription", value = "주문 코맨트")
	private String orderDescription;
}
