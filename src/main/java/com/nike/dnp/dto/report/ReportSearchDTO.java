package com.nike.dnp.dto.report;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiParam;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ReportSearchDTO extends SearchDTO {

    /**
     * 검색어
     *
     * @author [이소정]
     */
    @ApiParam(value = "검색어", name = "keyword" , defaultValue = "NIKE")
    private String keyword = "";

    /**
     * 보고서 구분 코드
     *
     * @author [이소정]
     */
    @ApiParam(value = "보고서 구분 코드", name = "sectionCode", defaultValue = "ALL")
    private String sectionCode = "";

    /**
     * 그룹 시퀀스
     *
     * @author [이소정]
     */
    @ApiParam(name = "groupSeq", value = "그룹 시퀀스")
    private Long groupSeq;

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
        return "ReportSearchDTO{"
                + "keyword=" + keyword
                + ", size=" + getSize()
                + ", page=" + getPage() + '}';
    }

}
