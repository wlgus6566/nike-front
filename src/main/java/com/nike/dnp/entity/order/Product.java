package com.nike.dnp.entity.order;

import com.nike.dnp.dto.order.ProductUpdateDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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
@DynamicUpdate
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
	@ApiModelProperty(name = "size", value = "사이즈",hidden = true)
	private String size;

	/**
	 * 단가
	 *
	 * @author [윤태호]
	 */
	@Column(name = "UNIT_PRICE")
	@ApiModelProperty(name = "unitPrice", value = "단가")
	private String unitPrice;

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


	/**
	 * 수정
	 *
	 * @param productUpdateDTO the product update dto
	 * @author [윤태호]
	 * @CreatedOn 2020. 6. 23. 오후 5:52:24
	 * @Description
	 */

	public void update(ProductUpdateDTO productUpdateDTO) {
		if(!ObjectUtils.isEmpty(productUpdateDTO.getCategory2code())){
			this.category2Code = productUpdateDTO.getCategory2code();
		}
		if(!ObjectUtils.isEmpty(productUpdateDTO.getCategory3code())){
			this.category3Code = productUpdateDTO.getCategory3code();
		}
		if(!ObjectUtils.isEmpty(productUpdateDTO.getAgencySeq()) && productUpdateDTO.getAgencySeq() > 0){
			this.agencySeq = productUpdateDTO.getAgencySeq();
		}
		if(!ObjectUtils.isEmpty(productUpdateDTO.getGoodsName())){
			this.goodsName = productUpdateDTO.getGoodsName();
		}
		if(!ObjectUtils.isEmpty(productUpdateDTO.getExposureYn())){
			this.exposureYn = productUpdateDTO.getExposureYn();
		}
		if(!ObjectUtils.isEmpty(productUpdateDTO.getGoodsDescription())){
			this.goodsDescription = productUpdateDTO.getGoodsDescription();
		}
		if(!ObjectUtils.isEmpty(productUpdateDTO.getUnitPrice())){
			this.unitPrice = unitPrice;
		}
		if(!ObjectUtils.isEmpty(productUpdateDTO.getMinimumQuantity())){
			this.minimumOrderQuantity = productUpdateDTO.getMinimumQuantity();
		}
		if(!ObjectUtils.isEmpty(productUpdateDTO.getOriginalImg())){
			this.imageFileName = StringUtils.getFilename(productUpdateDTO.getOriginalImg().getOriginalFilename());
			this.imageFileSize = String.valueOf(productUpdateDTO.getOriginalImg().getSize());
			this.imageFilePhysicalName  = productUpdateDTO.getOriginalImg().getOriginalFilename();
		}

		if(!ObjectUtils.isEmpty(productUpdateDTO.getThumbnailImg())){
			this.thumbnailFileName = StringUtils.getFilename(productUpdateDTO.getThumbnailImg().getOriginalFilename());
			this.thumbnailFileSize = String.valueOf(productUpdateDTO.getThumbnailImg().getSize());
			this.thumbnailFilePhysicalName = productUpdateDTO.getThumbnailImg().getOriginalFilename();
		}

		if(!ObjectUtils.isEmpty(productUpdateDTO.getUseYn())){
			this.useYn = productUpdateDTO.getUseYn();
		}

		setUpdaterSeq(productUpdateDTO.getUpdaterSeq());




	}
}
