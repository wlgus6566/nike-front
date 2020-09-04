package com.nike.dnp.service.main;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.dto.main.MainResultDTO;
import com.nike.dnp.dto.notice.CustomerSearchDTO;
import com.nike.dnp.dto.report.ReportResultDTO;
import com.nike.dnp.dto.user.UserAuthSearchDTO;
import com.nike.dnp.repository.contents.ContentsRepository;
import com.nike.dnp.repository.report.ReportRepository;
import com.nike.dnp.service.banner.BannerService;
import com.nike.dnp.service.notice.NoticeService;
import com.nike.dnp.service.report.ReportService;
import com.nike.dnp.service.user.UserContentsService;
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class Agency service.
 *
 * @author [이소정]
 * @implNote 메인 서비스
 * @since 2020. 7. 20. 오후 12:08:45
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
     * The User contents service
     *
     * @author [이소정]
     */
    private final UserContentsService userContentsService;

    /**
     * The Report service
     *
     * @author [이소정]
     */
    private final ReportService reportService;

    /**
     * Find main info main result dto.
     *
     * @return the main result dto
     * @author [이소정]
     * @implNote 메인 정보 조회 (메인비쥬얼, ASSET, TOOLKIT, FOUNDATION, REPORT, NOTICE, NEWS)
     * @since 2020. 7. 27. 오후 6:53:07
     */
    public MainResultDTO findMainInfo(final String mobileYn) {
        final MainResultDTO mainResultDTO = new MainResultDTO();

        // 메인 비쥬얼(베너)
        mainResultDTO.setMainVisual(bannerService.getBanner());

        // 콘텐츠 (ASSET, TOOLKIT, FOUNDATION)
        mainResultDTO.setAssetContentsList(
                contentsRepository.findRecentContents(ServiceCode.ContentsTopMenuCode.ASSET.toString(),
                        PageRequest.of(0, 5, Sort.by(SORT_BY).descending()), "Y"
                )
        );
        mainResultDTO.setToolKitContentsList(
                contentsRepository.findRecentContents(ServiceCode.ContentsTopMenuCode.TOOLKIT.toString(),
                        PageRequest.of(0, 2, Sort.by(SORT_BY).descending()), "Y"
                )
        );
        mainResultDTO.setFoundationContentsList(
                contentsRepository.findRecentContents(ServiceCode.ContentsTopMenuCode.FOUNDATION.toString(),
                        PageRequest.of(0, 2, Sort.by(SORT_BY).descending()), "Y"
                )
        );

        // REPORT
        mainResultDTO.setReportList(this.checkReportAuthList());

        // NOTICE
        CustomerSearchDTO customerSearchDTO = new CustomerSearchDTO();
        customerSearchDTO.setNoticeArticleSectionCode(ServiceCode.NoticeArticleSectionEnumCode.NOTICE.toString());
        customerSearchDTO.setPage(0);
        customerSearchDTO.setSize("Y".equals(mobileYn) ? 5 : 7);
        mainResultDTO.setNoticeArticleList(noticeService.findNoticePages(customerSearchDTO).getContent());


        // NEWS
        CustomerSearchDTO newsSearchDTO = new CustomerSearchDTO();
        newsSearchDTO.setNoticeArticleSectionCode(ServiceCode.NoticeArticleSectionEnumCode.NEWS.toString());
        newsSearchDTO.setPage(0);
        newsSearchDTO.setSize(2);
        mainResultDTO.setNewsArticleList(noticeService.findNoticePages(newsSearchDTO).getContent());

        return mainResultDTO;
    }


    /**
     * Check report auth list list.
     *
     * @return the list
     * @author [이소정]
     * @implNote 리포트 목록 & 권한 체크
     * @since 2020. 9. 1. 오전 1:34:06
     */
    public List<ReportResultDTO> checkReportAuthList() {
        List<ReportResultDTO> reportList = reportRepository.findRecentReport(PageRequest.of(0, 3, Sort.by(SORT_BY).descending()));

        final UserAuthSearchDTO userAuthSearchDTO = new UserAuthSearchDTO();
        userAuthSearchDTO.setMenuCode(ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString());
        userAuthSearchDTO.setSkillCode(ServiceCode.MenuSkillEnumCode.VIEW.toString());
        userAuthSearchDTO.setAuthSeq(SecurityUtil.currentUser().getAuthSeq());
        List<AuthReturnDTO> reportAuthList = reportService.findAllAuthListWithDepth(userAuthSearchDTO, "N");

        Map<Long, AuthReturnDTO> map = new HashMap<Long, AuthReturnDTO>();

        if (!ObjectUtils.isEmpty(reportAuthList)) {
            for (AuthReturnDTO authReturnDTO : reportAuthList) {
                map.put(authReturnDTO.getAuthSeq(), authReturnDTO);
                if (!ObjectUtils.isEmpty(authReturnDTO.getSubAuths())) {
                    for (AuthReturnDTO depth2 : authReturnDTO.getSubAuths()) {
                        map.put(depth2.getAuthSeq(), depth2);
                        if (!ObjectUtils.isEmpty(depth2.getSubAuths())) {
                            for (AuthReturnDTO depth3 : depth2.getSubAuths()) {
                                map.put(depth3.getAuthSeq(), depth3);
                            }
                        }

                    }
                }
            }
        }

        // 목록에 내 seq가 있는지
        boolean checkMyDetailAuthYn = map.containsKey(SecurityUtil.currentUser().getAuthSeq());

        for (ReportResultDTO reportResultDTO : reportList) {
            if (checkMyDetailAuthYn && map.containsKey(reportResultDTO.getAuthSeq())) {
                reportResultDTO.setDetailAuthYn("Y");
            } else {
                reportResultDTO.setDetailAuthYn("N");
            }

        }

        return reportList;
    }
}
