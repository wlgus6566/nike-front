package com.nike.dnp.service.history;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.history.HistoryResultDTO;
import com.nike.dnp.dto.history.HistorySearchDTO;
import com.nike.dnp.entity.contents.RecentUpload;
import com.nike.dnp.entity.history.History;
import com.nike.dnp.repository.history.HistoryRepository;
import com.nike.dnp.repository.history.RecentUploadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * The Recent upload repository
     *
     * @author [이소정]
     */
    private final RecentUploadRepository recentUploadRepository;

    /**
     * The History repository
     *
     * @author [이소정]
     */
    private final HistoryRepository historyRepository;

    /**
     * Find all recent upload folder paging page.
     *
     * @param historySearchDTO the history search dto
     * @param authUserDTO      the auth user dto
     * @return the page
     * @author [이소정]
     * @CreatedOn 2020. 7. 27. 오후 2:19:02
     * @Description
     */
    public Page<HistoryResultDTO> findAllUploadHistoryPaging(final HistorySearchDTO historySearchDTO, final AuthUserDTO authUserDTO) {
        historySearchDTO.setRegisterSeq(authUserDTO.getUserSeq());
        return recentUploadRepository.findAllRecentUpload(
                historySearchDTO,
                PageRequest.of(historySearchDTO.getPage()
                        , historySearchDTO.getSize()
                        , Sort.by("registrationDt").descending()));
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
    public RecentUpload saveRecentUploadHistory(Long folderSeq, String typeCd) {
        RecentUpload recentUpload = new RecentUpload();
        recentUpload.setTypeCd(typeCd);
        if (ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString().equals(typeCd)) {
            recentUpload.setReportSeq(folderSeq);
        } else {
            recentUpload.setContentsSeq(folderSeq);
        }
        return recentUploadRepository.save(recentUpload);
    }

    /**
     * Save history.
     *
     * @param folderSeq the folder seq
     * @param typeCd    the type cd
     * @return the history
     */
    public History saveViewHistory(Long folderSeq, String typeCd) {
        History history = new History();
        history.setTypeCd(typeCd);
        if (ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString().equals(typeCd)) {
            history.setReportSeq(folderSeq);
        } else {
            history.setContentsSeq(folderSeq);
        }
        return historyRepository.save(history);
    }
}
