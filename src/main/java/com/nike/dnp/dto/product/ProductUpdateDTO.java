package com.nike.dnp.dto.product;


import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * productSaveDTO
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 21. 오후 3:55:11
 * @Description Product Save DTO 작성
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
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name="exposureYn",value="상태", required = true,example = "Y")
	private String exposureYn;

	/**
	 * 카테고리 2 코드
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name="category2Code",value="구분 2 단계", required = true,example = "NIKE_BY_YOU")
	private String category2Code;

	/**
	 * 카테고리 3 코드
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "category3Code", value = "구분 3 단계", required = true,example = "NIKE_BY_YOU27")
	private String category3Code;

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
	private Long minimumOrderQuantity;

	/**
	 * 사이즈
	 *
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
	@ApiModelProperty(name = "imageFileSize", value = "이미지 파일 사이즈", example = "1000", hidden = true)
	private Long imageFileSize;

	/**
	 * 이미지 파일 물리명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "imageFilePhysicalName", value = "이미지 파일 물리 명", example = "product/update.jpg", hidden = true)
	private String imageFilePhysicalName;


	/**
	 * The Image base 64
	 *
	 * @author [오지훈]
	 */
	@ApiModelProperty(name = "imageBase64", value = "이미지 base64 문자", example = "data:image/jpeg;base64,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" +
			"oooAKKKKACprT/j6i/31/mKhqnq2s22hWn2m6lMEe4KrqpJ3Hp0HtUVI88HHuaU/jXqfZfxB1K4m1qSydwbaDaYlCgYJRSecZrgdZ8VaL4deJNV1ex015QSi3dykRYDqQGIyPpXxxrH7Qfi3WPDOnac+qahHqNrI7S6r9tc" +
			"zXKknCv/ALoIA5PArhte8Uav4mkil1fU7rUXiBWNrqUuVB5IGenSvksNk8oRUZv7j7yeKvse7+PP2p59Tsks/CkF1pmoLcgNcSpFKskeGGFB3ck7SDivN7XRdc+Ifii51nxGJFlZozcGWEw+dhdo27QAMBR0q/4D+H7WZN/" +
			"qkamUjEcDhXXqpDggnng16F7A8V9JRwtOivdR85isfJtwgRWtvHZ20VvCu2KJAijOeAMCpKKK7DwwooooEFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA" +
			"BRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABXmnxY1ueO4h0sLH9nZEuN2Dv3ZYdc4xx6V6XXi2uWmueKtXu51glvIoJmt1kjjACgMSBwB0BpM78HFc/NLZHLqpZgoBYk4AUZJNeoeA/AP2N4dUvjul2CSGEfdwynO8FevPrWx" +
			"oPw80vSYrWSSP7Vewt5gucsvO7I+Xdjjj8q6mhI2xGL5k40xfwxSUUUzywooooEFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRR" +
			"QAUUUUAFFFFABRRRQAVFb2dvZhxbwxwh2Lt5ahdzHqTjqfepaKBhRRRQIKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooo" +
			"oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA//2Q==")
	private String imageBase64;

}
