package com.nike.dnp.service.notice;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.notice.*;
import com.nike.dnp.entity.notice.NoticeArticle;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.exception.NotFoundHandleException;
import com.nike.dnp.repository.notice.NoticeRepository;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Class Notice service.
 *
 * @author [정주희]
 * @since 2020. 7. 13. 오후 6:15:37
 * @implNote
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

    @Value("${nike.file.editorUrl:}")
    private String editorUrl;

    /**
     * Find notice pages page.
     *
     * @param customerSearchDTO the notice search dto
     * @return the page
     * @author [정주희]
     * @since 2020. 7. 20. 오후 10:07:02
     * @implNote Customer Center 게시글 목록 조회
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
     * @since 2020. 7. 21. 오후 4:07:10
     * @implNote Customer Center 상세 조회
     */
    public CustomerResultDTO findById(final Long noticeSeq) {
        log.info("NoticeService.findById");

        return ObjectMapperUtil.map(noticeRepository.findByNoticeArticleSeq(noticeSeq), CustomerResultDTO.class);
    }

    /**
     * Save notice article.
     *
     * @param customerSaveDTO the notice save dto
     * @return the notice article
     * @author [정주희]
     * @since 2020. 7. 20. 오후 9:21:49
     * @implNote Customer Center 게시글 등록
     */
    @Transactional
    public NoticeArticle save(final CustomerSaveDTO customerSaveDTO) {
        log.info("NoticeService.save");

        this.checkNoticeYn(customerSaveDTO.getNoticeArticleSectionCode(), customerSaveDTO.getNoticeYn());

        return noticeRepository.save(new NoticeArticle().customerSave(customerSaveDTO));
    }

    /**
     * Check notice yn cnt long.
     *
     * @return the long
     * @author [정주희]
     * @since 2020. 7. 20. 오후 9:21:57
     * @implNote 공지사항 등록시 상단 고정된 게시글 개수 확인
     */
    public Long checkNoticeYnCnt() {
        log.info("NoticeService.checkNoticeYnCnt");

        final long count = Math.toIntExact(noticeRepository.checkNoticeYnCnt());

        final long NOTICE_MAX_COUNT = 3L;

        if (count >= NOTICE_MAX_COUNT) {
            throw new CodeMessageHandleException(FailCode.ConfigureError.EXCEED_MAX_NOTICE.name(),
                    MessageUtil.getMessage(FailCode.ConfigureError.EXCEED_MAX_NOTICE.name()));
        }

        return noticeRepository.checkNoticeYnCnt();
    }

    /**
     * Update customer center notice article.
     *
     *
     * @param noticeSeq
     * @param customerUpdateDTO the notice update dto
     * @return the notice article
     * @author [정주희]
     * @since 2020. 7. 23. 오후 10:11:06
     * @implNote Customer Center 게시글 수정
     */
    @Transactional
    public NoticeArticle updateCustomerCenter(Long noticeSeq, final CustomerUpdateDTO customerUpdateDTO) {
        log.info("NoticeService.updateCustomerCenter");

        this.checkNoticeYn(customerUpdateDTO.getNoticeArticleSectionCode(), customerUpdateDTO.getNoticeYn());

        return noticeRepository
                .findById(noticeSeq)
                .map(i -> i.update(customerUpdateDTO))
                .orElseThrow(NotFoundHandleException::new); //error code : NotFoundException
    }

    /**
     * Delete customer center optional.
     *
     *
     * @param noticeSeq
     * @return the optional
     * @author [정주희]
     * @since 2020. 7. 20. 오후 10:06:54
     * @implNote customer center 삭제 (사용 여부 == 'N')
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
     *
     * @param multiReq                 the multi req             
     * @return noticeArticleSectionCode the notice article section code
     * @author [정주희]
     * @since 2020. 8. 19. 오후 12:17:33
     * @implNote 에디터 이미지 업로드
     */
    public List<String> uploadEditorImages(MultipartHttpServletRequest multiReq, String noticeArticleSectionCode) {
        log.info("NoticeService.uploadEditorImages");

        final Iterator<String> fileNames = multiReq.getFileNames();
        List<String> imageUrl = new ArrayList<>();

        while (fileNames.hasNext()) {
            String name = fileNames.next();
            MultipartFile mf = multiReq.getFile(name);

            final String ext = org.springframework.util.StringUtils.getFilenameExtension(mf.getOriginalFilename());
            final String awsPath = "editor/" + noticeArticleSectionCode + "/" + FileUtil.makeFileName() + "." + ext;

            String uploadUrl = null;
            try {
                uploadUrl = S3Util.editorUpload(mf, awsPath);
            } catch (IOException e) {
                e.printStackTrace(); //code exception
            }

            imageUrl.add(editorUrl + uploadUrl);

        }

        return imageUrl;
    }

    private void checkNoticeYn(final String code, final String isYn){
        if(StringUtils.equalsIgnoreCase(code, "NOTICE") && StringUtils.equalsIgnoreCase(isYn, "Y")){
            this.checkNoticeYnCnt();
        }
    }
}
