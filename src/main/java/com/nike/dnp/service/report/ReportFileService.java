package com.nike.dnp.service.report;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.report.ReportFileResultDTO;
import com.nike.dnp.dto.report.ReportFileSearchDTO;
import com.nike.dnp.repository.report.ReportFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The Class Report file service.
 *
 * @author [이소정]
 * @since 2020. 8. 13. 오후 7:35:20
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportFileService {

    /**
     * The Report file repository
     *
     * @author [이소정]
     */
    private final ReportFileRepository reportFileRepository;

    /**
     * Find all report file paging page.
     *
     * @param reportFileSearchDTO the report file search dto
     * @return the page
     * @author [이소정]
     * @implNote
     * @since 2020. 8. 13. 오후 7:48:16
     */
    public Page<ReportFileResultDTO> findAllReportFilePaging(final ReportFileSearchDTO reportFileSearchDTO) {
        log.info("ReportFileService.findAllReportFilePaging");
        // QueryDsl 기능 이용
        return reportFileRepository.findAllReportFilePaging(
                reportFileSearchDTO,
                PageRequest.of(reportFileSearchDTO.getPage()
                        , reportFileSearchDTO.getSize()
                        , Sort.by(ServiceCode.SearchEnumCode.LATEST.getValue()).ascending()));
    }

}
