package com.nike.dnp.service.report;

import com.nike.dnp.dto.report.ReportSearchDTO;
import com.nike.dnp.entity.report.Report;
import com.nike.dnp.repository.report.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class Report service.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 7. 오후 2:40:15
 * @Description
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    /**
     * The Report repository
     * @author [이소정]
     */
    private final ReportRepository reportRepository;

    public Page<Report> findAllPaging(final ReportSearchDTO reportSearchDTO) {
        return reportRepository.findPageReport(
                reportSearchDTO,
                PageRequest.of(reportSearchDTO.getPage()
                , reportSearchDTO.getSize()
                , Sort.by("readCount").descending())
        );
    }


}
