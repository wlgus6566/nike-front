package com.nike.dnp.dto.product;


import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * productSaveDTO
 *
 * @author [윤태호]
 * @Description Product Save DTO 작성
 * @history [오지훈] [2020.06.17] [최초 작성]
 * @since 2020.06.17
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ProductUpdateDTO extends BasicDTO {


	/**
	 * 상품시퀀스
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name="goodsSeq",value = "상품 시퀀스",required = true,example = "27")
	private Long goodsSeq;


	/**
	 * 상태
	 * @author [윤태호]
	 */
	@ApiModelProperty(name="exposureYn",value="상태", required = true,example = "Y")
	private String exposureYn;

	/**
	 * 카테고리 2 코드
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name="category2code",value="구분 2 단계", required = true,example = "2")
	private String category2code;

	/**
	 * 카테고리 3 코드
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "category3code", value = "구분 3 단계", required = true,example = "3")
	private String category3code;

	/**
	 * 에이젼시 시퀀스
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "agencySeq", value = "에이젼시 시퀀스", required = true,example = "1")
	private Long agencySeq;

	/**
	 * 상품 명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "goodsName", value = "상품 명", required = true,example = "수정명입니다.")
	private String goodsName;

	/**
	 * 추가 설명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "goodsDescription", value = "추가설명", required = true,example = "수정 추가 설명")
	private String goodsDescription;

	/**
	 * 최소 주문 수량
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "minimumOrderQuantity", value = "최소 주문 수량", required = true,example = "100")
	private Long minimumQuantity;

	/**
	 * 사이즈
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "size", value = "사이즈",hidden = true)
	private String size;


	/**
	 * 단가
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "size", value = "단가", required = true,example = "10000")
	private Long unitPrice;

	/**
	 * 이미지 파일 이름
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "imageFileName", value = "이미지 파일 명",example = "update.jpg")
	private String imageFileName;

	/**
	 * 이미지 파일 사이즈
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "imageFileSize", value = "이미지 파일 사이즈",example = "1000")
	private Long imageFileSize;

	/**
	 * 이미지 파일 물리명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "imageFilePhysicalName", value = "이미지 파일 물리 명",example = "product/update.jpg")
	private String imageFilePhysicalName;


	/**
	 * 썸네일 파일명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "thumbnailFileName", value = "썸네일 파일 명",example = "thumbnail_update.jpg")
	private String thumbnailFileName;

	/**
	 * 썸네일 파일 사이즈
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "thumbnailFileSize", value = "썸네일 파일 사이즈",example = "100")
	private String thumbnailFileSize;

	/**
	 * 썸네일 파일 물리명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "thumbnailFilePhysicalName", value = "썸네일 파일 물리 명",example = "product/thumbnail_update.jpg")
	private String thumbnailFilePhysicalName;


}
