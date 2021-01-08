package com.nike.dnp.dto.order;

import com.nike.dnp.entity.order.OrderProductFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class Order product file save dto.
 *
 * @author [이소정]
 * @since 2020. 12. 14. 오후 5:12:53
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class OrderProductFileSaveDTO {

    /**
     * 주문 상품 시퀀스
     *
     * @author [윤태호]
     */
    @ApiModelProperty(name = "orderGoodsSeq" , value = "주문 상품 시퀀스", hidden = true)
    private Long orderGoodsSeq;

    /**
     * 파일 컨텐츠 타입
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileContentType", value = "파일 컨텐츠 타입", example = "image/jpeg")
    private String fileContentType;

    /**
     * 파일 확장자
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileExtension", value = "파일 확장자", example = "JPG")
    private String fileExtension;

    /**
     * 파일 명
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileName", value = "파일 명", example = "graphic_file_name.jpg")
    private String fileName;

    /**
     * 파일 사이즈
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileSize", value = "파일 사이즈", example = "1200")
    private Long fileSize = 0l;

    /**
     * 파일 물리 명
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "filePhysicalName", value = "파일 물리 명", example = "/temp/graphic_img.jpg")
    private String filePhysicalName;

    /**
     * 썸네일 파일 명
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "thumbnailFileName", value = "썸네일 명", example = "graphic_file_name_thumbnail.jpg")
    private String thumbnailFileName;

    /**
     * 썸네일 파일 사이즈
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "thumbnailFileSize", value = "썸네일 파일 사이즈", example = "100")
    private String thumbnailFileSize;

    /**
     * 썸네일 파일 물리 명
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "thumbnailFilePhysicalName", value = "썸네일 파일 물리 명", example = "/temp/graphic_file_name_thumbnail.jpg")
    private String thumbnailFilePhysicalName;
}
