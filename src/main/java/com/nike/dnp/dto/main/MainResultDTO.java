package com.nike.dnp.dto.main;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.dto.banner.BannerReturnDTO;
import com.nike.dnp.dto.contents.ContentsResultDTO;
import com.nike.dnp.dto.notice.CustomerListDTO;
import com.nike.dnp.dto.report.ReportResultDTO;
import lombok.*;

import java.util.List;

/**
 * The Class Main result dto.
 *
 * @author [이소정]
 * @since 2020. 7. 27. 오후 5:40:14
 * @implNote
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
    private BannerReturnDTO mainVisual;

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
    private List<CustomerListDTO> noticeArticleList;

    /**
     * The News article list
     *
     * @author [이소정]
     */
    private List<CustomerListDTO> newsArticleList;
}
