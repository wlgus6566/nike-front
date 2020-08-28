package com.nike.dnp.dto.contents;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.util.CloudFrontUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;


/**
 * The Class Contents basket result dto.
 *
 * @author [이소정]
 * @implNote 콘텐츠 장바구니 결과 DTO
 * @since 2020. 7. 14. 오후 5:23:51
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ContentsBasketResultDTO {

    /**
     * 컨텐츠 장바구니 시퀀스
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "contentsBasketSeq", value ="컨텐츠 장바구니 시퀀스", example = "3")
    private Long contentsBasketSeq;

    /**
     * 유저 시퀀스
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "userSeq", value ="유저 시퀀스", example = "3")
    private Long userSeq;

    /**
     * 컨텐츠 파일 시퀀스
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "contentsFileSeq", value ="컨텐츠 파일 시퀀스", example = "10")
    private Long contentsFileSeq;

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
    @ApiModelProperty(name = "fileSize", value = "파일 사이즈", example = "600")
    private Long fileSize;

    /**
     * 파일 물리 명
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "thumbnailFilePhysicalName", value = "썸네일 파일 물리 명", example = "http://cdnUrl/file/contents/graphic_img.jpg")
    private String thumbnailFilePhysicalName;

    /**
     * 파일 확장자
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileExtension", value = "파일 확장자", example = "JPG")
    private String fileExtension;

    /**
     * 파일 종류 공통코드
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileKindCode", value = "파일 종류 공통코드(FILE/VIDEO/VR)", required = true)
    private String fileKindCode;

    /**
     * Gets file physical name.
     *
     * @return the file physical name
     * @author [이소정]
     * @implNote cndUrl + FilePhysicalName
     * @since 2020. 7. 29. 오후 6:48:59
     */
    public String getThumbnailFilePhysicalName() {
        return CloudFrontUtil.getCustomSignedUrl(thumbnailFilePhysicalName);
    }

}
