package com.nike.dnp.controller.common;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.dto.file.FileUploadDTO;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.exception.FileHandleException;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.util.FileUtil;
import com.nike.dnp.util.MessageUtil;
import com.nike.dnp.util.S3Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 파일컨트롤러
 *
 * @author [윤태호]
 * @since 2020. 7. 28. 오전 11:08:35
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Api(description = "파일", tags = "FILE")
public class FileController {

	/**
	 * The Response service
	 *
	 * @author [윤태호]
	 */
	private final ResponseService responseService;

	/**
	 * The constant BASIC_CHARACTER
	 *
	 * @author [윤태호]
	 */
	private static final String BASIC_CHARACTER = "## Request ## \n" + "[하위 Parameters 참조] \n" + "## Request ## \n" + "[하위 Model 참조]\n\n";

	/**
	 * 파일 다운로드 테스트
	 *
	 * @return the response entity
	 * @throws IOException the io exception
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 28. 오전 11:08:35
	 */
	@ApiIgnore
	@GetMapping("/api/download")
	public ResponseEntity<Resource> download() throws IOException {
		log.info("FileController.download");
		return FileUtil.s3FileDownload("/test/20200728897000dVPDUIzevI.pptx", "test.pptx");
	}

	/**
	 * 단일 파일 업로드
	 *
	 * @param fileUploadDTO the file upload dto
	 * @param uploadFile    the upload file
	 * @return the single result
	 * @throws IOException the io exception
	 * @author [윤태호]
	 * @implNote 단일 파일 업로드
	 * @since 2020. 7. 28. 오전 11:08:35
	 */
	@ApiOperation(value = "파일 업로드", notes = BASIC_CHARACTER)
	@PostMapping(value = "/api/open/upload",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public SingleResult<FileResultDTO> upload(final FileUploadDTO fileUploadDTO,
							   @ApiParam(name = "uploadFile", value = "파일업로드") final MultipartFile uploadFile) throws IOException {
		log.info("FileController.upload");
		final FileResultDTO fileResultDTO = fileUpload(fileUploadDTO);
		S3Util.upload(fileResultDTO);
		return responseService.getSingleResult(fileResultDTO);
	}

	/**
	 * 리스트 파일 업로드
	 *
	 * @param fileUploadDTO  the file upload dto
	 * @param uploadFileList the upload file list
	 * @return the single result
	 * @author [윤태호]
	 * @implNote 리스트 파일 업로드
	 * @since 2020. 7. 28. 오전 11:08:35
	 */
	@ApiOperation(value = "파일 업로드 리스트", notes = BASIC_CHARACTER)
	@PostMapping(value = "/api/open/uploadList", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public SingleResult<List<FileResultDTO>> uploadList(final FileUploadDTO fileUploadDTO, @ApiParam(name = "uploadFileList", value = "파일업로드") final List<MultipartFile> uploadFileList) {
		log.info("FileController.uploadList");
		final List<FileResultDTO> resultList = new ArrayList<>();
		fileUploadDTO.getUploadFileList().forEach(multipartFile -> {
			final FileUploadDTO fileParam = new FileUploadDTO();
			fileParam.setUploadFile(multipartFile);
			final FileResultDTO fileResultDTO = fileUpload(fileParam);
			S3Util.upload(fileResultDTO);
			resultList.add(fileResultDTO);
		});

		return responseService.getSingleResult(resultList);
	}

	/**
	 * 파일 업로드
	 *
	 * @param fileUploadDTO the file upload dto
	 * @return the file result dto
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 28. 오전 11:08:35
	 */
	private FileResultDTO fileUpload(final FileUploadDTO fileUploadDTO) {
		log.info("FileController.fileUpload");
		try{
			return FileUtil.fileTempSaveAndImageResize(fileUploadDTO.getUploadFile());
			//fileResultDTO = FileUtil.fileSave(fileUploadDTO.getUploadFile(),"temp");
		} catch(IOException e) {
			throw new FileHandleException();
		}
	}


}
