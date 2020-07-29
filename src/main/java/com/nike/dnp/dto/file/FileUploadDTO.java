package com.nike.dnp.dto.file;

import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * The Class File upload dto.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 21. 오후 3:54:36
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class FileUploadDTO {

	/**
	 * The Upload file
	 *
	 * @author [오지훈]
	 */
	@ApiParam(hidden = true)
	private MultipartFile uploadFile;

	/**
	 * The Upload file list
	 *
	 * @author [오지훈]
	 */
	@ApiParam(hidden = true)
	private List<MultipartFile> uploadFileList;
}
