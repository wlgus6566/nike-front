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
public class ImageThumbnailFileDTO {

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

	// 임시
	private String cropImg;
}
