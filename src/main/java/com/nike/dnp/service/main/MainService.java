package com.nike.dnp.service.main;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.main.MainResultDTO;
import com.nike.dnp.dto.notice.NoticeSearchDTO;
import com.nike.dnp.repository.contents.ContentsRepository;
import com.nike.dnp.repository.report.ReportRepository;
import com.nike.dnp.service.banner.BannerService;
import com.nike.dnp.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 메인 화면(HOME 화면)관련 Service 입니다.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 27. 오후 5:39:29
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainService {

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
     * @CreatedOn 2020. 7. 27. 오후 6:53:07
     * @Description
     */
    public MainResultDTO findMainInfo() {
        MainResultDTO mainResultDTO = new MainResultDTO();

        // 메인 비쥬얼(베너)
        mainResultDTO.setMainVisual(bannerService.getBanner());

        // 콘텐츠 (ASSET, TOOLKIT, FOUNDATION)
        mainResultDTO.setAssetContentsList(
                contentsRepository.findRecentContents(ServiceCode.ContentsTopMenuCode.ASSET.toString(),
                        PageRequest.of(0, 4, Sort.by("updateDt").descending())
                )
        );
        mainResultDTO.setToolKitContentsList(
                contentsRepository.findRecentContents(ServiceCode.ContentsTopMenuCode.TOOLKIT.toString(),
                        PageRequest.of(0, 2, Sort.by("updateDt").descending())
                )
        );
        mainResultDTO.setFoundationContentsList(
                contentsRepository.findRecentContents(ServiceCode.ContentsTopMenuCode.FOUNDATION.toString(),
                        PageRequest.of(0, 2, Sort.by("updateDt").descending())
                )
        );

        // REPORT
        mainResultDTO.setReportList(reportRepository.findRecentReport(
                PageRequest.of(0, 4, Sort.by("updateDt").descending())
        ));

        // NOTICE
        NoticeSearchDTO noticeSearchDTO = new NoticeSearchDTO();
        noticeSearchDTO.setNoticeArticleSectionCode(ServiceCode.NoticeArticleSectionEnumCode.NOTICE.toString());
        noticeSearchDTO.setPage(0);
        noticeSearchDTO.setSize(5);
        mainResultDTO.setNoticeArticleList(noticeService.findNoticePages(noticeSearchDTO).getContent());


        // NEWS
        NoticeSearchDTO newsSearchDTO = new NoticeSearchDTO();
        newsSearchDTO.setNoticeArticleSectionCode(ServiceCode.NoticeArticleSectionEnumCode.NEWS.toString());
        newsSearchDTO.setPage(0);
        newsSearchDTO.setSize(2);
        mainResultDTO.setNewsArticleList(noticeService.findNoticePages(newsSearchDTO).getContent());

        return mainResultDTO;
    }
}
