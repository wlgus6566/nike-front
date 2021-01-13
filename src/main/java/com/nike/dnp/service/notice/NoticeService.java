package com.nike.dnp.service.notice;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.notice.*;
import com.nike.dnp.dto.report.ReportFileSaveDTO;
import com.nike.dnp.entity.notice.NoticeArticle;
import com.nike.dnp.entity.notice.NoticeFile;
import com.nike.dnp.entity.report.ReportFile;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.exception.NotFoundHandleException;
import com.nike.dnp.repository.notice.NoticeFileRepository;
import com.nike.dnp.repository.notice.NoticeRepository;
import com.nike.dnp.service.user.UserService;
import com.nike.dnp.util.FileUtil;
import com.nike.dnp.util.MessageUtil;
import com.nike.dnp.util.ObjectMapperUtil;
import com.nike.dnp.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.nike.dnp.common.variable.ServiceCode.NoticeArticleSectionEnumCode;

/**
 * The Class Notice service.
 *
 * @author [정주희]
 * @implNote 작성]
 * @since 2020. 7. 13. 오후 6:15:37
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {

    /**
     * The Notice repository
     *
     * @author [정주희]
     */
    private final NoticeRepository noticeRepository;

    /**
     * UserService
     *
     * @author [오지훈]
     */
    private final UserService userService;

    /**
     * The Notice file repository
     *
     * @author [이소정]
     */
    private final NoticeFileRepository noticeFileRepository;

    /**
     * The Editor url
     */
    @Value("${nike.url.pc.domain:}")
    private String cdnUrl;

    /**
     * Find notice pages page.
     *
     * @param customerSearchDTO the notice search dto
     * @return the page
     * @author [정주희]
     * @implNote Customer Center 게시글 목록 조회
     * @since 2020. 7. 20. 오후 10:07:02
     */
    public Page<CustomerListDTO> findNoticePages(final CustomerSearchDTO customerSearchDTO) {
        log.info("NoticeService.findNoticePages");

        final Page<CustomerListDTO> noticePages = noticeRepository.findNoticePages(
                customerSearchDTO, PageRequest.of(customerSearchDTO.getPage(), customerSearchDTO.getSize()));

        if (StringUtils.equalsIgnoreCase(customerSearchDTO.getNoticeArticleSectionCode(), "QNA")) {
            for (int i=0; i < noticePages.getContent().size(); i++) {
                noticePages.getContent().get(i).setNoticeArticleCategoryValue(
                        Enum.valueOf(ServiceCode.QNAEnumCode.class,
                                noticePages.getContent().get(i).getNoticeArticleCategoryCode()).getMessage());
            }
        }

        return noticePages;
    }

    /**
     * Find by id notice article.
     *
     * @param noticeSeq the notice seq
     * @return the notice article
     * @author [정주희]
     * @implNote Customer Center 상세 조회
     * @since 2020. 7. 21. 오후 4:07:10
     */
    public CustomerResultDTO findById(final Long noticeSeq) {
        log.info("NoticeService.findById");
        CustomerResultDTO result = ObjectMapperUtil.map(noticeRepository.findByNoticeArticleSeq(noticeSeq), CustomerResultDTO.class);
        result.setNickname(userService.findByUserSeq(result.getRegisterSeq()).getNickname());

        // NOTICE, NEW 인 경우에만 fileList 조회
        if (NoticeArticleSectionEnumCode.NOTICE.toString().equals(result.getNoticeArticleSectionCode())
            || NoticeArticleSectionEnumCode.NEWS.toString().equals(result.getNoticeArticleSectionCode())) {

            List<CustomerFileResultDTO> fileList = ObjectMapperUtil.mapAll(noticeFileRepository.findAllByNoticeArticleSeqAndUseYn(noticeSeq, "Y"), CustomerFileResultDTO.class);
            for (CustomerFileResultDTO customerFileResultDTO : fileList) {
                if (!ServiceCode.NoticeFileKindCode.VIDEO.toString().equals(customerFileResultDTO.getFileKindCode())) {
                    customerFileResultDTO.setFilePhysicalName(cdnUrl + customerFileResultDTO.getFilePhysicalName());
                }
            }

            result.setFileList( fileList );
        }
        return result;
    }

    /**
     * Save notice article.
     *
     * @param customerSaveDTO the notice save dto
     * @return the notice article
     * @author [정주희]
     * @implNote Customer Center 게시글 등록
     * @since 2020. 7. 20. 오후 9:21:49
     */
    @Transactional
    public NoticeArticle save(final CustomerSaveDTO customerSaveDTO) {
        log.info("NoticeService.save");

        this.checkNoticeYn(customerSaveDTO.getNoticeArticleSectionCode(),
                customerSaveDTO.getNoticeYn(), null);

        NoticeArticle savedNoticeArticle = noticeRepository.save(new NoticeArticle().customerSave(customerSaveDTO));
        List<NoticeFile> noticeFileList = new ArrayList<>();

        // NOTICE, NEW 인 경우에만 fileList 저장
        if (NoticeArticleSectionEnumCode.NOTICE.toString().equals(customerSaveDTO.getNoticeArticleSectionCode())
            || NoticeArticleSectionEnumCode.NEWS.toString().equals(customerSaveDTO.getNoticeArticleSectionCode())) {
            if (!ObjectUtils.isEmpty(customerSaveDTO.getFileList())) {
                for (CustomerFileSaveDTO customerFileSaveDTO : customerSaveDTO.getFileList()) {
                    customerFileSaveDTO.setNoticeArticleSeq(savedNoticeArticle.getNoticeArticleSeq());
                    // NOTICE 일 경우 파일 종류는 FILE
                    if (NoticeArticleSectionEnumCode.NOTICE.toString().equals(customerSaveDTO.getNoticeArticleSectionCode())) {
                        customerFileSaveDTO.setFileKindCode(ServiceCode.NoticeFileKindCode.FILE.toString());
                        customerFileSaveDTO.setDetailThumbnailFileName(null);
                        customerFileSaveDTO.setDetailThumbnailFileSize(null);
                        customerFileSaveDTO.setDetailThumbnailFilePhysicalName(null);
                    }

                    noticeFileList.add(noticeFileRepository.save(new NoticeFile().saveNoticeFile(this.s3FileCopySave(customerSaveDTO.getNoticeArticleSectionCode(), customerFileSaveDTO))));
                }
            }
        }

        savedNoticeArticle.setNoticeFileList(noticeFileList);

        return savedNoticeArticle;
    }

    /**
     * S 3 file copy save notice file save dto.
     *
     * @param cutCustomerFileSaveDTO the cut customer file save dto
     * @return the notice file save dto
     * @author [이소정]
     * @implNote 게시물 저장 > 파일 경로(temp -> contents) 변경 후 set
     * @since 2020. 12. 16. 오후 7:18:49
     */
    public CustomerFileSaveDTO s3FileCopySave(final String sectionCode, final CustomerFileSaveDTO cutCustomerFileSaveDTO) {
        log.info("ContentsService.s3FileCopySave");
        if (!ObjectUtils.isEmpty(cutCustomerFileSaveDTO.getFilePhysicalName()) && cutCustomerFileSaveDTO.getFilePhysicalName().contains("/temp/")) {
            cutCustomerFileSaveDTO.setFilePhysicalName(this.fileMoveTempToRealPath(cutCustomerFileSaveDTO.getFilePhysicalName(), ServiceCode.FileFolderEnumCode.NOTICE.getFolder()));
        }

        // news일 경우 상세 이미지 추가 작업
        if (NoticeArticleSectionEnumCode.NEWS.toString().equals(sectionCode)
            && !ObjectUtils.isEmpty(cutCustomerFileSaveDTO.getDetailThumbnailFilePhysicalName()) && cutCustomerFileSaveDTO.getDetailThumbnailFilePhysicalName().contains("/temp/")) {
            cutCustomerFileSaveDTO.setFilePhysicalName(this.fileMoveTempToRealPath(cutCustomerFileSaveDTO.getFilePhysicalName(), ServiceCode.FileFolderEnumCode.NOTICE.getFolder()));
        }

        return cutCustomerFileSaveDTO;
    }

    /**
     * Temp to real path file move string.
     *
     * @param filePhysicalName the file physical name
     * @param fileFolder       the file folder
     * @return the string
     * @author [이소정]
     * @implNote 게시물 파일 경로 temp -> contents
     * @since 2020. 7. 28. 오후 3:59:37
     */
    public String fileMoveTempToRealPath(final String filePhysicalName, final String fileFolder) {
        log.info("ContentsService.fileMoveTempToRealPath");
        String imgPath = filePhysicalName;
        if (null  != filePhysicalName) {
            imgPath = S3Util.fileCopyAndOldFileDelete(filePhysicalName, fileFolder, false);
        }
        return imgPath;
    }

    /**
     * Check notice yn cnt long.
     *
     * @param noticeArticleSeq the notice article seq
     * @return the long
     * @author [정주희]
     * @implNote 공지사항 등록시 상단 고정된 게시글 개수 확인
     * @since 2020. 7. 20. 오후 9:21:57
     */
    public Boolean checkNoticeYn(Long noticeArticleSeq) {
        log.info("NoticeService.checkNoticeYnCnt");

        final List<NoticeArticle> noticeArticles = noticeRepository.checkNoticeYn();

        final long NOTICE_MAX_COUNT = 3L;
        boolean seqCheck = false;

        if (!ObjectUtils.isEmpty(noticeArticleSeq)) {
            for (NoticeArticle notice : noticeArticles) {
                if (notice.getNoticeArticleSeq().equals(noticeArticleSeq)) {
                    seqCheck = true;
                    break;
                }
            }
        }

        if (noticeArticles.size() >= NOTICE_MAX_COUNT && !seqCheck) {
            throw new CodeMessageHandleException(FailCode.ConfigureError.EXCEED_MAX_NOTICE.name(),
                    MessageUtil.getMessage(FailCode.ConfigureError.EXCEED_MAX_NOTICE.name()));
        }

        return true;
    }

    /**
     * Update customer center notice article.
     *
     * @param noticeSeq         the notice seq
     * @param customerUpdateDTO the notice update dto
     * @return the notice article
     * @author [정주희]
     * @implNote Customer Center 게시글 수정
     * @since 2020. 7. 23. 오후 10:11:06
     */
    @Transactional
    public NoticeArticle updateCustomerCenter(Long noticeSeq, final CustomerUpdateDTO customerUpdateDTO) {
        log.info("NoticeService.updateCustomerCenter");

        this.checkNoticeYn(customerUpdateDTO.getNoticeArticleSectionCode(),
                customerUpdateDTO.getNoticeYn(), customerUpdateDTO.getNoticeArticleSeq());

        NoticeArticle updatedNoticeArticle = noticeRepository
                .findById(noticeSeq)
                .map(i -> i.update(customerUpdateDTO))
                .orElseThrow(NotFoundHandleException::new);


        final List<NoticeFile> beforeFileList = noticeFileRepository.findAllByNoticeArticleSeqAndUseYn(noticeSeq, "Y");

        final List<NoticeFile> lastBeforeFileList  = new ArrayList<>();
        if (!ObjectUtils.isEmpty(beforeFileList) && !beforeFileList.isEmpty()) {
            for (NoticeFile reportFile : beforeFileList) {
                lastBeforeFileList.add(reportFile);
            }
        }
        final List<CustomerFileSaveDTO> newFileList = customerUpdateDTO.getFileList();
        List<Long> keepFileSeqList = new ArrayList<>();

        if (!ObjectUtils.isEmpty(beforeFileList) && !beforeFileList.isEmpty()
                && !ObjectUtils.isEmpty(newFileList) && !newFileList.isEmpty()) {
            for (final NoticeFile beforeFile : beforeFileList) {
                for (final CustomerFileSaveDTO newFile : newFileList) {
                    if (beforeFile.getNoticeFileSeq().equals(newFile.getNoticeFileSeq())) {
                        keepFileSeqList.add(beforeFile.getNoticeFileSeq());
                    }
                }
            }
        }

        for (Long fileSeq : keepFileSeqList) {
            for (int i = 0; i < lastBeforeFileList.size(); i++) {
                if (fileSeq == lastBeforeFileList.get(i).getNoticeFileSeq()) {
                    lastBeforeFileList.remove(i);
                    break;
                }
            }
        }

        if (!ObjectUtils.isEmpty(newFileList) && !newFileList.isEmpty()) {
            for (final CustomerFileSaveDTO customerFileSaveDTO : newFileList) {
                final Long noticeFileSeq = null != customerFileSaveDTO.getNoticeFileSeq() ? customerFileSaveDTO.getNoticeFileSeq() : 0l;
                final Optional<NoticeFile> reportFile = noticeFileRepository.findById(noticeFileSeq);

                // NEWS 일 경우 파일 종류는 FILE
                if (NoticeArticleSectionEnumCode.NOTICE.toString().equals(updatedNoticeArticle.getNoticeArticleSectionCode())) {
                    customerFileSaveDTO.setFileKindCode(ServiceCode.NoticeFileKindCode.FILE.toString());
                    customerFileSaveDTO.setDetailThumbnailFileName(null);
                    customerFileSaveDTO.setDetailThumbnailFileSize(null);
                    customerFileSaveDTO.setDetailThumbnailFilePhysicalName(null);
                }

//                this.checkReportFileValidation(reportFileSaveDTO);
                this.s3FileCopySave(updatedNoticeArticle.getNoticeArticleSectionCode(), customerFileSaveDTO);
                customerFileSaveDTO.setNoticeArticleSeq(noticeSeq);
                final NoticeFile saveReportFile = reportFile.orElse(
                    new NoticeFile().saveNoticeFile(this.s3FileCopySave(updatedNoticeArticle.getNoticeArticleSectionCode(), customerFileSaveDTO))
                );

                if (0l != noticeFileSeq) {
                    reportFile.ifPresent(value -> value.update(customerFileSaveDTO, cdnUrl));
                } else {
                    noticeFileRepository.save(saveReportFile);
                }
            }
        }

        // 사용하지 않는 파일목록 삭제
        this.deleteNoticeFile(lastBeforeFileList);

        return updatedNoticeArticle;
    }

    /**
     * Delete notice file.
     *
     * @param noticeFileList the notice file list
     * @author [이소정]
     * @implNote [method 설명]
     * @since 2020. 12. 22. 오후 7:30:10
     */
    public void deleteNoticeFile(final List<NoticeFile> noticeFileList) {
        if (!ObjectUtils.isEmpty(noticeFileList) && !noticeFileList.isEmpty()) {
            for (final NoticeFile reportFile : noticeFileList) {
                reportFile.updateUseYn("N");
            }
        }
    }

    /**
     * Delete customer center optional.
     *
     * @param noticeSeq the notice seq
     * @return the optional
     * @author [정주희]
     * @implNote customer center 삭제 (사용 여부 == 'N')
     * @since 2020. 7. 20. 오후 10:06:54
     */
    @Transactional
    public NoticeArticle deleteCustomerCenter(Long noticeSeq) {
        log.info("NoticeService.deleteCustomerCenter");

        return noticeRepository
                .findById(noticeSeq)
                .map(NoticeArticle::delete)
                .orElseThrow(NotFoundHandleException::new);
    }

    /**
     * Upload editor images list.
     *
     * @param multiReq                 the multi req
     * @param noticeArticleSectionCode the notice article section code
     * @return noticeArticleSectionCode the notice article section code
     * @author [정주희]
     * @implNote 에디터 이미지 업로드
     * @since 2020. 8. 19. 오후 12:17:33
     */
    public String uploadEditorImages(MultipartFile multiReq, String noticeArticleSectionCode) {
        log.info("NoticeService.uploadEditorImages");

        final String ext = org.springframework.util.StringUtils.getFilenameExtension(multiReq.getOriginalFilename());
        final String awsPath = "editor/" + noticeArticleSectionCode + "/" + FileUtil.makeFileName() + "." + ext;

        String uploadUrl = null;
        try {
            uploadUrl = S3Util.editorUpload(multiReq, awsPath);
        } catch (IOException e) {
            throw (CodeMessageHandleException) new CodeMessageHandleException(FailCode.ConfigureError.INVALID_FILE.name(), MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
        }

        return cdnUrl + uploadUrl;
    }

    /**
     * Check notice yn.
     *
     * @param code the code
     * @param isYn the is yn
     * @param seq  the seq
     * @return isYn the is yn
     * @author [정주희]
     * @implNote 등록 /수정 버튼 클릭시 고정게시글 개수 체크
     * @since 2020. 8. 19. 오후 12:17:33
     */
    private void checkNoticeYn(final String code, final String isYn, final Long seq){
        if(StringUtils.equalsIgnoreCase(code, "NOTICE") && StringUtils.equalsIgnoreCase(isYn, "Y")){
            this.checkNoticeYn(seq);
        }
    }
}
