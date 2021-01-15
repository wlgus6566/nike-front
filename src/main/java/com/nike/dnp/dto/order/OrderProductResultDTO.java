package com.nike.dnp.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.nike.dnp.entity.agency.Agency;
import com.nike.dnp.entity.order.OrderEntity;
import com.nike.dnp.entity.order.OrderProductFile;
import com.nike.dnp.entity.order.OrderProductMapping;
import com.nike.dnp.entity.product.Product;
import com.nike.dnp.util.CloudFrontUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Class Order product result dto.
 *
 * @author [오지훈]
 * @implNote
 * @since 2020. 7. 21. 오후 3:54:59
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class OrderProductResultDTO {

	/**
	 * 주문 상품 시퀀스
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "orderSeq", value = "주문 상품 시퀀스")
	private Long orderSeq;

	/**
	 * 등록일
	 *
	 * @author [오지훈]
	 */
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
	@ApiModelProperty(name = "registrationDt", value = "최초 작성일", hidden = true)
	private LocalDateTime registrationDt;

	/**
	 * 주문 상품 시퀀스
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "orderQuantity", value = "주문 상품 시퀀스")
	private Long orderQuantity;

	/**
	 * 상품명
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "goodsName", value = "상품명", required = true)
	private String goodsName;

	/**
	 * 상품 설명
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "goodsDescription", value = "상품 설명")
	private String goodsDescription;

	/**
	 * 에이젼시 이름
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "agencyName", value = "에이젼시 이름")
	private String agencyName;

	/**
	 * 에이젼시 시퀀스
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "agencySeq", value = "에이전시 시퀀스")
	private long agencySeq;

	/**
	 * 이메일
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "email", value = "이메일")
	private String email;

	/**
	 * 주문 코멘트
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "orderDescription", value = "주문 코맨트")
	private String orderDescription;

	private Long orderGoodsSeq;

	/**
	 * 이미지 파일
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(hidden = true)
	private String imageFilePhysicalName;

	/**
	 * 상품 설명
	 *
	 * @author [이소정]
	 */
	@ApiModelProperty(name = "productDescription", value = "상품 설명", example = "상품 설명입니다.")
	private String productDescription;


	/**
	 * The Order product file list
	 *
	 * @author [이소정]
	 */
	@ApiModelProperty(name = "orderProductFileList", value = "주문 상품 파일 목록")
	private List<OrderProductFileSaveDTO> orderProductFile;

	public OrderProductResultDTO(OrderProductMapping orderProductMapping
			, Product product, Agency agency, OrderEntity order) {
		this.orderSeq = orderProductMapping.getOrderSeq();
		this.registrationDt = orderProductMapping.getRegistrationDt();
		this.orderQuantity = orderProductMapping.getOrderQuantity();
		this.goodsName = product.getGoodsName();
		this.goodsDescription = product.getGoodsDescription();
		this.imageFilePhysicalName = product.getImageFilePhysicalName();
		this.agencyName = agency.getAgencyName();
		this.agencySeq = agency.getAgencySeq();
		this.email = agency.getEmail();
		this.orderDescription = order.getOrderDescription();
		this.productDescription = orderProductMapping.getProductDescription();

		this.orderGoodsSeq = orderProductMapping.getOrderGoodsSeq();
	}




}
