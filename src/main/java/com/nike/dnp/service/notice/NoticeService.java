package com.nike.dnp.service.notice;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.dto.notice.CustomerListDTO;
import com.nike.dnp.dto.notice.CustomerSaveDTO;
import com.nike.dnp.dto.notice.CustomerSearchDTO;
import com.nike.dnp.dto.notice.CustomerUpdateDTO;
import com.nike.dnp.entity.notice.NoticeArticle;
import com.nike.dnp.repository.notice.NoticeRepository;
import com.nike.dnp.util.ImageUtil;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.util.Optional;

/**
 * The Class Notice service.
 *
 * @author [정주희]
 * @CreatedOn 2020. 7. 13. 오후 6:15:37
 * @Description
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    private static String root;

    private static String bucket;

    private final S3Util client;

    @Value("${nike.file.root:}")
    public void setRoot(final String root){
        this.root = root;
    }

    @Value("${cloud.aws.s3.bucket:}")
    public void setBucket(final String bucket) {
        this.bucket = bucket;
    }


    /**
     * Find notice pages page.
     *
     * @param customerSearchDTO the notice search dto
     * @return the page
     * @author [정주희]
     * @CreatedOn 2020. 7. 20. 오후 10:07:02
     * @Description Customer Center 게시글 목록 조회
     */
    public Page<CustomerListDTO> findNoticePages(final CustomerSearchDTO customerSearchDTO) {
        log.info("NoticeService.findNoticePages");

        Page<CustomerListDTO> noticeArticles = noticeRepository.findNoticePages(
                customerSearchDTO, PageRequest.of(customerSearchDTO.getPage(), customerSearchDTO.getSize()));

        return noticeArticles;
    }

    /**
     * Find by id notice article.
     *
     * @param noticeSeq the notice seq
     * @return the notice article
     * @author [정주희]
     * @CreatedOn 2020. 7. 21. 오후 4:07:10
     * @Description Customer Center 상세 조회
     */
    @Transactional
    public NoticeArticle findById(final Long noticeSeq) {
        log.info("NoticeService.findById");

        return noticeRepository.findByNoticeArticleSeq(noticeSeq);
    }

    /**
     * Save notice article.
     *
     * @param customerSaveDTO the notice save dto
     * @return the notice article
     * @author [정주희]
     * @CreatedOn 2020. 7. 20. 오후 9:21:49
     * @Description Customer Center 게시글 등록
     */
    @Transactional
    public NoticeArticle save(final CustomerSaveDTO customerSaveDTO) {
        log.info("NoticeService.save");

        NoticeArticle noticeArticle = new NoticeArticle();

        noticeArticle.setNoticeArticleSectionCode(customerSaveDTO.getNoticeArticleSectionCode());
        noticeArticle.setTitle(customerSaveDTO.getTitle());
        noticeArticle.setContents(customerSaveDTO.getContents());
        noticeArticle.setUseYn(customerSaveDTO.getUseYn());
        noticeArticle.setRegisterSeq(customerSaveDTO.getRegisterSeq());
        noticeArticle.setUpdaterSeq(customerSaveDTO.getRegisterSeq());

        if (StringUtils.equals(customerSaveDTO.getNoticeArticleSectionCode(), "NOTICE")) {
            noticeArticle.setNoticeYn(customerSaveDTO.getNoticeYn());
        } else if (StringUtils.equals(customerSaveDTO.getNoticeArticleSectionCode(), "NEWS")) {
            if (!ObjectUtils.isEmpty(customerSaveDTO.getImageBase64())) {
                FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(
                        ServiceCode.FileFolderEnumCode.QNA.getFolder(), customerSaveDTO.getImageBase64());

                noticeArticle.setThumbnailFileName(fileResultDTO.getFileName());
                noticeArticle.setThumbnailFilePhysicalName(fileResultDTO.getFilePhysicalName());
                noticeArticle.setThumbnailFileSize(String.valueOf(fileResultDTO.getFileSize()));
            }
        } else if (StringUtils.equals(customerSaveDTO.getNoticeArticleSectionCode(), "QNA")) {
            noticeArticle.setNoticeArticleCategoryCode(customerSaveDTO.getNoticeArticleCategoryCode());
        }

        return noticeRepository.save(noticeArticle);
    }

    /**
     * Check notice yn cnt long.
     *
     * @return the long
     * @author [정주희]
     * @CreatedOn 2020. 7. 20. 오후 9:21:57
     * @Description 공지사항 등록시 상단 고정된 게시글 개수 확인
     */
    public Long checkNoticeYnCnt() {
        log.info("NoticeService.checkNoticeYnCnt");

        return noticeRepository.checkNoticeYnCnt();
    }

    /**
     * Update customer center notice article.
     *
     * @param customerUpdateDTO the notice update dto
     * @return the notice article
     * @author [정주희]
     * @CreatedOn 2020. 7. 23. 오후 10:11:06
     * @Description Customer Center 게시글 수정
     */
    @Transactional
    public NoticeArticle updateCustomerCenter(final CustomerUpdateDTO customerUpdateDTO) {
        log.info("NoticeService.updateCustomerCenter");

        final Optional<NoticeArticle> updateNotice = noticeRepository.findById(customerUpdateDTO.getNoticeArticleSeq());

        final NoticeArticle noticeArticle = updateNotice.orElse(new NoticeArticle());

        noticeArticle.setNoticeArticleSectionCode(customerUpdateDTO.getNoticeArticleSectionCode());
        noticeArticle.setTitle(customerUpdateDTO.getTitle());
        noticeArticle.setContents(customerUpdateDTO.getContents());
        noticeArticle.setUseYn(customerUpdateDTO.getUseYn());

        if (StringUtils.equals(customerUpdateDTO.getNoticeArticleSectionCode(), "NOTICE")) {
            noticeArticle.setNoticeYn(customerUpdateDTO.getNoticeYn());
        } else if (StringUtils.equals(customerUpdateDTO.getNoticeArticleSectionCode(), "NEWS")) {
            if (!ObjectUtils.isEmpty(customerUpdateDTO.getImageBase64())) {
                FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(
                        ServiceCode.FileFolderEnumCode.QNA.getFolder(), customerUpdateDTO.getImageBase64());

                noticeArticle.setThumbnailFileName(fileResultDTO.getFileName());
                noticeArticle.setThumbnailFilePhysicalName(fileResultDTO.getFilePhysicalName());
                noticeArticle.setThumbnailFileSize(String.valueOf(fileResultDTO.getFileSize()));
            }
        } else if (StringUtils.equals(customerUpdateDTO.getNoticeArticleSectionCode(), "QNA")) {
            noticeArticle.setNoticeArticleCategoryCode(customerUpdateDTO.getNoticeArticleCategoryCode());
        }

        return noticeRepository.save(noticeArticle);
    }

    /**
     * Delete customer center optional.
     *
     * @param customerUpdateDTO the notice update dto
     * @return the optional
     * @author [정주희]
     * @CreatedOn 2020. 7. 20. 오후 10:06:54
     * @Description customer center 삭제 (사용 여부 == 'N')
     */
    @Transactional
    public NoticeArticle deleteCustomerCenter(final CustomerUpdateDTO customerUpdateDTO) {
        log.info("NoticeService.deleteCustomerCenter");

        final Optional<NoticeArticle> deleteNotice = noticeRepository.findById(customerUpdateDTO.getNoticeArticleSeq());
        final NoticeArticle noticeArticle = deleteNotice.orElse(new NoticeArticle());

        noticeArticle.setUseYn(customerUpdateDTO.getUseYn());

        return noticeRepository.save(noticeArticle);
    }

    public void uploadEditorImages(String sectionCode, MultipartHttpServletRequest multiReq) throws IOException {
        log.info("NoticeService.uploadEditorImages");

        final String pathUrl = "/temp/";
        MultipartFile mf = multiReq.getFile("editor");
        File file = new File(pathUrl + mf.getOriginalFilename());

        try (
                InputStream inputStream = mf.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
        ) {
            int bis = 0;
            while ((bis = bufferedInputStream.read()) != -1) {
                fileOutputStream.write(bis);
            }
        }

        //TODO[jjh] s3 파일 업로드 하고 file 삭제하기

    }
}
