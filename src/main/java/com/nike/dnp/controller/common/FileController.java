package com.nike.dnp.controller.common;

import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.dto.file.FileUploadDTO;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@Api(description = "파일", tags = "FILE")
public class FileController {

	private final ResponseService responseService;

	private static final String BASIC_CHARACTER = "## Request ## \n" + "[하위 Parameters 참조] \n" + "## Request ## \n" + "[하위 Model 참조]\n\n";

	@GetMapping("/api/download")
	@ApiIgnore
	public ResponseEntity<Resource> download() throws IOException {
//		Path path = Paths.get("d:/test/1.jpg");
//		Path path = Paths.get("d:/test/Jinny.zip");

		// 테스트 용 로컬에 맞게 수정해야 함
		final Path path = Paths.get("d:/test/erwin73.zip");

		final HttpHeaders headers = new HttpHeaders();

//		String contentType = Files.probeContentType(path);
//		headers.add(HttpHeaders.CONTENT_TYPE,contentType); //이미지일경우 바로 뷰

		headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + path.getFileName().toString());
		headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(path.toFile().length()));

		final Resource resource = new InputStreamResource(Files.newInputStream(path));
		return new ResponseEntity<>(resource,headers,HttpStatus.OK);

	}

	@ApiOperation(value = "파일 업로드", notes = BASIC_CHARACTER)
	@PostMapping(value = "/api/upload",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public SingleResult<FileResultDTO> upload(final FileUploadDTO fileUploadDTO,
							   @ApiParam(name = "uploadFile", value = "파일업로드") final MultipartFile uploadFile) {

		final FileResultDTO fileResultDTO = fileUpload(fileUploadDTO);
		// TODO [YTH] s3 파일 업로드
		return responseService.getSingleResult(fileResultDTO);

	}


	@ApiOperation(value = "파일 업로드 리스트", notes = BASIC_CHARACTER)
	@PostMapping(value = "/api/uploadList", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public SingleResult<List<FileResultDTO>> uploadList(final FileUploadDTO fileUploadDTO, @ApiParam(name = "uploadFileList", value = "파일업로드") final List<MultipartFile> uploadFileList) {

		final List<FileResultDTO> resultList = new ArrayList<>();

		fileUploadDTO.getUploadFileList().forEach(multipartFile -> {
			final FileResultDTO fileResultDTO = fileUpload(fileUploadDTO);
			// TODO [YTH] s3 파일 업로드
			resultList.add(fileResultDTO);
		});

		return responseService.getSingleResult(resultList);
	}

	private FileResultDTO fileUpload(final FileUploadDTO fileUploadDTO) {
		FileResultDTO fileResultDTO = null;
		try{
			fileResultDTO = FileUtil.fileTempSaveAndImageResize(fileUploadDTO.getUploadFile(), 240);
		}catch(InterruptedException | IOException e){
			// 리사이즈 문제
			throw (CodeMessageHandleException)new CodeMessageHandleException(ErrorEnumCode.FileError.FILE_COPY_ERROR.name(), ErrorEnumCode.FileError.FILE_COPY_ERROR.getMessage());
		}
		return fileResultDTO;
	}


}
