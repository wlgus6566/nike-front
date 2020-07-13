package com.nike.dnp.dto.file;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class File upload dto.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 10. 오전 10:09:12
 * @Description
 */
@Getter
@Setter
public class FileResultDTO {

	/**
	 * 파일명
	 *
	 * @author [윤태호]
	 */
	private String fileName;

	/**
	 * 파일 물리 경로
	 *
	 * @author [윤태호]
	 */
	private String filePhysicalName;

	/**
	 * 파일 사이즈
	 *
	 * @author [윤태호]
	 */
	private Long fileSize;

	/**
	 * 파일 컨텐츠 타입
	 *
	 * @author [윤태호]
	 */
	private String fileContentType;

	/**
	 * 파일 확장자
	 */
	private String fileExtension;

	/**
	 * 썸네일 파일 이름
	 */
	private String thumbnailFileName;


	/**
	 * 썸네일 파일 물리 경로
	 */
	private String thumbnailPhysicalName;


	/**
	 * 썸네일 파일 사이즈
	 */
	private Long thumbnailSize;




}
