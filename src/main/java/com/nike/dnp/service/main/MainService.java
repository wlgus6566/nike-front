package com.nike.dnp.service.main;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.main.MainResultDTO;
import com.nike.dnp.dto.notice.CustomerSearchDTO;
import com.nike.dnp.repository.contents.ContentsRepository;
import com.nike.dnp.repository.report.ReportRepository;
import com.nike.dnp.service.banner.BannerService;
import com.nike.dnp.service.contents.ContentsService;
import com.nike.dnp.service.notice.NoticeService;
import com.nike.dnp.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 메인 화면(HOME 화면)관련 Service 입니다.
 *
 * @author [이소정]
 * @since 2020. 7. 27. 오후 5:39:29
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainService {

    /**
     * The constant SORT_BY
     *
     * @author [오지훈]
     */
    private final static String SORT_BY = "updateDt";

    /**
     * 베너 서비스 (메인 비쥬얼 서비스)
     * 
     *
     * @author [이소정]
     */
    private final BannerService bannerService;

    /**
     * 보고서 repository
     *
     * @author [이소정]
     */
    private final ReportRepository reportRepository;

    /**
     * 콘텐츠 repository
     *
     * @author [이소정]
     */
    private final ContentsRepository contentsRepository;

    /**
     * 공지사항, FAQ service
     *
     * @author [정주희]
     */
    private final NoticeService noticeService;

    /**
     * Find main info main result dto.
     *
     * @return the main result dto
     * @author [이소정]
     * @since 2020. 7. 27. 오후 6:53:07
     * @implNote
     */
    public MainResultDTO findMainInfo() {
        final MainResultDTO mainResultDTO = new MainResultDTO();

        // 메인 비쥬얼(베너)
        mainResultDTO.setMainVisual(bannerService.getBanner());

        // 콘텐츠 (ASSET, TOOLKIT, FOUNDATION)
        mainResultDTO.setAssetContentsList(
                contentsRepository.findRecentContents(ServiceCode.ContentsTopMenuCode.ASSET.toString(),
                        PageRequest.of(0, 4, Sort.by(SORT_BY).descending())
                )
        );
        mainResultDTO.setToolKitContentsList(
                contentsRepository.findRecentContents(ServiceCode.ContentsTopMenuCode.TOOLKIT.toString(),
                        PageRequest.of(0, 2, Sort.by(SORT_BY).descending())
                )
        );
        mainResultDTO.setFoundationContentsList(
                contentsRepository.findRecentContents(ServiceCode.ContentsTopMenuCode.FOUNDATION.toString(),
                        PageRequest.of(0, 2, Sort.by(SORT_BY).descending())
                )
        );

        // REPORT
        mainResultDTO.setReportList(reportRepository.findRecentReport(
                PageRequest.of(0, 4, Sort.by(SORT_BY).descending())
        ));

        // NOTICE
        CustomerSearchDTO customerSearchDTO = new CustomerSearchDTO();
        customerSearchDTO.setNoticeArticleSectionCode(ServiceCode.NoticeArticleSectionEnumCode.NOTICE.toString());
        customerSearchDTO.setPage(0);
        customerSearchDTO.setSize(5);
        mainResultDTO.setNoticeArticleList(noticeService.findNoticePages(customerSearchDTO).getContent());


        // NEWS
        CustomerSearchDTO newsSearchDTO = new CustomerSearchDTO();
        newsSearchDTO.setNoticeArticleSectionCode(ServiceCode.NoticeArticleSectionEnumCode.NEWS.toString());
        newsSearchDTO.setPage(0);
        newsSearchDTO.setSize(2);
        mainResultDTO.setNewsArticleList(noticeService.findNoticePages(newsSearchDTO).getContent());

        return mainResultDTO;
    }
}
