package com.nike.dnp.service.report;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.contents.ContentsFileUpdateDTO;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.dto.report.*;
import com.nike.dnp.dto.user.UserContentsSearchDTO;
import com.nike.dnp.entity.contents.ContentsFile;
import com.nike.dnp.entity.report.Report;
import com.nike.dnp.entity.report.ReportFile;
import com.nike.dnp.entity.user.UserAuth;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.report.ReportFileRepository;
import com.nike.dnp.repository.report.ReportRepository;
import com.nike.dnp.repository.user.UserAuthRepository;
import com.nike.dnp.service.alarm.AlarmService;
import com.nike.dnp.service.auth.AuthService;
import com.nike.dnp.service.contents.ContentsService;
import com.nike.dnp.service.history.HistoryService;
import com.nike.dnp.service.user.UserContentsService;
import com.nike.dnp.util.ImageUtil;
import com.nike.dnp.util.MessageUtil;
import com.nike.dnp.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Class Report service.
 *
 * @author [이소정]
 * @since 2020. 7. 7. 오후 2:40:15
 * @implNote
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
     * @since 2020. 7. 8. 오후 5:28:17
     * @implNote
     */
    public Page<Report> findAllPaging(final AuthUserDTO authUserDTO, final ReportSearchDTO reportSearchDTO) {

        // 권한 검색 조건
        List<Long> authSeqList = new ArrayList<>();
        if (null != reportSearchDTO.getGroupSeq()) {
            authSeqList.add(reportSearchDTO.getGroupSeq());
        } else {
            List<AuthReturnDTO> authList = authService.findByAuthDepth(authUserDTO.getAuthSeq(), "REPORT_UPLOAD", ServiceCode.MenuSkillEnumCode.REPORT.toString());
            for (AuthReturnDTO authReturnDTO : authList) {
                authSeqList.add(authReturnDTO.getAuthSeq());
            }
        }
        reportSearchDTO.setAuthSeqList(authSeqList);

        return reportRepository.findPageReport(
                reportSearchDTO,
                PageRequest.of(reportSearchDTO.getPage()
                , reportSearchDTO.getSize()
                , Sort.by("updateDt").descending())
        );
    }

    /**
     * Save report.
     *
     * @param authUserDTO   the auth user dto
     * @param reportSaveDTO the report save dto
     * @return the report
     * @author [이소정]
     * @since 2020. 7. 8. 오후 5:28:20
     * @implNote
     */
    @Transactional
    public Report save(final AuthUserDTO authUserDTO, final ReportSaveDTO reportSaveDTO) {
        log.info("ReportService.save");
        reportSaveDTO.setAuthSeq(authUserDTO.getAuthSeq());

        // 썸네일 base64 -> file 정보로 변환
        if (!ObjectUtils.isEmpty(reportSaveDTO.getImageBase64())) {
            FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(ServiceCode.FileFolderEnumCode.REPORT.getFolder(), reportSaveDTO.getImageBase64());

            reportSaveDTO.setImageFileName(fileResultDTO.getFileName());
            reportSaveDTO.setImageFileSize(String.valueOf(fileResultDTO.getFileSize()));
            reportSaveDTO.setImageFilePhysicalName(fileResultDTO.getFilePhysicalName());
        }

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
                ServiceCode.AlarmActionEnumCode.UPDATE.toString()
                , ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString()
                , null
                , savedReport.getReportSeq()
                , this.findAllAuthUser());


        // 최근 업로드 목록 추가
        historyService.saveRecentUploadHistory(savedReport.getReportSeq(), ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString());

        return savedReport;
    }

    /**
     * Temp to real path file move string.
     *
     * @param filePhysicalName the file physical name
     * @return the string
     * @author [이소정]
     * @CreatedOn 2020. 7. 28. 오후 3:59:37
     */
    public String fileMoveTempToRealPath(final String filePhysicalName) {
        String imgPath = filePhysicalName;
        if (null  != filePhysicalName) {
            imgPath = S3Util.fileCopyAndOldFileDelete(filePhysicalName, ServiceCode.FileFolderEnumCode.REPORT.getFolder());
        }
        return imgPath;
    }

    /**
     * S 3 file copy update report file save dto.
     *
     * @param reportFileSaveDTO the report file save dto
     * @return the report file save dto
     * @author [이소정]
     * @CreatedOn 2020. 7. 28. 오후 5:21:39
     */
    public ReportFileSaveDTO s3FileCopyUpdate(final ReportFileSaveDTO reportFileSaveDTO) {
        reportFileSaveDTO.setFilePhysicalName(this.fileMoveTempToRealPath(reportFileSaveDTO.getFilePhysicalName()));
        reportFileSaveDTO.setThumbnailFilePhysicalName(this.fileMoveTempToRealPath(reportFileSaveDTO.getThumbnailFilePhysicalName()));
        reportFileSaveDTO.setDetailThumbnailFilePhysicalName(this.fileMoveTempToRealPath(reportFileSaveDTO.getThumbnailFilePhysicalName()));
        return reportFileSaveDTO;
    }

    /**
     * Find by report seq report.
     *
     * @param reportSeq the report seq
     * @return the report
     * @author [이소정]
     * @since 2020. 7. 8. 오후 5:52:10
     * @implNote
     */
    @Transactional
    public Report findByReportSeq(final Long reportSeq) {
        Report findReport = reportRepository.findByReportSeq(reportSeq);
        findReport.updateReadCount(findReport.getReadCount());

        // history 저장
        historyService.saveViewHistory(reportSeq, ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString());

        return findReport;
    }

    /**
     * Find by id optional.
     *
     * @param reportSeq the report seq
     * @return the optional
     */
    public Optional<Report> findById(final Long reportSeq) {
        return Optional.ofNullable(reportRepository.findById(reportSeq).orElseThrow(
                () -> new CodeMessageHandleException(FailCode.ExceptionError.NOT_FOUND.name(), MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name()))));
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
    public Report update(final ReportUpdateDTO reportUpdateDTO) {
        log.info("reportService.update");
        final Optional<Report> report = this.findById(reportUpdateDTO.getReportSeq());

        // 썸네일 base64 -> file 정보로 변환
        if (!ObjectUtils.isEmpty(reportUpdateDTO.getImageBase64())) {
            FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(ServiceCode.FileFolderEnumCode.REPORT.getFolder(), reportUpdateDTO.getImageBase64());

            reportUpdateDTO.setImageFileName(fileResultDTO.getFileName());
            reportUpdateDTO.setImageFileSize(String.valueOf(fileResultDTO.getFileSize()));
            reportUpdateDTO.setImageFilePhysicalName(fileResultDTO.getFilePhysicalName());
        }


        report.ifPresent(value -> value.update(reportUpdateDTO));

        final List<ReportFile> beforeFileList = reportFileRepository.findByReportSeqAndUseYn(reportUpdateDTO.getReportSeq(), "Y");
        final List<ReportFile> lastBeforeFileList = reportFileRepository.findByReportSeqAndUseYn(reportUpdateDTO.getReportSeq(), "Y");
        List<ReportFileUpdateDTO> newFileList = reportUpdateDTO.getReportFileUpdateDTOList();

        if (!beforeFileList.isEmpty() && !newFileList.isEmpty()) {
            for (ReportFile beforeFile : beforeFileList) {
                for (ReportFileUpdateDTO newFile : newFileList) {
                    if (beforeFile.getReportFileSeq() == newFile.getReportFileSeq()) {
                        lastBeforeFileList.remove(beforeFile);
                    }
                }
            }
        }

        if (!newFileList.isEmpty()) {
            for (ReportFileUpdateDTO reportFileUpdateDTO : newFileList) {
                Long reportFileSeq = null != reportFileUpdateDTO.getReportFileSeq() ? reportFileUpdateDTO.getReportFileSeq() : 0l;
                final Optional<ReportFile> reportFile = reportFileRepository.findById(reportFileSeq);

                this.s3FileCopyUpdate(reportFileUpdateDTO);
                ReportFile saveReportFile = reportFile.orElse(
                        new ReportFile().updateNewFile(report.get().getReportSeq(), reportFileUpdateDTO)
                );

                if (0l != reportFileSeq) {
                    reportFile.ifPresent(value -> value.update(reportFileUpdateDTO));
                } else {
                    reportFileRepository.save(saveReportFile);
                }
            }
        }

        if (!lastBeforeFileList.isEmpty()) {
            for (ReportFile reportFile : lastBeforeFileList) {
                reportFile.updateUseYn("N");
            }
        }

        // 알림 저장
        alarmService.sendAlarmTargetList(
                ServiceCode.AlarmActionEnumCode.UPDATE.toString()
                , ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString()
                , null
                , reportUpdateDTO.getReportSeq()
                , this.findAllAuthUser());

        return report.get();
    }

    /**
     * S 3 file copy update contents file save dto.
     *
     * @param reportFileUpdateDTO the report file update dto
     * @return the contents file save dto
     * @author [이소정]
     * @CreatedOn 2020. 7. 28. 오후 4:05:42
     */
    public ReportFileUpdateDTO s3FileCopyUpdate(final ReportFileUpdateDTO reportFileUpdateDTO) {
        reportFileUpdateDTO.setFilePhysicalName(this.fileMoveTempToRealPath(reportFileUpdateDTO.getFilePhysicalName()));
        reportFileUpdateDTO.setThumbnailFilePhysicalName(this.fileMoveTempToRealPath(reportFileUpdateDTO.getThumbnailFilePhysicalName()));
        reportFileUpdateDTO.setDetailThumbnailFilePhysicalName(this.fileMoveTempToRealPath(reportFileUpdateDTO.getThumbnailFilePhysicalName()));
        return reportFileUpdateDTO;
    }

    /**
     * 보고서 상세 권한 있는 그룹의 회원 목록
     *
     * @return the list
     * @author [이소정]
     * @since 2020. 7. 24. 오후 8:20:01
     * @implNote 보고서 상세 권한 있는 그룹의 회원 목록
     */
    public List<Long> findAllAuthUser() {
        UserContentsSearchDTO userContentsSearchDTO = new UserContentsSearchDTO();
        userContentsSearchDTO.setMenuCode(ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString());
        userContentsSearchDTO.setSkillCode(ServiceCode.MenuSkillEnumCode.VIEW.toString());
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
     * @since 2020. 7. 9. 오후 5:49:18
     * @implNote
     */
    @Transactional
    public Report delete(final Long reportSeq) {
        Optional<Report> report = this.findById(reportSeq);
        Report savedReport = report.get();
        report.ifPresent(value -> value.updateUseYn("N"));

        if (!savedReport.getReportFileList().isEmpty()) {
            for (ReportFile reportFile : savedReport.getReportFileList()) {
                reportFile.updateUseYn("N");
            }
        }

        return savedReport;
    }

}
