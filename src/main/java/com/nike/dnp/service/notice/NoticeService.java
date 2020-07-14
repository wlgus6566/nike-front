package com.nike.dnp.service.notice;

import com.nike.dnp.dto.notice.NoticeArticeListDTO;
import com.nike.dnp.dto.notice.NoticeSearchDTO;
import com.nike.dnp.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<NoticeArticeListDTO> findNoticePages(NoticeSearchDTO noticeSearchDTO) {
        log.info("NoticeService.findNoticePages");
        return noticeRepository.findNoticePages(noticeSearchDTO, PageRequest.of(noticeSearchDTO.getPage(), noticeSearchDTO.getSize()));
    }
}
