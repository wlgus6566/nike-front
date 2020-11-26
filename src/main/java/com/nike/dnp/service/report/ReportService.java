package com.nike.dnp.service.report;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.dto.report.ReportFileSaveDTO;
import com.nike.dnp.dto.report.ReportResultDTO;
import com.nike.dnp.dto.report.ReportSaveDTO;
import com.nike.dnp.dto.report.ReportSearchDTO;
import com.nike.dnp.dto.user.UserAuthSearchDTO;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.entity.report.Report;
import com.nike.dnp.entity.report.ReportFile;
import com.nike.dnp.entity.user.UserAuth;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.exception.NotFoundHandleException;
import com.nike.dnp.repository.report.ReportFileRepository;
import com.nike.dnp.repository.report.ReportRepository;
import com.nike.dnp.repository.user.UserAuthRepository;
import com.nike.dnp.service.DeviceService;
import com.nike.dnp.service.alarm.AlarmService;
import com.nike.dnp.service.auth.AuthService;
import com.nike.dnp.service.history.HistoryService;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
     * The Device service
     *
     * @author [이소정]
     */
    private final DeviceService deviceService;

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
        final UserAuthSearchDTO userAuthSearchDTO = new UserAuthSearchDTO();
        userAuthSearchDTO.setMenuCode(ServiceCode.MenuCode.REPORT_UPLOAD.toString());
        userAuthSearchDTO.setSkillCode(ServiceCode.MenuSkillEnumCode.REPORT.toString());
        if (ObjectUtils.isEmpty(reportSearchDTO.getGroupSeq())) {
            userAuthSearchDTO.setAuthSeq(SecurityUtil.currentUser().getAuthSeq());
            userAuthSearchDTO.setSearchYn("N");
        } else {
            userAuthSearchDTO.setAuthSeq(reportSearchDTO.getGroupSeq());
            userAuthSearchDTO.setSearchYn("Y");
        }

        List<AuthReturnDTO> authList = this.findAllAuthListWithDepth(userAuthSearchDTO, "Y");
        List<Long> authSeqList = this.authDepthToList(authList);

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
     * @param request       the request
     * @return the report
     * @author [이소정]
     * @implNote 보고서 저장
     * @since 2020. 7. 8. 오후 5:28:20
     */
    @Transactional
    public Report save(final ReportSaveDTO reportSaveDTO, final HttpServletRequest request) {
        log.info("ReportService.save");
        reportSaveDTO.setAuthSeq(SecurityUtil.currentUser().getAuthSeq());

        this.checkReportValidation(reportSaveDTO);
        // 썸네일 base64 -> file 정보로 변환
        if (!ObjectUtils.isEmpty(reportSaveDTO.getImageBase64())) {
            final FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(ServiceCode.FileFolderEnumCode.REPORT.getFolder(), reportSaveDTO.getImageBase64());

            reportSaveDTO.setImageFileName(fileResultDTO.getFileName());
            reportSaveDTO.setImageFileSize(String.valueOf(fileResultDTO.getFileSize()));
            reportSaveDTO.setImageFilePhysicalName(fileResultDTO.getFilePhysicalName().replace("//", "/"));
        }

        reportSaveDTO.setDevice(deviceService.checkDevice(request));

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
        this.sendAlarmReport(ServiceCode.AlarmActionEnumCode.NEW.toString(), savedReport.getReportSeq());

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
                () -> new NotFoundHandleException());

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
//    public FileResultDTO base64ToFile(final ReportSaveDTO reportSaveDTO) {
//        log.info("reportService.base64ToFile");
//        // 썸네일 base64 -> file 정보로 변환
//        if (!ObjectUtils.isEmpty(reportSaveDTO.getImageBase64())) {
//            final FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(ServiceCode.FileFolderEnumCode.REPORT.getFolder(), reportSaveDTO.getImageBase64());
//
//            reportSaveDTO.setImageFileName(fileResultDTO.getFileName());
//            reportSaveDTO.setImageFileSize(String.valueOf(fileResultDTO.getFileSize()));
//            reportSaveDTO.setImageFilePhysicalName(fileResultDTO.getFilePhysicalName());
//        }
//        return reportSaveDTO;
//    }

    /**
     * Update optional.
     *
     * @param reportSaveDTO the report save dto
     * @return the optional
     * @author [이소정]
     * @implNote 설명] 설명]
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
            final FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(ServiceCode.FileFolderEnumCode.REPORT.getFolder(), reportSaveDTO.getImageBase64());

            reportSaveDTO.setImageFileName(fileResultDTO.getFileName());
            reportSaveDTO.setImageFileSize(String.valueOf(fileResultDTO.getFileSize()));
            reportSaveDTO.setImageFilePhysicalName(fileResultDTO.getFilePhysicalName().replace("//", "/"));
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
                    if (beforeFile.getReportFileSeq().equals(newFile.getReportFileSeq())) {
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
        this.sendAlarmReport(ServiceCode.AlarmActionEnumCode.UPDATE.toString(), reportSaveDTO.getReportSeq());

        return report.get();
    }

    /**
     * Send alarm report.
     *
     * @param alarmActionCode the alarm action code
     * @param reportSeq       the report seq
     * @author [이소정]
     * @implNote 리포트 알림 전송
     * @since 2020. 8. 29. 오후 6:11:36
     */
    @Transactional
    public void sendAlarmReport(final String alarmActionCode, final Long reportSeq) {
        final Long authSeq = SecurityUtil.currentUser().getAuthSeq();
        Optional<Auth> authOptional= authService.findById(authSeq);
        if (1 != authOptional.get().getAuthDepth()) {
            alarmService.sendAlarmTargetList(
                    alarmActionCode
                    , ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString()
                    , null
                    , reportSeq
                    , this.findAllAuthUser(authSeq, authOptional.get().getAuthDepth()));
        }
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
     * @param beforeDate the before date
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
     * @throws IOException the io exception
     * @author [이소정]
     * @implNote 보고서 파일 다운로드
     * @since 2020. 7. 31. 오후 3:24:10
     */
    @Transactional
    public ResponseEntity<Resource> downloadFile(final Long reportFileSeq) throws IOException {
        log.info("ReportService.downloadFile");
        final Optional<ReportFile> reportFile = this.findByFileId(reportFileSeq);
        if (reportFile.isPresent()) {
            reportFile.ifPresent(value -> value.updateDownloadCount(reportFile.get().getDownloadCount()));
            return FileUtil.s3FileDownload(reportFile.get().getFilePhysicalName(), reportFile.get().getFileName());
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
     * @implNote 설명] 설명]
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
     * @param authSeq   the auth seq
     * @param authDepth the auth depth
     * @return the list
     * @author [이소정]
     * @implNote 보고서 상세 권한 있고 depth에 맞는 그룹의 회원 목록
     * @since 2020. 7. 24. 오후 8:20:01
     */
    public List<Long> findAllAuthUser(final Long authSeq, final Long authDepth) {
        UserAuthSearchDTO userAuthSearchDTO = new UserAuthSearchDTO();
        userAuthSearchDTO.setMenuCode(ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString());
        userAuthSearchDTO.setSkillCode(ServiceCode.MenuSkillEnumCode.VIEW.toString());
        final List<AuthReturnDTO> authList = authService.findByConfig(userAuthSearchDTO);

        final List<Long> reportViewAuthList = this.findUpperAuthList(authSeq, authDepth, authList);

        final List<Long> userSeqList  = new ArrayList<>();
        for (final Long findAuthSeq : reportViewAuthList) {
            // authSeq 를 가지고 userSeq 목록 가져오기
            final List<UserAuth> userAuthList = userAuthRepository.findAllByAuthSeq(findAuthSeq);
            for (final UserAuth userAuth : userAuthList) {
                userSeqList.add(userAuth.getUserSeq());
            }
        }
        return userSeqList;
    }

    /**
     * Find upper auth list list.
     *
     * @param authSeq   the auth seq
     * @param authDepth the auth depth
     * @param authList  the auth list
     * @return the list
     * @author [이소정]
     * @implNote 권한에 맞는 목록 저장
     * @since 2020. 8. 29. 오후 6:41:39
     */
    public List<Long> findUpperAuthList(final Long authSeq, final Long authDepth, final List<AuthReturnDTO> authList) {
        List<Long> upperAuthList = new ArrayList<>();
        for (AuthReturnDTO authReturnDTO : authList) {
            if (authSeq.equals(authReturnDTO.getAuthSeq())) {
                if (2 == authDepth) {
                    upperAuthList.add(authReturnDTO.getAuthSeq());
                    Long oneDepth = this.findUpperAuthSeq(authReturnDTO.getAuthSeq(), authList);
                    if (null != oneDepth) {
                        upperAuthList.add(oneDepth);
                    }
                    break;
                } else {
                    Long twoDepth = this.findUpperAuthSeq(authReturnDTO.getAuthSeq(), authList);
                    if (null != twoDepth) {
                        upperAuthList.add(twoDepth);
                    }
                    Long threeDepth = this.findUpperAuthSeq(authReturnDTO.getUpperAuthSeq(), authList);
                    if (null != threeDepth) {
                        upperAuthList.add(threeDepth);
                    }
                    break;
                }
            }
        }
        return upperAuthList;
    }

    /**
     * Find upper auth seq long.
     *
     * @param authSeq  the auth seq
     * @param authList the auth list
     * @return the long
     * @author [이소정]
     * @implNote authseq로 상위 권한 seq 찾기
     * @since 2020. 8. 29. 오후 6:41:51
     */
    public Long findUpperAuthSeq(final Long authSeq,final List<AuthReturnDTO> authList) {
        Long upperAuthSeq = null;
        for (AuthReturnDTO authReturnDTO : authList) {
            if (authSeq.equals(authReturnDTO.getAuthSeq())) {
                upperAuthSeq = authReturnDTO.getUpperAuthSeq();
            }
        }
        return upperAuthSeq;
    }


    /**
     * Find all auth user with depth list.
     *
     * @param userAuthSearchDTO the user auth search dto
     * @param onlySkillCode     the only skill code
     * @return the list
     * @author [이소정]
     * @implNote 권한 목록 조회
     * @since 2020. 8. 26. 오후 4:52:23
     */
    public List<AuthReturnDTO> findAllAuthListWithDepth(
            final UserAuthSearchDTO userAuthSearchDTO, final String onlySkillCode
    ) {
        return authService.getAuthListWithDepth(
                userAuthSearchDTO
                , authService.getById(userAuthSearchDTO.getAuthSeq())
                , onlySkillCode
        );
    }

    /**
     * Auth depth to list list.
     *
     * @param authReturnDTOList the auth return dto list
     * @return the list
     * @author [이소정]
     * @implNote [method 설명]
     * @since 2020. 11. 24. 오후 7:24:14
     */
    public List<Long> authDepthToList(List<AuthReturnDTO> authReturnDTOList) {
        List<Long> authSeqList = new ArrayList<>();

        if (!ObjectUtils.isEmpty(authReturnDTOList)) {
            for (AuthReturnDTO authReturnDTO : authReturnDTOList) {
                authSeqList.add(authReturnDTO.getAuthSeq());
                if (!ObjectUtils.isEmpty(authReturnDTO.getSubAuths())) {
                    for (AuthReturnDTO depth2 : authReturnDTO.getSubAuths()) {
                        authSeqList.add(depth2.getAuthSeq());
                        if (!ObjectUtils.isEmpty(depth2.getSubAuths())) {
                            for (AuthReturnDTO depth3 : depth2.getSubAuths()) {
                                authSeqList.add(depth3.getAuthSeq());
                            }
                        }

                    }
                }
            }
        }

        return authSeqList;
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
                () -> new NotFoundHandleException()));
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
                () -> new NotFoundHandleException()));
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
