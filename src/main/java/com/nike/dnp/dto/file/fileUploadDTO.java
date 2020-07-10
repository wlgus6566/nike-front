package com.nike.dnp.dto.file;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class fileUploadDTO {

	private MultipartFile file;
}
