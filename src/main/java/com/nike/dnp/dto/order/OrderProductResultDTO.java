package com.nike.dnp.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderProductResultDTO {

	@ApiModelProperty(name = "orderSeq", value = "주문 상품 시퀀스")
	private Long orderSeq;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
	@ApiModelProperty(name = "registrationDt", value = "최초 작성일", hidden = true)
	private LocalDateTime registrationDt;

	@ApiModelProperty(name = "orderQuantity", value = "주문 상품 시퀀스")
	private Long orderQuantity;

	@ApiModelProperty(name = "goodsName", value = "상품명", required = true)
	private String goodsName;

	@ApiModelProperty(name = "goodsDescription", value = "상품 설명")
	private String goodsDescription;

	@ApiModelProperty(name = "agencyName", value = "에이젼시 이름")
	private String agencyName;

	@ApiModelProperty(name = "agencySeq", value = "에이전시 시퀀스")
	private long agencySeq;

	@ApiModelProperty(name = "email", value = "이메일")
	private String email;

	@ApiModelProperty(name = "orderDescription", value = "주문 코맨트")
	private String orderDescription;
}
