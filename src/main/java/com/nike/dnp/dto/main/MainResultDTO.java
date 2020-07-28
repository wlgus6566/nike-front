package com.nike.dnp.dto.main;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.dto.contents.ContentsResultDTO;
import com.nike.dnp.dto.notice.NoticeArticleListDTO;
import com.nike.dnp.dto.report.ReportResultDTO;
import com.nike.dnp.entity.banner.Banner;
import lombok.*;

import java.util.List;

/**
 * The Class Main result dto.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 27. 오후 5:40:14
 * @Description
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class MainResultDTO {

    /**
     * The Main visual
     *
     * @author [이소정]
     */
    private Banner mainVisual;

    /**
     * The Asset contents list
     *
     * @author [이소정]
     */
    private List<ContentsResultDTO> assetContentsList;

    /**
     * The Tool kit contents list
     *
     * @author [이소정]
     */
    private List<ContentsResultDTO> toolKitContentsList;

    /**
     * The Foundation contents list
     *
     * @author [이소정]
     */
    private List<ContentsResultDTO> foundationContentsList;

    /**
     * The Report list
     *
     * @author [이소정]
     */
    private List<ReportResultDTO> reportList;

    /**
     * The Notice article list
     *
     * @author [이소정]
     */
    private List<NoticeArticleListDTO> noticeArticleList;

    /**
     * The News article list
     *
     * @author [이소정]
     */
    private List<NoticeArticleListDTO> newsArticleList;
}
