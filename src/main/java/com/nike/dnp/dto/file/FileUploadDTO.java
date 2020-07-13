package com.nike.dnp.dto.file;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileUploadDTO {

	@ApiParam(hidden = true)
	private MultipartFile uploadFile;
}
