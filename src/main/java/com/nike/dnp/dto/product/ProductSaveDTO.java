package com.nike.dnp.dto.product;


import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * productSaveDTO
 *
 * @author [윤태호]
 * @CreatedOn 2020. 6. 25. 오전 11:12:53
 * @Description Product Save DTO 작성
 * @history [오지훈] [2020.06.17] [최초 작성]
 * @since 2020.06.17
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ProductSaveDTO extends BasicDTO {


	/**
	 * 상태
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name="exposureYn",value="상태",required = true,example = "Y")
	private String exposureYn;

	/**
	 * 카테고리 2 코드
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name="category2code",value="카테고리 2 코드",required = true,example = "NIKE_BY_YOU")
	private String category2code;

	/**
	 * 카테고리 3 코드
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "category3code", value = "카테고리 3 코드", required = true,example = "NIKE_BY_YOU27")
	private String category3code;

	/**
	 * 에이전시 시퀀스
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "agencySeq", value = "에이전시 시퀀스", required = true,example = "1")
	private long agencySeq;

	/**
	 * 상품 명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "goodsName", value = "상품 명", required = true,example = "상품명입니다.")
	private String goodsName;

	/**
	 * 추가 설명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "goodsDescription", value = "추가설명", required = true,example = "추가설명 입니다.")
	private String goodsDescription;

	/**
	 * 단가
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "size", value = "단가", required = true,example = "1000")
	private Long unitPrice;


	/**
	 * 최소 주문 수량
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "minimumOrderQuantity", value = "최소 주문 수량", required = true, example = "10")
	private Long minimumQuantity;

	/**
	 * 사이즈
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "size", value = "사이즈",hidden = true)
	private String size;


	/**
	 * 이미지 파일 이름
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "imageFileName", value = "이미지 파일 명", example = "test.jpg")
	private String imageFileName;

	/**
	 * 이미지 파일 사이즈
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "imageFileSize", value = "이미지 파일 사이즈", example = "10000")
	private Long imageFileSize;

	/**
	 * 이미지 파일 물리명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "imageFilePhysicalName", value = "이미지 파일 물리 명", example = "product/test.jpg")
	private String imageFilePhysicalName;


	/**
	 * 썸네일 파일명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "thumbnailFileName", value = "썸네일 파일 명", example = "thumbnail_test.jpg")
	private String thumbnailFileName;

	/**
	 * 썸네일 파일 사이즈
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "thumbnailFileSize", value = "썸네일 파일 사이즈", example = "1000")
	private String thumbnailFileSize;

	/**
	 * 썸네일 파일 물리명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "thumbnailFilePhysicalName", value = "썸네일 파일 물리 명", example = "product/thumbnail_test.jpg")
	private String thumbnailFilePhysicalName;

}
