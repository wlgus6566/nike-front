package com.nike.dnp.service.contents;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.contents.ContentsFileResultDTO;
import com.nike.dnp.dto.contents.ContentsFileSearchDTO;
import com.nike.dnp.repository.contents.ContentsFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The Class Contents file service.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 13. 오후 5:42:58
 * @Description
 */
@Slf4j
@Service
@Transactional
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
                , contentsFileSearchDTO.getOrderType().equals(ServiceCode.SearchEnumCode.ORDER.toString())
                        ? Sort.by("fileOrder").ascending() : Sort.by("fileName").ascending()));
    }
}
