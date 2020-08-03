package com.nike.dnp.service.product;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.dto.product.ProductResultDTO;
import com.nike.dnp.dto.product.ProductSaveDTO;
import com.nike.dnp.dto.product.ProductSearchDTO;
import com.nike.dnp.dto.product.ProductUpdateDTO;
import com.nike.dnp.entity.product.Product;
import com.nike.dnp.repository.goodsbasket.GoodsBasketRepository;
import com.nike.dnp.repository.product.ProductRepository;
import com.nike.dnp.repository.wishlist.WishListRepository;
import com.nike.dnp.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;


/**
 * The Class Product service.
 *
 * @author [윤태호]
 * @since 2020. 6. 23. 오후 3:24:39
 * @implNote
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

	public final WishListRepository wishListRepository;

	public final GoodsBasketRepository goodsBasketRepository;

	/**
	 * 제품 관리 리스트 페이징
	 *
	 * @param productSearchDTO the product search dto
	 * @return the page
	 * @author [윤태호]
	 * @since 2020. 6. 23. 오후 3:24:30
	 * @implNote
	 */
	public Page<ProductResultDTO> findPagesProduct(final ProductSearchDTO productSearchDTO) {
		log.info("ProductService.findPagesProduct");
		return productRepository.findPagesProduct(
				productSearchDTO,
				PageRequest.of(productSearchDTO.getPage(), productSearchDTO.getSize(), Sort.by("goodsSeq").descending()
				));
	}

	/**
	 * 상품 상세 조회
	 *
	 * @param goodsSeq the goods seq
	 * @return the optional
	 * @author [윤태호]
	 * @since 2020. 6. 24. 오전 11:39:06
	 * @implNote
	 */
	public Product findByGoodsSeq(final Long goodsSeq) {
		log.info("ProductService.findByGoodsSeq");
		return productRepository.findByGoodsSeq(goodsSeq );	}

	/**
	 * 제품 등록
	 *
	 * @param productSaveDTO the product save dto
	 * @return product
	 * @author [윤태호]`
	 * @since 2020. 6. 23. 오후 3:24:48
	 * @implNote
	 */
	@Transactional
	public Product save(final ProductSaveDTO productSaveDTO) {
		log.info("ProductService.save");
		final Product product = new Product();
		product.setCategory2Code(productSaveDTO.getCategory2Code());
		product.setCategory3Code(productSaveDTO.getCategory3Code());
		product.setAgencySeq(productSaveDTO.getAgencySeq());
		product.setExposureYn(productSaveDTO.getExposureYn());
		product.setGoodsName(productSaveDTO.getGoodsName());
		product.setGoodsDescription(productSaveDTO.getGoodsDescription());
		product.setSize(productSaveDTO.getSize());
		product.setUnitPrice(productSaveDTO.getUnitPrice());
		product.setMinimumOrderQuantity(productSaveDTO.getMinimumOrderQuantity());

		if(!ObjectUtils.isEmpty(productSaveDTO.getImageBase64()) &&
			productSaveDTO.getImageBase64().contains("base64")){
			final FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(ServiceCode.FileFolderEnumCode.PRODUCT.getFolder(), productSaveDTO.getImageBase64());

			product.setImageFileName(productSaveDTO.getImageFileName());
			product.setImageFileSize(String.valueOf(fileResultDTO.getFileSize()));
			product.setImageFilePhysicalName(fileResultDTO.getFilePhysicalName());
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
	 * @since 2020. 6. 24. 오후 4:42:09
	 * @implNote
	 */
	@Transactional
	public Product update(final ProductUpdateDTO productUpdateDTO) {
		log.info("ProductService.update");
		final Optional<Product> optionalProduct = productRepository.findById(productUpdateDTO.getGoodsSeq());
		final Product product = optionalProduct.orElse(new Product());
		product.setExposureYn(productUpdateDTO.getExposureYn());
		product.setCategory2Code(productUpdateDTO.getCategory2Code());
		product.setCategory3Code(productUpdateDTO.getCategory3Code());
		product.setAgencySeq(productUpdateDTO.getAgencySeq());
		product.setGoodsName(productUpdateDTO.getGoodsName());
		product.setGoodsDescription(productUpdateDTO.getGoodsDescription());
		product.setMinimumOrderQuantity(productUpdateDTO.getMinimumOrderQuantity());
		product.setUnitPrice(productUpdateDTO.getUnitPrice());
		if(!ObjectUtils.isEmpty(productUpdateDTO.getImageBase64()) && productUpdateDTO.getImageBase64().contains("base64")){
			final FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(ServiceCode.FileFolderEnumCode.PRODUCT.getFolder(), productUpdateDTO.getImageBase64());
			product.setImageFileName(productUpdateDTO.getImageFileName());
			product.setImageFileSize(String.valueOf(fileResultDTO.getFileSize()));
			product.setImageFilePhysicalName(fileResultDTO.getFilePhysicalName());
		}
		return productRepository.save(product);
	}


	/**
	 * 상품 삭제
	 *
	 * @param productUpdateDTO the product update dto
	 * @return the product
	 * @author [윤태호]
	 * @since 2020. 6. 24. 오후 5:23:04
	 * @implNote
	 */
	@Transactional
	public Optional<Product> delete(final ProductUpdateDTO productUpdateDTO) {
		log.info("ProductService.delete");
		final Optional<Product> product = productRepository.findById(productUpdateDTO.getGoodsSeq());
		product.ifPresent(value -> {
			value.delete(productUpdateDTO);
			goodsBasketRepository.deleteByGoodsSeq(value.getGoodsSeq());
			wishListRepository.deleteByGoodsSeq(value.getGoodsSeq());
		});
		return product;
	}


	/**
	 * 다수 상품 조회
	 *
	 * @param goodsSeqList the goods seq list
	 * @return the list
	 * @author [윤태호]
	 * @since 2020. 6. 25. 오후 2:37:34
	 * @implNote
	 */
	@Transactional
	public List<Product> findBySearchId(final List<Long> goodsSeqList){
		log.info("ProductService.findBySearchId");
		return productRepository.findAllById(goodsSeqList);
	}

	/**
	 * 다수 상품 삭제
	 *
	 * @param productList      the product list
	 * @param productUpdateDTO the product update dto
	 * @return the optional
	 * @author [윤태호]
	 * @since 2020. 6. 26. 오후 3:14:32
	 * @implNote
	 */
	@Transactional
	public boolean deleteArray(final List<Product> productList,
										 final ProductUpdateDTO productUpdateDTO) {
		log.info("ProductService.deleteArray");
		productList.forEach(product -> {
			product.delete(productUpdateDTO);
			goodsBasketRepository.deleteByGoodsSeq(product.getGoodsSeq());
			wishListRepository.deleteByGoodsSeq(product.getGoodsSeq());
		});
		return true;
	}
}
