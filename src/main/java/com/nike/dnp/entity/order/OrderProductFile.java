package com.nike.dnp.entity.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nike.dnp.dto.order.OrderProductFileSaveDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.entity.product.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * The Class Order product file.
 *
 * @author [이소정]
 * @since 2020. 12. 14. 오후 6:33:22
 */
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "TB_ORDER_PRODUCT_FILE")
public class OrderProductFile extends BaseTimeEntity {

    /**
     * 주문 상품 파일 시퀀스
     *
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_GOODS_FILE_SEQ")
    @ApiModelProperty(name = "orderGoodsFileSeq" , value = "주문 상품 파일 시퀀스")
    private Long orderGoodsFileSeq;


    /**
     * 주문 상품 시퀀스
     *
     * @author [이소정]
     */
    @Column(name = "ORDER_GOODS_SEQ")
    @ApiModelProperty(name = "orderGoodsSeq" , value = "주문 상품 시퀀스")
    private Long orderGoodsSeq;

    /**
     * 파일 컨텐츠 타입
     *
     * @author [이소정]
     */
    @Column(name = "FILE_CONTENT_TYPE")
    @ApiModelProperty(name = "fileContentType", value = "파일 컨텐츠 타입", example = "image/jpeg")
    private String fileContentType;

    /**
     * 파일 확장자
     *
     * @author [이소정]
     */
    @Column(name = "FILE_EXTENSION")
    @ApiModelProperty(name = "fileExtension", value = "파일 확장자", example = "JPG")
    private String fileExtension;

    /**
     * 파일 명
     *
     * @author [이소정]
     */
    @Column(name = "FILE_NAME")
    @ApiModelProperty(name = "fileName", value = "파일 명")
    private String fileName;

    /**
     * 파일 사이즈
     *
     * @author [이소정]
     */
    @Column(name = "FILE_SIZE")
    @ApiModelProperty(name = "fileSize", value = "파일 사이즈")
    private Long fileSize;

    /**
     * 파일 물리 명
     *
     * @author [이소정]
     */
    @Column(name = "FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "filePhysicalName", value = "파일 물리 명")
    private String filePhysicalName;

    /**
     * 썸네일 파일 명
     *
     * @author [이소정]
     */
    @Column(name = "THUMBNAIL_FILE_NAME")
    @ApiModelProperty(name = "thumbnailFileName", value = "썸네일 명", example = "graphic_file_name_thumbnail.jpg")
    private String thumbnailFileName;

    /**
     * 썸네일 파일 사이즈
     *
     * @author [이소정]
     */
    @Column(name = "THUMBNAIL_FILE_SIZE")
    @ApiModelProperty(name = "thumbnailFileSize", value = "썸네일 파일 사이즈", example = "300")
    private String thumbnailFileSize;

    /**
     * 썸네일 파일 물리 명
     *
     * @author [이소정]
     */
    @Column(name = "THUMBNAIL_FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "thumbnailFilePhysicalName", value = "썸네일 파일 물리 명", example = "http://cdnUrl/file/contents/graphic_file_name_thumbnail.jpg")
    private String thumbnailFilePhysicalName;


    /**
     * 주문 상품 정보
     *
     * @author [윤태호]
     */
    @ManyToOne
    @JoinColumn(name="ORDER_GOODS_SEQ",insertable = false,updatable = false)
    @JsonBackReference
    @ApiModelProperty(name = "주문 상품 정보", value = "주문 상품 정보")
    private OrderProductMapping orderProduct;


    /**
     * Instantiates a new Order product file.
     *
     * @param orderProductFileSaveDTO the order product file save dto
     * @author [이소정]
     * @implNote 생성자 주입 *
     * @since 2020. 12. 14. 오후 6:51:45
     */
    @Builder
    public OrderProductFile(final OrderProductFileSaveDTO orderProductFileSaveDTO) {
        this.orderGoodsSeq = orderProductFileSaveDTO.getOrderGoodsSeq();
        this.fileContentType = orderProductFileSaveDTO.getFileContentType();
        this.fileExtension = orderProductFileSaveDTO.getFileExtension();
        this.fileName = orderProductFileSaveDTO.getFileName();
        this.fileSize = orderProductFileSaveDTO.getFileSize();
        this.filePhysicalName = orderProductFileSaveDTO.getFilePhysicalName();
        this.thumbnailFileName = orderProductFileSaveDTO.getThumbnailFileName();
        this.thumbnailFileSize = orderProductFileSaveDTO.getThumbnailFileSize();
        this.thumbnailFilePhysicalName = orderProductFileSaveDTO.getThumbnailFilePhysicalName();
    }

}
