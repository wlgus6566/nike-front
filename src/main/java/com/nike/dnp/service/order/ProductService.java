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
import org.springframework.util.ObjectUtils;
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
	public Page<Product> findPagesProduct(final ProductSearchDTO productSearchDTO) {
		return productRepository.findPagesProduct(
				productSearchDTO,
				PageRequest.of(productSearchDTO.getPage(), productSearchDTO.getSize(), Sort.by("goodsSeq").descending()
				));
	}

	/**
	 * 상품 상세 조회
	 *
	 * @param goodsSeq      the goods seq
	 * @param category1Code the category 1 code
	 * @return the optional
	 * @author [윤태호]
	 * @CreatedOn 2020. 6. 24. 오전 11:39:06
	 * @Description
	 */
	@Transactional
	public Product findByGoodsSeqAndCategory1Code(final Long goodsSeq,final String category1Code) {
		return productRepository.findByGoodsSeqAndCategory1Code(goodsSeq, category1Code);
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

		if(!ObjectUtils.isEmpty(productSaveDTO.getOriginalImg())){
			final String originalFileName = StringUtils.getFilename(productSaveDTO.getOriginalImg().getOriginalFilename());
			product.setImageFileName(originalFileName);
			product.setImageFilePhysicalName(productSaveDTO.getOriginalImg().getOriginalFilename());
			product.setImageFileSize(String.valueOf(productSaveDTO.getOriginalImg().getSize()));
		}

		if(!ObjectUtils.isEmpty(productSaveDTO.getThumbnailImg())){
			final String thumbnailFileName = StringUtils.getFilename(productSaveDTO.getThumbnailImg().getOriginalFilename());
			product.setThumbnailFileName(thumbnailFileName);
			product.setThumbnailFilePhysicalName(productSaveDTO.getThumbnailImg().getOriginalFilename());
			product.setThumbnailFileSize(String.valueOf(productSaveDTO.getThumbnailImg().getSize()));
		}

		product.setRegisterSeq(productSaveDTO.getRegisterSeq());
		product.setUpdaterSeq(productSaveDTO.getRegisterSeq());
		product.setUseYn(productSaveDTO.getUseYn());

		return productRepository.save(product);

	}

	/**
	 * 상품 수정
	 *
	 * @param productUpdateDTO the product update dto
	 * @return the product
	 * @author [윤태호]
	 * @CreatedOn 2020. 6. 24. 오후 4:42:09
	 * @Description
	 */
	@Transactional
	public Product update(final ProductUpdateDTO productUpdateDTO) {
		final Optional<Product> product = productRepository.findById(productUpdateDTO.getGoodsSeq());
		product.ifPresent(value ->  value.update(productUpdateDTO));
		return product.get();
	}


	/**
	 * 상품 삭제
	 *
	 * @param productUpdateDTO the product update dto
	 * @return the product
	 * @author [윤태호]
	 * @CreatedOn 2020. 6. 24. 오후 5:23:04
	 * @Description
	 */
	@Transactional
	public Product delete(final ProductUpdateDTO productUpdateDTO) {
		final Optional<Product> product = productRepository.findById(productUpdateDTO.getGoodsSeq());
		product.ifPresent(value -> value.delete(productUpdateDTO));
		return product.get();
	}
}
