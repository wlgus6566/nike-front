package com.nike.dnp.dto.file;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * The Class File check dto.
 *
 * @author [이소정]
 * @since 2020. 12. 14. 오후 8:22:41
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class FileCheckDTO {

	/**
	 * 파일명
	 *
	 * @author [이소정]
	 */
	@ApiModelProperty(name = "fileName", value = "파일 이름")
	private String originalFileName;

	/**
	 * 파일명
	 *
	 * @author [이소정]
	 */
	@ApiModelProperty(name = "folderParam", value = "파일 경로")
	private String folderParam;

	private String folder;

	/**
	 * contentType
	 *
	 * @author [이소정]
	 */
	@ApiModelProperty(name = "contentType", value = "파일타입")
	private String contentType;

	/**
	 * 확장자
	 *
	 * @author [이소정]
	 */
	@ApiModelProperty(name = "extension", value = "extension")
	private String extension;

	private MultipartFile uploadFile;
}
