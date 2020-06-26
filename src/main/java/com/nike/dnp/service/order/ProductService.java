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

import java.util.List;
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
		product.setImageFileName(productSaveDTO.getImageFileName());
		product.setImageFileSize(String.valueOf(productSaveDTO.getImageFileSize()));
		product.setImageFilePhysicalName(productSaveDTO.getImageFilePhysicalName());

		product.setThumbnailFileName(productSaveDTO.getThumbnailFileName());
		product.setThumbnailFileSize(String.valueOf(productSaveDTO.getThumbnailFileSize()));
		product.setThumbnailFilePhysicalName(productSaveDTO.getThumbnailFilePhysicalName());

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
	public Optional<Product> update(final ProductUpdateDTO productUpdateDTO) {
		final Optional<Product> product = productRepository.findById(productUpdateDTO.getGoodsSeq());
		product.ifPresent(value ->  value.update(productUpdateDTO));
		return product;
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
	public Optional<Product> delete(final ProductUpdateDTO productUpdateDTO) {
		final Optional<Product> product = productRepository.findById(productUpdateDTO.getGoodsSeq());
		product.ifPresent(value -> value.delete(productUpdateDTO));
		return product;
	}

	/**
	 * 다수 상품 조회
	 *
	 * @param goodsSeqList the goods seq list
	 * @return the list
	 * @author [윤태호]
	 * @CreatedOn 2020. 6. 25. 오후 2:37:34
	 * @Description
	 */
	public List<Product> findBySearchId(final List<Long> goodsSeqList) {
		return productRepository.findAllById(goodsSeqList);
	}
}
