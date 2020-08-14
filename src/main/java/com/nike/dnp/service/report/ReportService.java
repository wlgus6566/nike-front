package com.nike.dnp.service.report;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.dto.contents.ContentsSaveDTO;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.dto.report.ReportFileSaveDTO;
import com.nike.dnp.dto.report.ReportResultDTO;
import com.nike.dnp.dto.report.ReportSaveDTO;
import com.nike.dnp.dto.report.ReportSearchDTO;
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
import java.util.Objects;
import java.util.Optional;

/**
 * The Class Report service.
 *
 * @author [이소정]
 * @implNote 보고서 서비스
 * @since 2020. 7. 7. 오후 2:40:15
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
     * The Report basket service
     *
     * @author [이소정]
     */
    private final ReportBasketService reportBasketService;

    /**
     * Find all paging page.
     *
     * @param reportSearchDTO the report search dto
     * @return the page
     * @author [이소정]
     * @implNote 보고서 페이징 처리 된 목록 조회
     * @since 2020. 7. 8. 오후 5:28:17
     */
    public Page<ReportResultDTO> findAllPaging(final ReportSearchDTO reportSearchDTO) {
        log.info("ReportService.findAllPaging");
        // 권한 검색 조건
        final List<Long> authSeqList = new ArrayList<>();
        if (null != reportSearchDTO.getGroupSeq()) {
            authSeqList.add(reportSearchDTO.getGroupSeq());
        } else {
            final List<AuthReturnDTO> authList = authService.findByAuthDepth(SecurityUtil.currentUser().getAuthSeq(), "REPORT_UPLOAD", ServiceCode.MenuSkillEnumCode.REPORT.toString());
            for (final AuthReturnDTO authReturnDTO : authList) {
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

        this.checkReportValidation(reportSaveDTO);
        // 썸네일 base64 -> file 정보로 변환
        this.base64ToFile(reportSaveDTO);

        final Report savedReport = reportRepository.save(new Report().save(reportSaveDTO));
        final List<ReportFile> reportFileList = new ArrayList<>();

        if (!ObjectUtils.isEmpty(reportSaveDTO.getReportFileSaveDTOList()) && !reportSaveDTO.getReportFileSaveDTOList().isEmpty()) {
            for (final ReportFileSaveDTO reportFileSaveDTO : reportSaveDTO.getReportFileSaveDTOList()) {
                this.checkReportFileValidation(reportFileSaveDTO);
                final ReportFile savedReportFile = reportFileRepository.save(
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
    public ReportResultDTO findByReportSeq(final Long reportSeq) {
        log.info("ReportService.findByReportSeq");
        final Optional<Report> report = reportRepository.findByReportSeq(reportSeq);
        final Report findReport = report.orElseThrow(
                () -> new CodeMessageHandleException(FailCode.ExceptionError.NOT_FOUND.name(), MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name())));

        findReport.updateReadCount(findReport.getReadCount());

        // history 저장
        historyService.saveViewHistory(reportSeq, ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString());
        return reportRepository.findReportWithUserName(reportSeq);
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
    public ReportSaveDTO base64ToFile(final ReportSaveDTO reportSaveDTO) {
        log.info("reportService.base64ToFile");
        // 썸네일 base64 -> file 정보로 변환
        if (!ObjectUtils.isEmpty(reportSaveDTO.getImageBase64())) {
            final FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(ServiceCode.FileFolderEnumCode.REPORT.getFolder(), reportSaveDTO.getImageBase64());

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
        // 썸네일 base64 -> file 정보로 변환
        if(!ObjectUtils.isEmpty(reportSaveDTO.getImageBase64()) && reportSaveDTO.getImageBase64().contains("base64")){
            this.base64ToFile(reportSaveDTO);
        }

        report.ifPresent(value -> value.update(reportSaveDTO));

        final List<ReportFile> beforeFileList = reportFileRepository.findByReportSeqAndUseYn(reportSaveDTO.getReportSeq(), "Y");

        final List<ReportFile> lastBeforeFileList  = new ArrayList<>();
        if (!ObjectUtils.isEmpty(beforeFileList) && !beforeFileList.isEmpty()) {
            for (ReportFile reportFile : beforeFileList) {
                lastBeforeFileList.add(reportFile);
            }
        }
        final List<ReportFileSaveDTO> newFileList = reportSaveDTO.getReportFileSaveDTOList();

        if (!ObjectUtils.isEmpty(beforeFileList) && !beforeFileList.isEmpty()
                && !ObjectUtils.isEmpty(newFileList) && !newFileList.isEmpty()) {
            for (final ReportFile beforeFile : beforeFileList) {
                for (final ReportFileSaveDTO newFile : newFileList) {
                    if (beforeFile.getReportFileSeq() == newFile.getReportFileSeq()) {
                        lastBeforeFileList.remove(beforeFile);
                    }
                }
            }
        }

        if (!ObjectUtils.isEmpty(newFileList) && !newFileList.isEmpty()) {
            for (final ReportFileSaveDTO reportFileSaveDTO : newFileList) {
                final Long reportFileSeq = null != reportFileSaveDTO.getReportFileSeq() ? reportFileSaveDTO.getReportFileSeq() : 0l;
                final Optional<ReportFile> reportFile = reportFileRepository.findById(reportFileSeq);

                this.checkReportFileValidation(reportFileSaveDTO);
                this.s3FileCopySave(reportFileSaveDTO);
                final ReportFile saveReportFile = reportFile.orElse(
                        new ReportFile().save(report.get().getReportSeq(), reportFileSaveDTO)
                );

                if (0l != reportFileSeq) {
                    reportFile.ifPresent(value -> value.update(reportFileSaveDTO));
                } else {
                    reportFileRepository.save(saveReportFile);
                }
            }
        }

        // 사용하지 않는 파일목록, 장바구니 삭제
        this.deleteReportFile(lastBeforeFileList);

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
        final Optional<Report> report = this.findById(reportSeq);
        final Report savedReport = report.get();
        report.ifPresent(value -> value.updateUseYn("N"));

        this.deleteReportFile(savedReport.getReportFileList());

        // 최근 본, 업로드 폴더 삭제
        historyService.deleteViewHistory(savedReport.getReportSeq(), ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString());
        historyService.deleteUploadHistory(savedReport.getReportSeq(), ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString());

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
        final Optional<ReportFile> reportFile = this.findByFileId(reportFileSeq);
        if (reportFile.isPresent()) {
            reportFile.ifPresent(value -> value.updateDownloadCount(reportFile.get().getDownloadCount()));
            return FileUtil.fileDownload(reportFile.get().getFilePhysicalName());
        } else {
            return null;
        }
    }

    /**
     * Check report validation.
     *
     * @param reportSaveDTO the report save dto
     * @author [이소정]
     * @implNote 보고서 유효성 체크
     * @since 2020. 8. 14. 오후 1:56:40
     */
    public void checkReportValidation(final ReportSaveDTO reportSaveDTO) {
        log.info("ContentsService.checkContentsValidation");
        // 등록인 경우, base64 필수
        if (ObjectUtils.isEmpty(reportSaveDTO.getImageBase64())) {
            throw new CodeMessageHandleException(FailCode.ConfigureError.NULL_FOLDER_IMAGE.name(),
                    MessageUtil.getMessage(FailCode.ConfigureError.NULL_FOLDER_IMAGE.name()));
        }
    }

    /**
     * Check report file validation.
     *
     * @param reportFileSaveDTO the report file save dto
     * @author [이소정]
     * @implNote
     * @since 2020. 8. 13. 오후 7:07:34
     */
    public void checkReportFileValidation(final ReportFileSaveDTO reportFileSaveDTO) {
        log.info("ReportService.checkReportFileValidation");
        // 새로 등록한 파일 인 경우에만 validation check
        if (!ObjectUtils.isEmpty(reportFileSaveDTO.getFilePhysicalName()) && reportFileSaveDTO.getFilePhysicalName().contains("/temp/")) {
            if (ObjectUtils.isEmpty(reportFileSaveDTO.getFileName())
                    || Objects.isNull(reportFileSaveDTO.getFileSize())
                    || ObjectUtils.isEmpty(reportFileSaveDTO.getFilePhysicalName())) {
                throw new CodeMessageHandleException(FailCode.ConfigureError.SELECT_FILE.name(),
                        MessageUtil.getMessage(FailCode.ConfigureError.SELECT_FILE.name()));
            }
        }
    }

    /**
     * Delete report file.
     *
     * @param reportFileList the report file list
     * @author [이소정]
     * @implNote 보고서 파일, 장바구니 삭제
     * @since 2020. 8. 3. 오후 6:02:16
     */
    public void deleteReportFile(final List<ReportFile> reportFileList) {
        if (!ObjectUtils.isEmpty(reportFileList) && !reportFileList.isEmpty()) {
            for (final ReportFile reportFile : reportFileList) {
                reportFile.updateUseYn("N");
                // 관련 보고서 장바구니 삭제
                reportBasketService.deleteByReportFileSeq(reportFile.getReportFileSeq());
            }
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
        final UserContentsSearchDTO userContentsSearchDTO = new UserContentsSearchDTO();
        userContentsSearchDTO.setMenuCode(ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString());
        userContentsSearchDTO.setSkillCode(ServiceCode.MenuSkillEnumCode.VIEW.toString());
        final List<AuthReturnDTO> authList = authService.getAuthList(userContentsSearchDTO);

        final List<Long> userSeqList  = new ArrayList<>();
        for (final AuthReturnDTO authReturnDTO : authList) {
            // authSeq 를 가지고 userSeq 목록 가져오기
            final List<UserAuth> userAuthList = userAuthRepository.findAllByAuthSeq(authReturnDTO.getAuthSeq());
            for (final UserAuth userAuth : userAuthList) {
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
        if (!ObjectUtils.isEmpty(reportFileSaveDTO.getFilePhysicalName()) && reportFileSaveDTO.getFilePhysicalName().contains("/temp/")) {
            reportFileSaveDTO.setFilePhysicalName(this.fileMoveTempToRealPath(reportFileSaveDTO.getFilePhysicalName()));
            reportFileSaveDTO.setThumbnailFilePhysicalName(this.fileMoveTempToRealPath(reportFileSaveDTO.getThumbnailFilePhysicalName()));
            reportFileSaveDTO.setDetailThumbnailFilePhysicalName(this.fileMoveTempToRealPath(reportFileSaveDTO.getDetailThumbnailFilePhysicalName()));
        }
        return reportFileSaveDTO;
    }

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
