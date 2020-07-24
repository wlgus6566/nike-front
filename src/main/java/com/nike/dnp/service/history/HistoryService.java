package com.nike.dnp.service.history;

import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.history.HistoryResultDTO;
import com.nike.dnp.dto.history.HistorySearchDTO;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.history.History;
import com.nike.dnp.repository.contents.ContentsRepository;
import com.nike.dnp.repository.history.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The Class History service.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 23. 오전 11:32:16
 * @Description
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HistoryService {

    /**
     * The History repository
     *
     * @author [이소정]
     */
    private final HistoryRepository historyRepository;

    /**
     * Find all upload folder paging page.
     *
     * @param historySearchDTO the history search dto
     * @return the page
     */
    public Page<HistoryResultDTO> findAllUploadFolderPaging(final HistorySearchDTO historySearchDTO) {
        List<HistoryResultDTO> uploadHistoryList = historyRepository.findAllUploadFilePaging(
                historySearchDTO.getRegisterSeq()
                , historySearchDTO.getPage()
                , historySearchDTO.getSize());

        return new PageImpl<>(uploadHistoryList,
                PageRequest.of(historySearchDTO.getPage(), historySearchDTO.getSize())
                , uploadHistoryList.size());
    }

    /**
     * Find all view history paging page.
     *
     * @param historySearchDTO the history search dto
     * @param authUserDTO      the auth user dto
     * @return the page
     * @author [이소정]
     * @CreatedOn 2020. 7. 24. 오후 4:00:56
     * @Description
     */
    public Page<HistoryResultDTO> findAllViewHistoryPaging(final HistorySearchDTO historySearchDTO, final AuthUserDTO authUserDTO) {
        historySearchDTO.setRegisterSeq(authUserDTO.getUserSeq());
        return historyRepository.findAllHistoryPage(
                historySearchDTO,
                PageRequest.of(historySearchDTO.getPage()
                        , historySearchDTO.getSize()
                        , Sort.by("registrationDt").descending()));

    }

    /**
     * Save history.
     *
     * @param folderSeq the folder seq
     * @param typeCd    the type cd
     * @return the history
     */
    public History save(Long folderSeq, String typeCd) {
        History history = new History();
        history.setTypeCd(typeCd);
        if (ServiceEnumCode.HistoryTabEnumCode.REPORT.toString().equals(typeCd)) {
            history.setReportSeq(folderSeq);
        } else {
            history.setContentsSeq(folderSeq);
        }
        return historyRepository.save(history);
    }
}
