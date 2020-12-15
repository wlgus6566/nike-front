package com.nike.dnp.service.order;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.dto.file.FileUploadDTO;
import com.nike.dnp.dto.order.OrderProductFileSaveDTO;
import com.nike.dnp.entity.order.OrderProductFile;
import com.nike.dnp.repository.order.OrderProductFileRepository;
import com.nike.dnp.util.FileUtil;
import com.nike.dnp.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * The Class Order produc file service.
 *
 * @author [이소정]
 * @since 2020. 12. 14. 오후 6:43:01
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderProductFileService {

	/**
	 * The Order product file repository
	 */
	private final OrderProductFileRepository orderProductFileRepository;

	/**
	 * The Img url
	 */
	@Value("${nike.url.pc.domain:}")
	private String imgUrl;

	/**
	 * Save order product file order product file.
	 *
	 * @param orderProductFileSaveDTO the order product file save dto
	 * @return the order product file
	 * @author [이소정]
	 * @implNote 주문_상품_파일 저장
	 * @since 2020. 12. 14. 오후 6:54:14
	 */
	@Transactional
	public OrderProductFile saveOrderProductFile(final OrderProductFileSaveDTO orderProductFileSaveDTO) {
		log.info("OrderProductFileService.saveOrderProductFile");
		return orderProductFileRepository.save(
				OrderProductFile.builder().orderProductFileSaveDTO(orderProductFileSaveDTO).build()
		);
	}

	/**
	 * Upload file file result dto.
	 *
	 * @param fileUploadDTO the file upload dto
	 * @param uploadFile    the upload file
	 * @return the file result dto
	 * @throws IOException the io exception
	 * @author [이소정]
	 * @implNote 파일 업로드
	 * @since 2020. 12. 14. 오후 8:49:25
	 */
	public FileResultDTO uploadFile(
			final FileUploadDTO fileUploadDTO
			, final MultipartFile uploadFile
	) throws IOException {
		log.info("OrderProductFileService.uploadEditorImages");
		FileResultDTO fileResultDTO = FileUtil.fileSave(uploadFile, ServiceCode.FileFolderEnumCode.ORDER_PRODUCT.getFolder(), true, null);
		S3Util.upload(fileResultDTO, "N");
		return fileResultDTO;
	}
}
