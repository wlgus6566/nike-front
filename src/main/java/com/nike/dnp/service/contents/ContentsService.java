package com.nike.dnp.service.contents;

import com.nike.dnp.common.variable.EnumCode;
import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.repository.contents.ContentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Contents Service
 *
 * @author [이소정]
 * @Description Contents Service 작성
 * @history [이소정] [2020.06.11] [최초 작성]
 * @since 2020.06.11
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
}
