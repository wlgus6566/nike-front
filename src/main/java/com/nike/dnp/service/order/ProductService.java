package com.nike.dnp.service.order;

import com.nike.dnp.dto.order.ProductSaveDTO;
import com.nike.dnp.entity.order.Product;
import com.nike.dnp.repository.order.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Product service.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

	/**
	 *
	 */
	public final ProductRepository productRepository;


	/**
	 * 전체 검색(paging)
 	 * @return
	 */
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	/**
	 * 제품 등록
	 * @param productSaveDTO
	 * @return
	 */
	public Product save(final ProductSaveDTO productSaveDTO) {
		final Product product = new Product();
		product.setCategory1Code(productSaveDTO.getCategory1code());
		product.setCategory2Code(productSaveDTO.getCategory2code());
		product.setCategory3Code(productSaveDTO.getCategory3code());
		product.setAgencySeq(productSaveDTO.getAgencySeq());
		product.setExposureYn(productSaveDTO.getExposureYn());
		product.setGoodsName(productSaveDTO.getGoodsName());
		product.setGoodsDescription(productSaveDTO.getGoodsDescription());
		product.setSize(productSaveDTO.getSize());
		product.setMinimumOrderQuantity(productSaveDTO.getMinimumQuantity());

		String originalFileName = productSaveDTO.getOriginalImg().getOriginalFilename();
		originalFileName =  productSaveDTO.getOriginalImg().getName()+originalFileName.substring(originalFileName.lastIndexOf('.'));

		String thumbnailFileName = productSaveDTO.getThumbnailImg().getOriginalFilename();
		thumbnailFileName = productSaveDTO.getThumbnailImg().getName() + thumbnailFileName.substring(thumbnailFileName.lastIndexOf('.'));

		product.setImageFileName(originalFileName);
		product.setImageFilePhysicalName(productSaveDTO.getOriginalImg().getOriginalFilename());
		product.setImageFileSize(String.valueOf(productSaveDTO.getOriginalImg().getSize()));

		product.setThumbnailFileName(thumbnailFileName);
		product.setThumbnailFilePhysicalName(productSaveDTO.getThumbnailImg().getOriginalFilename());
		product.setThumbnailFileSize(String.valueOf(productSaveDTO.getThumbnailImg().getSize()));

		product.setRegisterSeq(productSaveDTO.getRegisterSeq());
		product.setUpdaterSeq(productSaveDTO.getRegisterSeq());
		product.setUseYn(productSaveDTO.getUseYn());

		return productRepository.save(product);

	}
}
