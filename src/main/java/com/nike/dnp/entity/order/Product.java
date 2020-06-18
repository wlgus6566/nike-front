package com.nike.dnp.entity.order;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * Product Entity
 *
 * @author [윤태호]
 * @Description Product Entity 작성
 * @history [윤태호] [2020.06.17] [최초 작성]
 * @since 2020.06.17
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_PRODUCT")
public class Product extends BaseTimeEntity {

	/**
	 * 상품 시퀀스
	 * @author [윤태호]
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GOODS_SEQ")
	@ApiModelProperty(name="goodsSeq", value="상품 시퀀스")
	private long goodsSeq;


	/**
	 * 카테고리 1 코드
	 * @author [윤태호]
	 */
	@Column(name = "CATEGORY1_CODE")
	@ApiModelProperty(name="category1Code",value = "카테고리1 공통코드", required = true)
	private String category1Code;


	/**
	 * 카테고리 2 코드
	 * @author [윤태호]
	 */
	@Column(name = "CATEGORY2_CODE")
	@ApiModelProperty(name = "category2Code", value = "카테고리2 공통코드", required = true)
	private String category2Code;

	/**
	 * 카테고리 3 코드
	 * @author [윤태호]
	 */
	@Column(name = "CATEGORY3_CODE")
	@ApiModelProperty(name = "category3Code", value = "카테고리3 공통코드", required = true)
	private String category3Code;

	/**
	 * 에이젼시 시퀀스
	 * @author [윤태호]
	 */
	@Column(name = "AGENCY_SEQ")
	@ApiModelProperty(name = "agencySeq", value = "에이젼시 시퀀스",required = true)
	private long agencySeq;

	/**
	 *
	 * 이미지 파일명
	 * @author [윤태호]
	 */
	@Column(name = "IMAGE_FILE_NAME")
	@ApiModelProperty(name = "imageFileName", value = "이미지 파일명", required = true)
	private String imageFileName;

	/**
	 *
	 * 이미지 파일 사이즈
	 * @author [윤태호]
	 */
	@Column(name = "IMAGE_FILE_SIZE")
	@ApiModelProperty(name = "imageFileSize", value = "이미지 파일 사이즈", required = true)
	private String imageFileSize;

	/**
	 * 이미지 파일 물리 파일명
	 * @author [윤태호]
	 */
	@Column(name = "IMAGE_FILE_PHYSICAL_NAME")
	@ApiModelProperty(name = "imageFilePhysicalName", value = "이미지 파일 물리 파일명", required = true)
	private String imageFilePhysicalName;

	/**
	 * 썸네일 파일명
	 * @author [윤태호]
	 */
	@Column(name = "THUMBNAIL_FILE_NAME")
	@ApiModelProperty(name = "thumbnailFileName", value ="썸네일 파일명", required = true)
	private String thumbnailFileName;


	/**
	 * 썸네일 파일 사이즈
	 * @author [윤태호]
	 */
	@Column(name = "THUMBNAIL_FILE_SIZE")
	@ApiModelProperty(name = "thumbnailFileSize", value = "썸네일 파일 사이즈", required = true)
	private String thumbnailFileSize;


	/**
	 * 썸네일 파일 물리 파일명
	 * @author [윤태호]
	 */
	@Column(name = "THUMBNAIL_FILE_PHYSICAL_NAME")
	@ApiModelProperty(name = "thumbnailFilePhysicalName", value = "썸네일 파일 물리 파일명", required = true)
	private String thumbnailFilePhysicalName;


	/**
	 * 상품명
	 * @author [윤태호]
	 */
	@Column(name = "GOODS_NAME")
	@ApiModelProperty(name = "goodsName", value = "상품명", required = true)
	private String goodsName;

	/**
	 * 상품설명
	 * @author [윤태호]
	 */
	@Column(name = "GOODS_DESCRIPTION")
	@ApiModelProperty(name = "goodsDescription", value = "상품 설명")
	private String goodsDescription;

	/**
	 * 사이즈
	 * @author [윤태호]
	 */
	@Column(name = "SIZE")
	@ApiModelProperty(name = "size", value = "사이즈")
	private String size;

	/**
	 * 최소 주문 수량
	 * @author [윤태호]
	 */
	@Column(name = "MINIMUM_ORDER_QUANTITY")
	@ApiModelProperty(name = "minimumOrderQuantity", value = "최소 주문 수량", required = true)
	private long minimumOrderQuantity;

	/**
	 * 노출 여부
	 * @author [윤태호]
	 */
	@Column(name = "EXPOSURE_YN")
	@ApiModelProperty(name = "exposureYn", value = "노출_여부")
	private String exposureYn;

	/**
	 * 사용여부
	 * @author [윤태호]
	 */
	@Column(name = "USE_YN")
	@ApiModelProperty(name = "useYn", value = "사용 여부", required = true)
	private String useYn;


}
