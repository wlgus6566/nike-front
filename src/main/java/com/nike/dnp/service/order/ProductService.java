package com.nike.dnp.service.order;

import com.nike.dnp.dto.order.ProductSaveDTO;
import com.nike.dnp.dto.order.ProductSearchDTO;
import com.nike.dnp.dto.order.ProductUpdateDTO;
import com.nike.dnp.entity.order.Product;
import com.nike.dnp.repository.order.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;


/**
 * The Class Product service.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 6. 23. 오후 3:24:39
 * @Description
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

	/**
	 * The Product repository
	 *
	 * @author [윤태호]
	 */
	public final ProductRepository productRepository;

	/**
	 * Find all pages page.
	 *
	 * @param productSearchDTO the product search dto
	 * @return the page
	 * @author [윤태호]
	 * @CreatedOn 2020. 6. 23. 오후 3:24:30
	 * @Description
	 */
	public Page<Product> findAllPages(ProductSearchDTO productSearchDTO) {
		return productRepository.findAllPages(
				productSearchDTO,
				PageRequest.of(productSearchDTO.getPage(), productSearchDTO.getSize(), Sort.by("goodsSeq").descending()
				));
	}

	/**
	 * 제품 등록
	 *
	 * @param productSaveDTO the product save dto
	 * @return product
	 * @author [윤태호]`
	 * @CreatedOn 2020. 6. 23. 오후 3:24:48
	 * @Description
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
		product.setUnitPrice(productSaveDTO.getUnitPrice());
		product.setMinimumOrderQuantity(productSaveDTO.getMinimumQuantity());

		String originalFileName = StringUtils.getFilename(productSaveDTO.getOriginalImg().getOriginalFilename());
		String thumbnailFileName = StringUtils.getFilename(productSaveDTO.getThumbnailImg().getOriginalFilename());

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

	@Transactional
	public Product update(ProductUpdateDTO productUpdateDTO) {
		Optional<Product> product = productRepository.findById(productUpdateDTO.getGoodsSeq());
		product.ifPresent(value ->  value.update(productUpdateDTO));
		return product.get();
	}
}
