package com.nike.dnp.controller.contents;

import com.nike.dnp.dto.file.FileUploadDTO;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Slf4j
@RequiredArgsConstructor
@RestController
@ApiIgnore
public class FileController {

	final ResponseService responseService;


	@GetMapping("/api/download")
	public ResponseEntity<Resource> download() throws IOException {
//		Path path = Paths.get("d:/test/1.jpg");
//		Path path = Paths.get("d:/test/Jinny.zip");

		// 테스트 용 로컬에 맞게 수정해야 함
		Path path = Paths.get("d:/test/erwin73.zip");

		HttpHeaders headers = new HttpHeaders();

//		String contentType = Files.probeContentType(path);
//		headers.add(HttpHeaders.CONTENT_TYPE,contentType); //이미지일경우 바로 뷰

		headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + path.getFileName().toString());
		headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(path.toFile().length()));

		Resource resource = new InputStreamResource(Files.newInputStream(path));
		return new ResponseEntity<>(resource,headers,HttpStatus.OK);

	}


	@PostMapping(value = "/api/upload",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public CommonResult upload(@RequestBody FileUploadDTO fileUploadDTO) throws IOException {
		//log.debug("fileStr {}", fileUploadDTO.getCropImg());
		BufferedImage image = null;
		String base64Str = fileUploadDTO.getCropImg().split(",")[1];
		String info = fileUploadDTO.getCropImg().split(",")[0];
		fileUploadDTO.getCropImg().split(",")[0].substring(fileUploadDTO.getCropImg().indexOf("/") + 1, fileUploadDTO.getCropImg().indexOf(";"));
		byte[] imageByte;
		try{
			imageByte = DatatypeConverter.parseBase64Binary(fileUploadDTO.getCropImg().split(",")[1]);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			bis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
				ImageIO.write(image, "png", new File("d:/test/test.png"));
		return responseService.getSuccessResult();

	}
}
