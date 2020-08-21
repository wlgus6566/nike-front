package com.nike.dnp.dto.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.util.CloudFrontUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * The Class Product result dto.
 *
 * @author [오지훈]
 * @since 2020. 7. 21. 오후 3:55:06
 * @implNote
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ProductResultDTO {

	/**
	 * 상품 시퀀스
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "goodsSeq", value = "상품 시퀀스")
	private Long goodsSeq;

	/**
	 * 대분류 코드
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "category2Code", value = "대분류 공통코드")
	private String category2Code;

	/**
	 * 소분류 코드
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "category3Code", value = "소분류 공통코드")
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
	private String imageFilePhysicalName;


	@ApiModelProperty(name = "imageFilePhysicalName", value = "이미지 파일 물리 파일명")
	public String getImageFilePhysicalName() {
		return CloudFrontUtil.getCustomSignedUrl(imageFilePhysicalName);
	}

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
	 * 에이전시 이름
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "agencyName", value = "에이젼시 이름")
	private String agencyName;

	/**
	 * 최종 수정일
	 *
	 * @author [오지훈]
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
	@ApiModelProperty(name = "updateDt", value = "최종 수정일")
	private LocalDateTime updateDt;

	/**
	 * 유저 닉네임
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "nickname", value = "유저 닉네임")
	private String nickname;

	/**
	 * 대분류 코드명
	 *
	 * @return the string
	 * @author [오지훈]
	 * @since 2020. 7. 21. 오후 4:03:46
	 * @implNote
	 */
	@ApiModelProperty(name = "category2Name", value = "대분류 명")
	public String getCategory2Name(){
		for(final ServiceCode.ProductCategory2EnumCode value : ServiceCode.ProductCategory2EnumCode.values()){
			if(value.name().equalsIgnoreCase(this.category2Code)){
				return value.getMessage();
			}
		}
		return this.category2Code;
	}

	/**
	 * 소구분 코드명
	 *
	 * @return the category 3 name
	 * @author [오지훈]
	 * @since 2020. 7. 21. 오후 4:03:46
	 * @implNote 소구분 코드명
	 */
	@ApiModelProperty(name = "category3Name", value = "소구분 명")
	public String getCategory3Name() {
		for(final ServiceCode.ProductCategory3EnumCode value : ServiceCode.ProductCategory3EnumCode.values()){
			if(value.name().equalsIgnoreCase(this.category3Code)){
				return value.getMessage();
			}
		}
		return this.category3Code;
	}




}
