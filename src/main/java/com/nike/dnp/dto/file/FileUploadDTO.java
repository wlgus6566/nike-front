package com.nike.dnp.dto.file;

import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * The Class File upload dto.
 *
 * @author [오지훈]
 * @since 2020. 7. 21. 오후 3:54:36
 * @implNote
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class FileUploadDTO {

	/**
	 * 멀티 파트 파일
	 *
	 * @author [오지훈]
	 */
	@ApiParam(hidden = true)
	private MultipartFile uploadFile;

	/**
	 * 멀티 파트 파일 리스트
	 *
	 * @author [오지훈]
	 */
	@ApiParam(hidden = true)
	private List<MultipartFile> uploadFileList;
}
