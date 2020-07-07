package com.nike.dnp.service.contents;

import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.dto.contents.save.ContentsFileSaveDTO;
import com.nike.dnp.dto.contents.save.ContentsSaveDTO;
import com.nike.dnp.dto.contents.update.ContentsFileUpdateDTO;
import com.nike.dnp.dto.contents.update.ContentsUpdateDTO;
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
import java.util.Optional;

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

        savedContents.setContentsFileList(savedContentsFileList);
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

    /**
     * Update contents.
     *
     * @param contentsUpdateDTO the contents update dto
     * @return the contents
     * @author [이소정]
     * @CreatedOn 2020. 7. 3. 오후 4:01:24
     * @Description
     */
    @Transactional
    public Optional<Contents> update(final ContentsUpdateDTO contentsUpdateDTO) {
        log.info("contentsService.update");
        // contents Update
        final Optional<Contents> contents = contentsRepository.findById(contentsUpdateDTO.getContentsSeq());
        contents.ifPresent(value -> value.update(contentsUpdateDTO));

        // contents File
        final List<ContentsFile> beforeFileList = contentsFileRepository.findByContentsSeqAndUseYn(contents.get().getContentsSeq(), "Y");
        List<ContentsFileUpdateDTO> newFileList = contentsUpdateDTO.getContentsFileList();

        // 기존에 있는 파일 목록과 DTO받은 파일 목록 비교해서
        // case1.기본목록O, 새로운목록X : useYn = 'N' update
        // case2.기존목록X, 새로운목록O : save
        // case3.기존목록O, 새로운목록O : update
        if (!beforeFileList.isEmpty() && !newFileList.isEmpty()) {
            for (ContentsFile beforeFile : beforeFileList) {
                for (ContentsFileUpdateDTO newFile : newFileList) {
                    if (beforeFile.getContentsFileSeq() == newFile.getContentsFileSeq()) {
                        beforeFileList.remove(beforeFile);
                    }
                }
            }
        }

        if (!newFileList.isEmpty()) {
            for (ContentsFileUpdateDTO contentsFileUpdateDTO : newFileList) {
                Long contentsFileSeq = contentsFileUpdateDTO.getContentsFileSeq();
                ContentsFile saveContentsFile = new ContentsFile().newContentsFile(contents.get().getContentsSeq(), contentsFileUpdateDTO);
                if (null != contentsFileSeq) {
                    Optional<ContentsFile> contentsFile = contentsFileRepository.findById(contentsFileUpdateDTO.getContentsFileSeq());
                    contentsFile.ifPresent(value -> value.update(contentsFileUpdateDTO));
                } else {
                    contentsFileRepository.save(saveContentsFile);
                }
            }
        }
        if (!beforeFileList.isEmpty()) {
            for (ContentsFile contentsFile : beforeFileList) {
                contentsFile.setUseYn("N");
                contentsFileRepository.save(contentsFile);
            }
        }
        return contents;
    }

}
