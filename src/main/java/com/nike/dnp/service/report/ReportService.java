package com.nike.dnp.service.report;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.dto.contents.ContentsSaveDTO;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.dto.report.*;
import com.nike.dnp.dto.user.UserContentsSearchDTO;
import com.nike.dnp.entity.report.Report;
import com.nike.dnp.entity.report.ReportFile;
import com.nike.dnp.entity.user.UserAuth;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.report.ReportFileRepository;
import com.nike.dnp.repository.report.ReportRepository;
import com.nike.dnp.repository.user.UserAuthRepository;
import com.nike.dnp.service.alarm.AlarmService;
import com.nike.dnp.service.auth.AuthService;
import com.nike.dnp.service.history.HistoryService;
import com.nike.dnp.service.user.UserContentsService;
import com.nike.dnp.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Class Report service.
 *
 * @author [이소정]
 * @implNote 보고서 서비스
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
     *
     * @author [이소정]
     */
    private final ReportRepository reportRepository;

    /**
     * The Report file repository
     *
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
     *
     * @author [이소정]
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
     * @implNote 보고서 페이징 처리 된 목록 조회
     * @since 2020. 7. 8. 오후 5:28:17
     */
    public Page<Report> findAllPaging(final ReportSearchDTO reportSearchDTO) {
        log.info("ReportService.findAllPaging");
        // 권한 검색 조건
        List<Long> authSeqList = new ArrayList<>();
        if (null != reportSearchDTO.getGroupSeq()) {
            authSeqList.add(reportSearchDTO.getGroupSeq());
        } else {
            List<AuthReturnDTO> authList = authService.findByAuthDepth(SecurityUtil.currentUser().getAuthSeq(), "REPORT_UPLOAD", ServiceCode.MenuSkillEnumCode.REPORT.toString());
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
     * @param reportSaveDTO the report save dto
     * @return the report
     * @author [이소정]
     * @implNote 보고서 저장
     * @since 2020. 7. 8. 오후 5:28:20
     */
    @Transactional
    public Report save(final ReportSaveDTO reportSaveDTO) {
        log.info("ReportService.save");
        reportSaveDTO.setAuthSeq(SecurityUtil.currentUser().getAuthSeq());

        // 썸네일 base64 -> file 정보로 변환
        this.base64ToFile(reportSaveDTO);

        final Report savedReport = reportRepository.save(new Report().save(reportSaveDTO));
        List<ReportFile> reportFileList = new ArrayList<>();

        if (!reportSaveDTO.getReportFileSaveDTOList().isEmpty()) {
            for (ReportFileSaveDTO reportFileSaveDTO : reportSaveDTO.getReportFileSaveDTOList()) {
                ReportFile savedReportFile = reportFileRepository.save(
                        new ReportFile().save(savedReport.getReportSeq(), this.s3FileCopySave(reportFileSaveDTO))
                );
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
     * Find by report seq report.
     *
     * @param reportSeq the report seq
     * @return the report
     * @author [이소정]
     * @implNote 보고서 seq로 상세 조회
     * @since 2020. 7. 8. 오후 5:52:10
     */
    @Transactional
    public Report findByReportSeq(final Long reportSeq) {
        log.info("ReportService.findByReportSeq");
        Report findReport = reportRepository.findByReportSeq(reportSeq);
        findReport.updateReadCount(findReport.getReadCount());

        // history 저장
        historyService.saveViewHistory(reportSeq, ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString());

        return findReport;
    }

    /**
     * Base 64 to file report save dto.
     *
     * @param reportSaveDTO the report save dto
     * @return the report save dto
     * @author [이소정]
     * @implNote base64 -> 파일 형식으로 변환
     * @since 2020. 8. 3. 오후 3:30:09
     */
    public ReportSaveDTO base64ToFile(ReportSaveDTO reportSaveDTO) {
        log.info("reportService.base64ToFile");
        // 썸네일 base64 -> file 정보로 변환
        if (!ObjectUtils.isEmpty(reportSaveDTO.getImageBase64())) {
            FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(ServiceCode.FileFolderEnumCode.REPORT.getFolder(), reportSaveDTO.getImageBase64());

            reportSaveDTO.setImageFileName(fileResultDTO.getFileName());
            reportSaveDTO.setImageFileSize(String.valueOf(fileResultDTO.getFileSize()));
            reportSaveDTO.setImageFilePhysicalName(fileResultDTO.getFilePhysicalName());
        }
        return reportSaveDTO;
    }

    /**
     * Update optional.
     *
     * @param reportSaveDTO the report save dto
     * @return the optional
     * @author [이소정]
     * @implNote
     * @CreatedOn 2020. 7. 9. 오후 6:49:17
     * @Description
     * @since 2020. 7. 30. 오후 2:58:57
     */
    @Transactional
    public Report update(final ReportSaveDTO reportSaveDTO) {
        log.info("reportService.update");
        final Optional<Report> report = this.findById(reportSaveDTO.getReportSeq());

        // 썸네일 base64 -> file 정보로 변환
        this.base64ToFile(reportSaveDTO);

        report.ifPresent(value -> value.update(reportSaveDTO));

        final List<ReportFile> beforeFileList = reportFileRepository.findByReportSeqAndUseYn(reportSaveDTO.getReportSeq(), "Y");
        final List<ReportFile> lastBeforeFileList = reportFileRepository.findByReportSeqAndUseYn(reportSaveDTO.getReportSeq(), "Y");
        List<ReportFileSaveDTO> newFileList = reportSaveDTO.getReportFileSaveDTOList();

        if (!beforeFileList.isEmpty() && !newFileList.isEmpty()) {
            for (ReportFile beforeFile : beforeFileList) {
                for (ReportFileSaveDTO newFile : newFileList) {
                    if (beforeFile.getReportFileSeq() == newFile.getReportFileSeq()) {
                        lastBeforeFileList.remove(beforeFile);
                    }
                }
            }
        }

        if (!newFileList.isEmpty()) {
            for (ReportFileSaveDTO reportFileSaveDTO : newFileList) {
                Long reportFileSeq = null != reportFileSaveDTO.getReportFileSeq() ? reportFileSaveDTO.getReportFileSeq() : 0l;
                final Optional<ReportFile> reportFile = reportFileRepository.findById(reportFileSeq);

                this.s3FileCopySave(reportFileSaveDTO);
                ReportFile saveReportFile = reportFile.orElse(
                        new ReportFile().save(report.get().getReportSeq(), reportFileSaveDTO)
                );

                if (0l != reportFileSeq) {
                    reportFile.ifPresent(value -> value.update(reportFileSaveDTO));
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
                , reportSaveDTO.getReportSeq()
                , this.findAllAuthUser());

        return report.get();
    }

    /**
     * Delete optional.
     *
     * @param reportSeq the report seq
     * @return the optional
     * @author [이소정]
     * @implNote 보고서 삭제
     * @since 2020. 7. 9. 오후 5:49:18
     */
    @Transactional
    public Report delete(final Long reportSeq) {
        log.info("ReportService.delete");
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

    /**
     * Delete report.
     * 수정일 기준 일정기간 이전 보고서 삭제 - 배치용
     *
     * @param beforeDate  the before date
     * @author [이소정]
     * @implNote 수정일 기준 일정기간 이전 보고서 삭제 - 배치용
     * @since 2020. 7. 30. 오후 6:29:17
     */
    @Transactional
    public void deleteReport(final LocalDateTime beforeDate) {
        log.info("ReportService.deleteContents");
        final List<Report> reportList = reportRepository.findByUpdateDtBefore(beforeDate);
        reportRepository.deleteAll(reportList);
    }

    /**
     * Download file response entity.
     *
     * @param reportFileSeq the report file seq
     * @return the response entity
     * @author [이소정]
     * @implNote 보고서 파일 다운로드
     * @since 2020. 7. 31. 오후 3:24:10
     */
    @Transactional
    public ResponseEntity<Resource> downloadFile(final Long reportFileSeq) {
        log.info("ReportService.downloadFile");
        Optional<ReportFile> reportFile = this.findByFileId(reportFileSeq);
        if (reportFile.isPresent()) {
            reportFile.ifPresent(value -> value.updateDownloadCount(reportFile.get().getDownloadCount()));
            return FileUtil.fileDownload(reportFile.get().getFilePhysicalName());
        } else {
            return null;
        }
    }

    /**
     * 보고서 상세 권한 있는 그룹의 회원 목록
     *
     * @return the list
     * @author [이소정]
     * @implNote 보고서 상세 권한 있는 그룹의 회원 목록
     * @since 2020. 7. 24. 오후 8:20:01
     */
    public List<Long> findAllAuthUser() {
        log.info("ReportService.findAllAuthUser");
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
     * Find by id optional.
     *
     * @param reportSeq the report seq
     * @return the optional
     * @author [이소정]
     * @implNote 보고서 seq 로 조회
     * @since 2020. 7. 30. 오후 2:58:57
     */
    public Optional<Report> findById(final Long reportSeq) {
        log.info("ReportService.findById");
        return Optional.ofNullable(reportRepository.findById(reportSeq).orElseThrow(
                () -> new CodeMessageHandleException(FailCode.ExceptionError.NOT_FOUND.name(), MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name()))));
    }

    /**
     * Find by file id optional.
     *
     * @param reportFileSeq the report file seq
     * @return the optional
     * @author [이소정]
     * @implNote 보고서 파일 seq 로 조회
     * @since 2020. 7. 31. 오후 3:36:50
     */
    public Optional<ReportFile> findByFileId(final Long reportFileSeq) {
        log.info("ReportService.findByFileId");
        return Optional.ofNullable(reportFileRepository.findById(reportFileSeq).orElseThrow(
                () -> new CodeMessageHandleException(FailCode.ExceptionError.NOT_FOUND.name(), MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name()))));
    }

    /**
     * S 3 file copy update report file save dto.
     *
     * @param reportFileSaveDTO the report file save dto
     * @return the report file save dto
     * @author [이소정]
     * @implNote 보고서 저장 > 파일 경로(temp -> report) 변경 후 set
     * @since 2020. 7. 28. 오후 5:21:39
     */
    public ReportFileSaveDTO s3FileCopySave(final ReportFileSaveDTO reportFileSaveDTO) {
        log.info("ReportService.s3FileCopySave");
        reportFileSaveDTO.setFilePhysicalName(this.fileMoveTempToRealPath(reportFileSaveDTO.getFilePhysicalName()));
        reportFileSaveDTO.setThumbnailFilePhysicalName(this.fileMoveTempToRealPath(reportFileSaveDTO.getThumbnailFilePhysicalName()));
        reportFileSaveDTO.setDetailThumbnailFilePhysicalName(this.fileMoveTempToRealPath(reportFileSaveDTO.getThumbnailFilePhysicalName()));
        return reportFileSaveDTO;
    }

//    /**
//     * S 3 file copy update contents file save dto.
//     *
//     * @param reportFileUpdateDTO the report file update dto
//     * @return the contents file save dto
//     * @author [이소정]
//     * @implNote 보고서 수정 > 파일 경로(temp -> report) 변경 후 set
//     * @since 2020. 7. 28. 오후 4:05:42
//     */
//    public ReportFileUpdateDTO s3FileCopyUpdate(final ReportFileUpdateDTO reportFileUpdateDTO) {
//        log.info("ReportService.s3FileCopyUpdate");
//        reportFileUpdateDTO.setFilePhysicalName(this.fileMoveTempToRealPath(reportFileUpdateDTO.getFilePhysicalName()));
//        reportFileUpdateDTO.setThumbnailFilePhysicalName(this.fileMoveTempToRealPath(reportFileUpdateDTO.getThumbnailFilePhysicalName()));
//        reportFileUpdateDTO.setDetailThumbnailFilePhysicalName(this.fileMoveTempToRealPath(reportFileUpdateDTO.getThumbnailFilePhysicalName()));
//        return reportFileUpdateDTO;
//    }

    /**
     * Temp to real path file move string.
     *
     * @param filePhysicalName the file physical name
     * @return the string
     * @author [이소정]
     * @implNote 보고서 파일 경로 temp -> report
     * @since 2020. 7. 30. 오후 2:58:57
     */
    public String fileMoveTempToRealPath(final String filePhysicalName) {
        log.info("ReportService.fileMoveTempToRealPath");
        String imgPath = filePhysicalName;
        if (null  != filePhysicalName) {
            imgPath = S3Util.fileCopyAndOldFileDelete(filePhysicalName, ServiceCode.FileFolderEnumCode.REPORT.getFolder());
        }
        return imgPath;
    }

}
