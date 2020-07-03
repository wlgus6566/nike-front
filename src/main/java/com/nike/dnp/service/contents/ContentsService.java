package com.nike.dnp.service.contents;

import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.contents.ContentsFileSaveDTO;
import com.nike.dnp.dto.contents.ContentsSaveDTO;
import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.contents.ContentsFile;
import com.nike.dnp.repository.contents.ContentsFileRepository;
import com.nike.dnp.repository.contents.ContentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
     * The Contents repository
     *
     * @author [이소정]
     */
    private final ContentsRepository contentsRepository;

    /**
     * The Contents file repository
     *
     * @author [이소정]
     */
    private final ContentsFileRepository contentsFileRepository;

    @Autowired
    private MessageSource messageSource;

    /**
     * 전체조회(paging)
     *
     * @param contentsSearchDTO the contents search dto
     * @return the list
     */
    public Page<Contents> findAllPaging(final ContentsSearchDTO contentsSearchDTO) {
        // QueryDsl 기능 이용
        return contentsRepository.findPageContents(
                contentsSearchDTO,
                PageRequest.of(contentsSearchDTO.getPage()
                        , contentsSearchDTO.getSize()
                        , contentsSearchDTO.equals(ServiceEnumCode.SearchEnumCode.START_DATE.toString())
                                ? Sort.by("campaignBeginDt").ascending() : Sort.by("contentsSeq").descending()));
    }

    /**
     * Save contents.
     *
     * @param contentsSaveDTO the contents save dto
     * @return the contents
     * @author [이소정]
     * @CreatedOn 2020. 6. 24. 오후 3:22:15
     * @Description
     */
    @Transactional
    public Contents save(final ContentsSaveDTO contentsSaveDTO) {
        log.info("contentsService.save");
        final Contents savedContents = contentsRepository.save(new Contents().save(contentsSaveDTO));
        List<ContentsFile> savedContentsFileList = new ArrayList<>();

//        contentsFile 추가
        if (!contentsSaveDTO.getContentsFileList().isEmpty()) {
            for (ContentsFileSaveDTO contentsFileSaveDTO : contentsSaveDTO.getContentsFileList()) {
                ContentsFile savedContentsFile = contentsFileRepository.save(new ContentsFile().save(savedContents, contentsFileSaveDTO));
                savedContentsFileList.add(savedContentsFile);
            }
        }

        savedContents.setContentsFiles(savedContentsFileList);
        return savedContents;
    }

    /**
     * Find by contents seq contents.
     *
     * @param contentsSeq the contents seq
     * @return the contents
     * @author [이소정]
     * @CreatedOn 2020. 7. 2. 오후 2:25:43
     * @Description
     */
    public Contents findByContentsSeq(final Long contentsSeq) {
        return contentsRepository.findByContentsSeq(contentsSeq);
    }

}
