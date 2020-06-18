package com.nike.dnp.dto.order;


import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
public class ProductSaveDTO extends BasicDTO {


	/**
	 * 카테고리 1 코드
	 * @author [윤태호]
	 */
	@ApiModelProperty(hidden = true)
	private String category1code;

	/**
	 * 상태
	 * @author [윤태호]
	 */
	@ApiModelProperty(name="exposureYn",value="상태")
	private String exposureYn;

	/**
	 * 카테고리 2 코드
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name="category2code",value="구분 2 단계",required = true)
	private String category2code;

	/**
	 * 카테고리 3 코드
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "category3code", value = "구분 3 단계", required = true)
	private String category3code;

	/**
	 * 에이전시 시퀀스
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "agencySeq", value = "에이전시 시퀀스", required = true)
	private long agencySeq;

	/**
	 * 상품 명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "goodsName", value = "상품 명", required = true)
	private String goodsName;

	/**
	 * 추가 설명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "goodsDescription", value = "추가설명", required = true)
	private String goodsDescription;

	/**
	 * 최소 주문 수량
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "minimumOrderQuantity", value = "최소 주문 수량", required = true)
	private int minimumQuantity;

	/**
	 * 사이즈
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "size", value = "사이즈")
	private String size;

	/**
	 * 원본 이미지
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "originalImg", value = "원본 이미지")
	private MultipartFile originalImg;

	/**
	 * 썸네일 이미지
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "thumbnailImg", value = "썹네일 이미지")
	private MultipartFile thumbnailImg;


}
