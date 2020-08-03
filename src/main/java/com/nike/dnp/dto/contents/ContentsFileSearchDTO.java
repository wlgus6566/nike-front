package com.nike.dnp.dto.contents;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

/**
 * The Class Contents file search dto.
 *
 * @author [이소정]
 * @since 2020. 7. 13. 오후 5:47:03
 * @implNote 컨텐츠 파일 조회 DTO
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ContentsFileSearchDTO extends SearchDTO {

    /**
     * 컨텐츠 시퀀스
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "contentsSeq", value = "컨텐츠 시퀀스", hidden = true)
    private Long contentsSeq;

    /**
     * 파일 구분 코드
     *
     * @author [이소정]
     */
    @ApiParam(value = "파일 구분 코드(ALL/ASSET/GUIDE/VIDEO)", name = "sectionCode" , defaultValue = "ALL")
    private String sectionCode = "";

    /**
     * 정렬 타입
     *
     * @author [이소정]
     */
    @ApiParam(value = "정렬 타입(기본정렬 ORDER/파일명 순 FILE_NAME)", name = "orderType" , defaultValue = "ORDER")
    private String orderType = "";

    /**
     * 파일 확장자
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileExtension(부모코드:FILE_EXTENSION)", value = "파일 확장자", example = "JPG")
    private String fileExtension;

    /**
     * Method to String
     *
     * @return String string
     * @author [이소정]
     * @since 2020. 6. 19. 오후 5:57:26
     * @implNote
     */
    @Override
    public String toString() {
        return "ContentsFileSearchDTO{"
                + "sectionCode=" + sectionCode
                + ", orderType=" + orderType
                + ", size=" + getSize()
                + ", page=" + getPage() + '}';
    }

}
