package com.nike.dnp.controller.common;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.file.FileCheckDTO;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.dto.file.FileUploadDTO;
import com.nike.dnp.exception.FileHandleException;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.util.FileUtil;
import com.nike.dnp.util.S3Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
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
	public SingleResult<FileResultDTO> upload(
			final FileUploadDTO fileUploadDTO
			, @ApiParam(name = "uploadFile", value = "파일업로드") final MultipartFile uploadFile
			, @RequestParam @ApiParam(name = "menuCode", value = "메뉴코드(default:null/주문:ORDER/공지사항:NOTICE)") final String menuCode
	) throws IOException {
		log.info("FileController.upload");

		String folder = ServiceCode.FileFolderEnumCode.TEMP.getFolder();
		String downloadYn = "Y";
		boolean resize = true;
		String privateYn = "Y";
		if (!StringUtils.isEmpty(menuCode) && menuCode.equals("order")) {
			folder = ServiceCode.FileFolderEnumCode.ORDER_PRODUCT.getFolder();
			downloadYn = "N";
			privateYn = "N";
		} else if (!StringUtils.isEmpty(menuCode) && menuCode.equals("notice")) {
			privateYn = "N";
			resize = false;
		}
		final FileResultDTO fileResultDTO = fileUpload(fileUploadDTO, folder, resize);
		S3Util.upload(fileResultDTO, privateYn, downloadYn);
		return responseService.getSingleResult(fileResultDTO);
	}

//	/**
//	 * Upload order product file single result.
//	 *
//	 * @param fileUploadDTO the file upload dto
//	 * @param uploadFile    the upload file
//	 * @return the single result
//	 * @author [이소정]
//	 * @implNote 주문 파일 업로드
//	 * @since 2020. 12. 14. 오후 7:44:13
//	 */
//	@ApiOperation(value = "파일 업로드", notes = BASIC_CHARACTER)
//	@PostMapping(value = "/api/open/publicUpload/{menuCode}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public SingleResult<FileResultDTO> uploadPublicFile (
//			final FileUploadDTO fileUploadDTO
//			, @ApiParam(name = "uploadFile", value = "파일업로드") final MultipartFile uploadFile
//			, @ApiParam(value = "메뉴코드(주문:order / 공지사항:notice)", defaultValue = "ALL") @PathVariable final String menuCode
//	) throws IOException {
//		log.info("FileController.publicUpload");
//
//	}

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
	public SingleResult<List<FileResultDTO>> uploadList(
			final FileUploadDTO fileUploadDTO
			, @ApiParam(name = "uploadFileList", value = "파일업로드") final List<MultipartFile> uploadFileList
	) {
		log.info("FileController.uploadList");
		final List<FileResultDTO> resultList = new ArrayList<>();
		fileUploadDTO.getUploadFileList().forEach(multipartFile -> {
			final FileUploadDTO fileParam = new FileUploadDTO();
			fileParam.setUploadFile(multipartFile);
			final FileResultDTO fileResultDTO = fileUpload(fileParam, ServiceCode.FileFolderEnumCode.TEMP.getFolder(), true);
			S3Util.upload(fileResultDTO, "Y", "Y");
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
	private FileResultDTO fileUpload(final FileUploadDTO fileUploadDTO, final String folder, final boolean resize) {
		log.info("FileController.fileUpload");
		try{
			return FileUtil.fileTempSaveAndImageResize(fileUploadDTO.getUploadFile(), resize, folder);
		} catch(IOException e) {
			throw new FileHandleException();
		}
	}


}
