package com.nike.dnp.dto.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.common.variable.ServiceEnumCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResultDTO {


	@ApiModelProperty(name = "goodsSeq", value = "상품 시퀀스")
	private Long goodsSeq;

	/**
	 * 카테고리 2 코드
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "category2Code", value = "카테고리2 공통코드")
	private String category2Code;

	/**
	 * 카테고리 3 코드
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "category3Code", value = "카테고리3 공통코드")
	private String category3Code;

	/**
	 * 에이젼시 시퀀스
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "agencySeq", value = "에이젼시 시퀀스")
	private Long agencySeq;

	/**
	 * 이미지 파일명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "imageFileName", value = "이미지 파일명")
	private String imageFileName;

	/**
	 * 이미지 파일 사이즈
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "imageFileSize", value = "이미지 파일 사이즈")
	private String imageFileSize;

	/**
	 * 이미지 파일 물리 파일명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "imageFilePhysicalName", value = "이미지 파일 물리 파일명")
	private String imageFilePhysicalName;

	/**
	 * 썸네일 파일명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "thumbnailFileName", value = "썸네일 파일명")
	private String thumbnailFileName;


	/**
	 * 썸네일 파일 사이즈
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "thumbnailFileSize", value = "썸네일 파일 사이즈")
	private String thumbnailFileSize;


	/**
	 * 썸네일 파일 물리 파일명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "thumbnailFilePhysicalName", value = "썸네일 파일 물리 파일명")
	private String thumbnailFilePhysicalName;


	/**
	 * 상품명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "goodsName", value = "상품명")
	private String goodsName;

	/**
	 * 상품설명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "goodsDescription", value = "상품 설명")
	private String goodsDescription;

	/**
	 * 단가
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "unitPrice", value = "단가")
	private Long unitPrice;

	/**
	 * 최소 주문 수량
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "minimumOrderQuantity", value = "최소 주문 수량")
	private Long minimumOrderQuantity;

	/**
	 * 노출 여부
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "exposureYn", value = "노출_여부")
	private String exposureYn;

	/**
	 * 사용여부
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "useYn", value = "사용 여부")
	private String useYn;


	/**
	 * The Agency name
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "agencyName", value = "에이젼시 이름")
	private String agencyName;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")	
	@ApiModelProperty(name = "updateDt", value = "최종 수정일")
	private LocalDateTime updateDt;

	@ApiModelProperty(name = "nickName", value = "유저 닉네임")
	private String nickName;

	@ApiModelProperty(name = "category2Name", value = "카테고리 2 명")
	public String getCategory2Name(){
		for(ServiceEnumCode.ProductCategory2EnumCode value : ServiceEnumCode.ProductCategory2EnumCode.values()){
			if(value.name().equalsIgnoreCase(this.category2Code)){
				return value.getMessage();
			}
		}
		return this.category2Code;
	}

	@ApiModelProperty(name = "category3Name", value = "카테고리 3 명")
	public String getCategory3Name() {
		for(ServiceEnumCode.ProductCategory3EnumCode value : ServiceEnumCode.ProductCategory3EnumCode.values()){
			if(value.name().equalsIgnoreCase(this.category3Code)){
				return value.getMessage();
			}
		}
		return this.category3Code;
	}


}
