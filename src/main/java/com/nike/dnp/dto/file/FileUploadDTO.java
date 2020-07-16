package com.nike.dnp.dto.file;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class FileUploadDTO {

	@ApiParam(hidden = true)
	private MultipartFile uploadFile;

	@ApiParam(hidden = true)
	private List<MultipartFile> uploadFileList;
}
