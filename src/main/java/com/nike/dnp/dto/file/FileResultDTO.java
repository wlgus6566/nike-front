package com.nike.dnp.dto.file;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.File;

/**
 * The Class File upload dto.
 *
 * @author [윤태호]
 * @implNote
 * @since 2020. 7. 10. 오전 10:09:12
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
	 * 새로운 파일 이름
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(hidden = true)
	private String fileNewName;

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
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "fileExtension", value = "파일 확장자", example = "JPG")
	private String fileExtension;

	/**
	 * 썸네일 파일 이름
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "thumbnailFileName", value = "썸네일 파일 이름")
	private String thumbnailFileName;


	/**
	 * 썸네일 파일 물리 경로
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "thumbnailFilePhysicalName", value = "썸네일 파일 물리 경로")
	private String thumbnailFilePhysicalName;


	/**
	 * 썸네일 파일 사이즈
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "thumbnailFileSize", value = "썸네일 파일 사이즈")
	private Long thumbnailFileSize;


	/**
	 * 디테일 썸네일 파일 이름
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "detailThumbnailFileName", value = "디테일 썸네일 파일 이름")
	private String detailThumbnailFileName;

	/**
	 * 디테일 썸네일 파일 사이즈
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "detailThumbnailFileSize", value = "디테일 썸네일 파일 물리 경로")
	private Long detailThumbnailFileSize;


	/**
	 * 디테일 썸네일 파일 물리 경로
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "detailThumbnailFilePhysicalName", value = "디테일 썸네일 파일 사이즈")
	private String detailThumbnailFilePhysicalName;

	/**
	 * The Thumb file
	 */
	@ApiModelProperty(name = "thumbFile", value = "썸네일 파일", hidden = true)
	private File thumbFile;


}
