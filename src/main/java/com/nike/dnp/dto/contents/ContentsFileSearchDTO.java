package com.nike.dnp.dto.contents;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.persistence.Column;

/**
 * The Class Contents file search dto.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 13. 오후 5:47:03
 * @Description
 */
@Getter
@Setter
@ToString
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
    @ApiParam(value = "파일 구분 코드", name = "sectionCode" , defaultValue = "ALL")
    private String sectionCode = "";

    /**
     * 정렬 타입
     *
     * @author [이소정]
     */
    @ApiParam(value = "정렬 타입", name = "orderType" , defaultValue = "FILENAME")
    private String orderType = "";

    /**
     * 확장자
     *
     * @author [이소정]
     */
    @ApiParam(value = "확장자", name = "fileExtension" , defaultValue = "JPG")
    private String fileExtension = "";

    /**
     * Method to String
     *
     * @return String string
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 5:57:26
     * @Description
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
