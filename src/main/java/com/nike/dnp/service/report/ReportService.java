package com.nike.dnp.service.report;

import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.report.*;
import com.nike.dnp.dto.user.UserContentsSearchDTO;
import com.nike.dnp.entity.report.Report;
import com.nike.dnp.entity.report.ReportFile;
import com.nike.dnp.entity.user.UserAuth;
import com.nike.dnp.repository.report.ReportFileRepository;
import com.nike.dnp.repository.report.ReportRepository;
import com.nike.dnp.repository.user.UserAuthRepository;
import com.nike.dnp.service.alarm.AlarmService;
import com.nike.dnp.service.auth.AuthService;
import com.nike.dnp.service.history.HistoryService;
import com.nike.dnp.service.user.UserContentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Class Report service.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 7. 오후 2:40:15
 * @Description
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    /**
     * The Report repository
     * @author [이소정]
     */
    private final ReportRepository reportRepository;

    /**
     * The Report file repository
     * @author [이소정]
     */
    private final ReportFileRepository reportFileRepository;

    /**
     * The Auth service
     *
     * @author [오지훈]
     */
    private final AuthService authService;

    /**
     * The History service.
     */
    private final HistoryService historyService;

    /**
     * The Alarm service
     *
     * @author [이소정]
     */
    private final AlarmService alarmService;

    /**
     * The User contents service
     *
     * @author [이소정]
     */
    private final UserContentsService userContentsService;

    /**
     * The User auth repository
     *
     * @author [이소정]
     */
    private final UserAuthRepository userAuthRepository;

    /**
     * Find all paging page.
     *
     * @param reportSearchDTO the report search dto
     * @return the page
     * @author [이소정]
     * @CreatedOn 2020. 7. 8. 오후 5:28:17
     * @Description
     */
    public Page<Report> findAllPaging(final AuthUserDTO authUserDTO, final ReportSearchDTO reportSearchDTO) {

        // 권한 검색 조건
        List<Long> authSeqList = new ArrayList<>();
        if (null != reportSearchDTO.getGroupSeq()) {
            authSeqList.add(reportSearchDTO.getGroupSeq());
        } else {
            List<AuthReturnDTO> authList = authService.findByAuthDepth(authUserDTO.getAuthSeq(), "REPORT_UPLOAD", ServiceEnumCode.MenuSkillEnumCode.REPORT.toString());
            for (AuthReturnDTO authReturnDTO : authList) {
                authSeqList.add(authReturnDTO.getAuthSeq());
            }
        }
        reportSearchDTO.setAuthSeqList(authSeqList);

        return reportRepository.findPageReport(
                reportSearchDTO,
                PageRequest.of(reportSearchDTO.getPage()
                , reportSearchDTO.getSize()
                , Sort.by("readCount").descending())
        );
    }

    /**
     * Save report.
     *
     * @param authUserDTO   the auth user dto
     * @param reportSaveDTO the report save dto
     * @return the report
     * @author [이소정]
     * @CreatedOn 2020. 7. 8. 오후 5:28:20
     * @Description
     */
    @Transactional
    public Report save(final AuthUserDTO authUserDTO, final ReportSaveDTO reportSaveDTO) {
        log.info("ReportService.save");
        reportSaveDTO.setAuthSeq(authUserDTO.getAuthSeq());
        final Report savedReport = reportRepository.save(new Report().save(reportSaveDTO));
        List<ReportFile> reportFileList = new ArrayList<>();

        if (!reportSaveDTO.getReportFileSaveDTOList().isEmpty()) {
            for (ReportFileSaveDTO reportFileSaveDTO : reportSaveDTO.getReportFileSaveDTOList()) {
                ReportFile savedReportFile = reportFileRepository.save(new ReportFile().save(savedReport.getReportSeq(), reportFileSaveDTO));
                reportFileList.add(savedReportFile);
            }
        }
        savedReport.setReportFileList(reportFileList);

        // 알림 저장
        alarmService.sendAlarmTargetList(
                ServiceEnumCode.AlarmActionEnumCode.UPDATE.toString()
                , ServiceEnumCode.HistoryTabEnumCode.REPORT_MANAGE.toString()
                , null
                , savedReport.getReportSeq()
                , this.findAllAuthUser());


        // 최근 업로드 목록 추가
        historyService.saveRecentUploadHistory(savedReport.getReportSeq(), ServiceEnumCode.HistoryTabEnumCode.REPORT_MANAGE.toString());

        return savedReport;
    }

    /**
     * Find by report seq report.
     *
     * @param reportSeq the report seq
     * @return the report
     * @author [이소정]
     * @CreatedOn 2020. 7. 8. 오후 5:52:10
     * @Description
     */
    @Transactional
    public Report findByReportSeq(final Long reportSeq) {
        Report findReport = reportRepository.findByReportSeq(reportSeq);
        findReport.updateReadCount(findReport.getReadCount());

        // history 저장
        historyService.saveViewHistory(reportSeq, ServiceEnumCode.HistoryTabEnumCode.REPORT_MANAGE.toString());

        return findReport;
    }

    /**
     * Update optional.
     *
     * @param reportUpdateDTO the report update dto
     * @return the optional
     * @author [이소정]
     * @CreatedOn 2020. 7. 9. 오후 6:49:17
     * @Description
     */
    @Transactional
    public Optional<Report> update(final Long reportSeq, final ReportUpdateDTO reportUpdateDTO) {
        log.info("reportService.update");
        reportUpdateDTO.setReportSeq(reportSeq);

        final Optional<Report> report = reportRepository.findById(reportUpdateDTO.getReportSeq());
        report.ifPresent(value -> value.update(reportUpdateDTO));

        final List<ReportFile> beforeFileList = reportFileRepository.findByReportSeqAndUseYn(reportUpdateDTO.getReportSeq(), "Y");
        List<ReportFileUpdateDTO> newFileList = reportUpdateDTO.getReportFileUpdateDTOList();

        if (!beforeFileList.isEmpty() && !newFileList.isEmpty()) {
            for (ReportFile beforeFile : beforeFileList) {
                for (ReportFileUpdateDTO newFile : newFileList) {
                    if (beforeFile.getReportFileSeq() == newFile.getReportFileSeq()) {
                        beforeFileList.remove(beforeFile);
                    }
                }
            }
        }

        if (!newFileList.isEmpty()) {
            for (ReportFileUpdateDTO reportFileUpdateDTO : newFileList) {
                Long contentsFileSeq = reportFileUpdateDTO.getReportFileSeq();
                ReportFile saveReportFile = new ReportFile().updateNewFile(report.get().getReportSeq(), reportFileUpdateDTO);
                if (null != contentsFileSeq) {
                    Optional<ReportFile> reportFile = reportFileRepository.findById(reportFileUpdateDTO.getReportFileSeq());
                    reportFile.ifPresent(value -> value.update(reportFileUpdateDTO));
                } else {
                    reportFileRepository.save(saveReportFile);
                }
            }
        }

        if (!beforeFileList.isEmpty()) {
            for (ReportFile reportFile : beforeFileList) {
                reportFile.updateUseYn("N");
            }
        }

        // 알림 저장
        alarmService.sendAlarmTargetList(
                ServiceEnumCode.AlarmActionEnumCode.UPDATE.toString()
                , ServiceEnumCode.HistoryTabEnumCode.REPORT_MANAGE.toString()
                , null
                , reportSeq
                , this.findAllAuthUser());

        return report;
    }

    /**
     * Find all auth user list.
     *
     * @return the list
     * @author [이소정]
     * @CreatedOn 2020. 7. 24. 오후 8:20:01
     * @Description 보고서 상세 권한 있는 그룹의 회원 목록
     */
    public List<Long> findAllAuthUser() {
        UserContentsSearchDTO userContentsSearchDTO = new UserContentsSearchDTO();
        userContentsSearchDTO.setMenuCode(ServiceEnumCode.HistoryTabEnumCode.REPORT_MANAGE.toString());
        userContentsSearchDTO.setSkillCode(ServiceEnumCode.MenuSkillEnumCode.VIEW.toString());
        List<AuthReturnDTO> authList = userContentsService.getAuthList(userContentsSearchDTO);

        List<Long> userSeqList  = new ArrayList<>();
        for (AuthReturnDTO authReturnDTO : authList) {
            // authSeq 를 가지고 userSeq 목록 가져오기
            List<UserAuth> userAuthList = userAuthRepository.findAllByAuthSeq(authReturnDTO.getAuthSeq());
            for (UserAuth userAuth : userAuthList) {
                userSeqList.add(userAuth.getUserSeq());
            }
        }
        return userSeqList;
    }


    /**
     * Delete optional.
     *
     * @param reportSeq the report seq
     * @return the optional
     * @author [이소정]
     * @CreatedOn 2020. 7. 9. 오후 5:49:18
     * @Description
     */
    @Transactional
    public Optional<Report> delete(final Long reportSeq) {
        Optional<Report> report = reportRepository.findById(reportSeq);
        report.ifPresent(value -> value.updateUseYn("N"));

        if (!report.get().getReportFileList().isEmpty()) {
            for (ReportFile reportFile : report.get().getReportFileList()) {
                reportFile.updateUseYn("N");
            }
        }

        return report;
    }

}
