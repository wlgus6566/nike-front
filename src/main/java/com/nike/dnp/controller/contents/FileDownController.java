package com.nike.dnp.controller.contents;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Slf4j
@RequiredArgsConstructor
@RestController
public class FileDownController {

	final BCryptPasswordEncoder passwordEncoder;

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
}
