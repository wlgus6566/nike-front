package com.nike.dnp.dto.file;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class File upload dto.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 10. 오전 10:09:12
 * @Description
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class FileResultDTO {

	/**
	 * 파일명
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "fileName", value = "파일 이름")
	private String fileName;

	/**
	 * 파일 물리 경로
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "filePhysicalName", value = "파일 경로")
	private String filePhysicalName;

	/**
	 * 파일 사이즈
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "fileSize", value = "파일 사이즈")
	private Long fileSize;

	/**
	 * 파일 컨텐츠 타입
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "fileContentType", value = "파일 컨텐츠 타입")
	private String fileContentType;

	/**
	 * 파일 확장자
	 */
	@ApiModelProperty(name = "fileExtension", value = "파일 확장자")
	private String fileExtension;

	/**
	 * 썸네일 파일 이름
	 */
	@ApiModelProperty(name = "thumbnailFileName", value = "썸네일 파일 이름")
	private String thumbnailFileName;


	/**
	 * 썸네일 파일 물리 경로
	 */
	@ApiModelProperty(name = "thumbnailPhysicalName", value = "썸네일 파일 물리 경로")
	private String thumbnailPhysicalName;


	/**
	 * 썸네일 파일 사이즈
	 */
	@ApiModelProperty(name = "thumbnailSize", value = "썸네일 파일 사이즈")
	private Long thumbnailSize;


	/**
	 * 디테일 썸네일 파일 이름
	 */
	@ApiModelProperty(name = "detailThumbnailFileName", value = "디테일 썸네일 파일 이름")
	private String detailThumbnailFileName;


	/**
	 * 디테일 썸네일 파일 물리 경로
	 */
	@ApiModelProperty(name = "detailThumbnailPhysicalName", value = "디테일 썸네일 파일 물리 경로")
	private String detailThumbnailPhysicalName;


	/**
	 * 디테일 썸네일 파일 사이즈
	 */
	@ApiModelProperty(name = "detailThumbnailSize", value = "디테일 썸네일 파일 사이즈")
	private Long detailThumbnailSize;


}
