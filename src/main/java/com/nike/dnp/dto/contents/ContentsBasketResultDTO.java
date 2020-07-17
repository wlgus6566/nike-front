package com.nike.dnp.dto.contents;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;


/**
 * The Class Contents basket result dto.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 14. 오후 5:23:51
 * @Description
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
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
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileName", value = "파일 명", example = "graphic_file_name.jpg")
    private String fileName;

    /**
     * 파일 사이즈
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileSize", value = "파일 사이즈", example = "600")
    private Long fileSize;

    /**
     * 파일 물리 명
     * @author [이소정]
     */
    @ApiModelProperty(name = "filePhysicalName", value = "파일 물리 명", example = "/cdn/file/path")
    private String filePhysicalName;

}
