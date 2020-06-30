package com.nike.dnp.service.contents;

import com.nike.dnp.common.variable.EnumCode;
import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.contents.ContentsFileSaveDTO;
import com.nike.dnp.dto.contents.ContentsSaveDTO;
import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.contents.ContentsFile;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.contents.ContentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Contents Service
 *
 * @author [이소정]
 * @CreatedOn 2020. 6. 11. 오후 3:25:23
 * @Description Contents Service 작성
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentsService {

    /**
     * 콘텐츠 repository
     * @author [이소정]
     */
    private final ContentsRepository contentsRepository;

    /**
     * 전체조회(paging)
     *
     * @param contentsSearchDTO the contents search dto
     * @return the list
     */
    public Page<Contents> findAllPaging(final ContentsSearchDTO contentsSearchDTO) {
        // QueryDsl 기능 이용
        return contentsRepository.findAlls(
                contentsSearchDTO,
                PageRequest.of(contentsSearchDTO.getPage()
                        , contentsSearchDTO.getSize()
                        , contentsSearchDTO.equals(EnumCode.SearchEnumCode.START_DATE.toString())
                                ? Sort.by("campaignBeginDt").ascending() : Sort.by("contentsSeq").descending()));
    }

    /**
     * Save contents.
     *
     * @param contentsSaveDTO the contents save dto
     * @param authUserDTO     the auth user dto
     * @return the contents
     * @author [이소정]
     * @CreatedOn 2020. 6. 24. 오후 3:22:15
     * @Description
     */
    @Transactional
    public Contents save(
            final ContentsSaveDTO contentsSaveDTO
            , final AuthUserDTO authUserDTO
    ) {
        final Contents contentsEntity = new Contents();
        contentsEntity.setTopMenuCode(contentsSaveDTO.getTopMenuCode());
        contentsEntity.setMenuCode(contentsSaveDTO.getMenuCode());
        contentsEntity.setImageFileName(contentsSaveDTO.getImageFileName());
        contentsEntity.setImageFileSize(contentsSaveDTO.getImageFileSize());
        contentsEntity.setImageFilePhysicalName(contentsSaveDTO.getImageFilePhysicalName());
        contentsEntity.setFolderName(contentsSaveDTO.getFolderName());
        contentsEntity.setCampaignPeriodSectionCode(contentsSaveDTO.getCampaignPeriodSectionCode());
        // 캠페인기간 > 날짜선택 인 경우
        if (EnumCode.ContentsCampaignPeriodCode.EVERY.toString().equals(contentsSaveDTO.getCampaignPeriodSectionCode())) {
            contentsEntity.setCampaignBeginDt(contentsSaveDTO.getCampaignBeginDt());
            contentsEntity.setCampaignEndDt(contentsSaveDTO.getCampaignEndDt());
        }
        contentsEntity.setMemo(contentsSaveDTO.getMemo());
        // TODO[lsj] 권한설정 추가 하기

        Contents saveContents = contentsRepository.save(contentsEntity);

        // contents파일 추가
        if (!contentsSaveDTO.getContentsFileList().isEmpty()) {
            for (ContentsFileSaveDTO contentsFileSaveDTO : contentsSaveDTO.getContentsFileList()) {
                final ContentsFile contentsFileEntity = new ContentsFile();
                contentsFileEntity.setContentsSeq(saveContents.getContentsSeq());
                contentsFileEntity.setFileSectionCode(contentsFileSaveDTO.getFileSectionCode());
                contentsFileEntity.setFileKindCode(contentsFileSaveDTO.getFileKindCode());

                String fileKindCode = contentsFileSaveDTO.getFileKindCode();

                if (EnumCode.ContentsFileKindCode.FILE.equals(fileKindCode)) {
                    this.checkStringValidation(contentsFileSaveDTO.getFileName(), ErrorEnumCode.ContentsError.CONE01.toString(), ErrorEnumCode.ContentsError.CONE01.getMessage());

                    contentsFileEntity.setFileName(contentsFileSaveDTO.getFileName());
                    contentsFileEntity.setFileSize(contentsFileSaveDTO.getFileSize());
                    contentsFileEntity.setFilePhysicalName(contentsFileSaveDTO.getFilePhysicalName());
                } else {
                    this.checkStringValidation(contentsFileSaveDTO.getTitle(), ErrorEnumCode.ContentsError.CONE02.toString(), ErrorEnumCode.ContentsError.CONE02.getMessage());
                    this.checkStringValidation(contentsFileSaveDTO.getUrl(), ErrorEnumCode.ContentsError.CONE03.toString(), ErrorEnumCode.ContentsError.CONE03.getMessage());

                    contentsFileEntity.setTitle(contentsFileSaveDTO.getTitle());
                    contentsFileEntity.setUrl(contentsFileSaveDTO.getUrl());
                }
            }
        }

        return saveContents;
    }


    /**
     * Check string validation boolean.
     *
     * @param value        the value
     * @param errorCode    the error code
     * @param errorMessage the error message
     * @return the boolean
     * @author [이소정]
     * @CreatedOn 2020. 6. 26. 오후 5:30:51
     * @Description
     */
    public Boolean checkStringValidation(String value, String errorCode, String errorMessage) {
        if (value.isEmpty() || value.trim().isEmpty()) {
            throw new CodeMessageHandleException(errorCode, errorMessage);
        }
        return true;
    }

}
