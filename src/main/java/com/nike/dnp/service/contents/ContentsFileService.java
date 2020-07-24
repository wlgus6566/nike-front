package com.nike.dnp.service.contents;

import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.contents.*;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.contents.ContentsFile;
import com.nike.dnp.repository.contents.ContentsFileRepository;
import com.nike.dnp.repository.contents.ContentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * The Class Contents file service.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 13. 오후 5:42:58
 * @Description
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentsFileService {

    /**
     * The Contents repository
     *
     * @author [이소정]
     */
    private final ContentsFileRepository contentsFileRepository;

    /**
     * Find all contents file list.
     *
     * @param contentsFileSearchDTO the contents file search dto
     * @return the list
     * @author [이소정]
     * @CreatedOn 2020. 7. 13. 오후 6:01:00
     * @Description
     */
    public Page<ContentsFileResultDTO> findAllContentsFilePaging(final ContentsFileSearchDTO contentsFileSearchDTO) {

        // QueryDsl 기능 이용
        return contentsFileRepository.findAllContentsFilePaging(
                contentsFileSearchDTO,
                PageRequest.of(contentsFileSearchDTO.getPage()
                , contentsFileSearchDTO.getSize()
                , contentsFileSearchDTO.getOrderType().equals(ServiceEnumCode.SearchEnumCode.FILE_NAME.toString())
                        ? Sort.by("registrationDt").descending() : Sort.by("fileName").ascending()));
    }
//
//    /**
//     * Update contents.
//     *
//     * @param contentsUpdateDTO the contents update dto
//     * @return the contents
//     * @author [이소정]
//     * @CreatedOn 2020. 7. 3. 오후 4:01:24
//     * @Description
//     */
//    public Optional<Contents> update(final ContentsUpdateDTO contentsUpdateDTO) {
//        log.info("contentsService.update");
//        // contents Update
//        final Optional<Contents> contents = con.findById(contentsUpdateDTO.getContentsSeq());
//        contents.ifPresent(value -> value.update(contentsUpdateDTO));
//
//        // contents File
//        final List<ContentsFile> beforeFileList = contentsFileRepository.findByContentsSeqAndUseYn(contents.get().getContentsSeq(), "Y");
//        List<ContentsFileUpdateDTO> newFileList = contentsUpdateDTO.getContentsFileList();
//
//        // 기존에 있는 파일 목록과 DTO받은 파일 목록 비교해서
//        // case1.기본목록O, 새로운목록X : useYn = 'N' update
//        // case2.기존목록X, 새로운목록O : save
//        // case3.기존목록O, 새로운목록O : update
//        if (!beforeFileList.isEmpty() && !newFileList.isEmpty()) {
//            for (ContentsFile beforeFile : beforeFileList) {
//                for (ContentsFileUpdateDTO newFile : newFileList) {
//                    if (beforeFile.getContentsFileSeq() == newFile.getContentsFileSeq()) {
//                        beforeFileList.remove(beforeFile);
//                    }
//                }
//            }
//        }
//
//        if (!newFileList.isEmpty()) {
//            for (ContentsFileUpdateDTO contentsFileUpdateDTO : newFileList) {
//                Long contentsFileSeq = contentsFileUpdateDTO.getContentsFileSeq();
//                ContentsFile saveContentsFile = new ContentsFile().newContentsFile(contents.get().getContentsSeq(), contentsFileUpdateDTO);
//                if (null != contentsFileSeq) {
//                    Optional<ContentsFile> contentsFile = contentsFileRepository.findById(contentsFileUpdateDTO.getContentsFileSeq());
//                    contentsFile.ifPresent(value -> value.update(contentsFileUpdateDTO));
//                } else {
//                    contentsFileRepository.save(saveContentsFile);
//                }
//            }
//        }
//        if (!beforeFileList.isEmpty()) {
//            for (ContentsFile contentsFile : beforeFileList) {
//                contentsFile.updateUseYn("N");
//            }
//        }
//        return contents;
//    }
//
//
//    /**
//     * Delete optional.
//     *
//     * @param contentsSeq the contents seq
//     * @return the optional
//     * @author [이소정]
//     * @CreatedOn 2020. 7. 7. 오전 10:59:29
//     * @Description
//     */
//    public Optional<Contents> delete(final Long contentsSeq) {
//        log.info("contentsService.delete");
//
//        Optional<Contents> contents = contentsRepository.findById(contentsSeq);
//        contents.ifPresent(value -> value.delete());
//
//        List<ContentsFile> contentsFileList = contents.get().getContentsFileList();
//        if (!contentsFileList.isEmpty()) {
//            for (ContentsFile contentsFile : contentsFileList) {
//                contentsFile.updateUseYn("N");
//            }
//        }
//
//        return contents;
//    }



}