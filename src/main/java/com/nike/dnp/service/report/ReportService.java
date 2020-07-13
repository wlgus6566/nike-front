package com.nike.dnp.service.report;

import com.nike.dnp.dto.report.*;
import com.nike.dnp.entity.report.Report;
import com.nike.dnp.entity.report.ReportFile;
import com.nike.dnp.repository.report.ReportFileRepository;
import com.nike.dnp.repository.report.ReportRepository;
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

    /**
     * The Report file repository
     * @author [이소정]
     */
    private final ReportFileRepository reportFileRepository;

    /**
     * Find all paging page.
     *
     * @param reportSearchDTO the report search dto
     * @return the page
     * @author [이소정]
     * @CreatedOn 2020. 7. 8. 오후 5:28:17
     * @Description
     */
    public Page<Report> findAllPaging(final ReportSearchDTO reportSearchDTO) {
        return reportRepository.findPageReport(
                reportSearchDTO,
                PageRequest.of(reportSearchDTO.getPage()
                , reportSearchDTO.getSize()
                , Sort.by("readCount").descending())
        );
    }

    /**
     * Save report.
     *
     * @param reportSaveDTO the report save dto
     * @return the report
     * @author [이소정]
     * @CreatedOn 2020. 7. 8. 오후 5:28:20
     * @Description
     */
    public Report save(final ReportSaveDTO reportSaveDTO) {
        log.info("ReportService.save");
        final Report savedReport = reportRepository.save(new Report().save(reportSaveDTO));
        List<ReportFile> reportFileList = new ArrayList<>();

        if (!reportSaveDTO.getReportFileSaveDTOList().isEmpty()) {
            for (ReportFileSaveDTO reportFileSaveDTO : reportSaveDTO.getReportFileSaveDTOList()) {
                ReportFile savedReportFile = reportFileRepository.save(new ReportFile().save(savedReport.getReportSeq(), reportFileSaveDTO));
                reportFileList.add(savedReportFile);
            }
        }

        savedReport.setReportFileList(reportFileList);

        return savedReport;
    }

    /**
     * Find by report seq report.
     *
     * @param reportSeq the report seq
     * @return the report
     * @author [이소정]
     * @CreatedOn 2020. 7. 8. 오후 5:52:10
     * @Description
     */
    public Report findByReportSeq(final Long reportSeq) {
        return reportRepository.findByReportSeq(reportSeq);
    }

    /**
     * Update optional.
     *
     * @param reportUpdateDTO the report update dto
     * @return the optional
     * @author [이소정]
     * @CreatedOn 2020. 7. 9. 오후 6:49:17
     * @Description
     */
    public Optional<Report> update(final ReportUpdateDTO reportUpdateDTO) {
        log.info("reportService.update");

        final Optional<Report> report = reportRepository.findById(reportUpdateDTO.getReportSeq());
        report.ifPresent(value -> value.update(reportUpdateDTO));

        final List<ReportFile> beforeFileList = reportFileRepository.findByReportSeqAndUseYn(reportUpdateDTO.getReportSeq(), "Y");
        List<ReportFileUpdateDTO> newFileList = reportUpdateDTO.getReportFileUpdateDTOList();

        if (!beforeFileList.isEmpty() && !newFileList.isEmpty()) {
            for (ReportFile beforeFile : beforeFileList) {
                for (ReportFileUpdateDTO newFile : newFileList) {
                    if (beforeFile.getReportFileSeq() == newFile.getReportFileSeq()) {
                        beforeFileList.remove(beforeFile);
                    }
                }
            }
        }

        if (!newFileList.isEmpty()) {
            for (ReportFileUpdateDTO reportFileUpdateDTO : newFileList) {
                Long contentsFileSeq = reportFileUpdateDTO.getReportFileSeq();
                ReportFile saveReportFile = new ReportFile().updateNewFile(report.get().getReportSeq(), reportFileUpdateDTO);
                if (null != contentsFileSeq) {
                    Optional<ReportFile> reportFile = reportFileRepository.findById(reportFileUpdateDTO.getReportFileSeq());
                    reportFile.ifPresent(value -> value.update(reportFileUpdateDTO));
                } else {
                    reportFileRepository.save(saveReportFile);
                }
            }
        }

        if (!beforeFileList.isEmpty()) {
            for (ReportFile reportFile : beforeFileList) {
                reportFile.updateUseYn("N");
            }
        }

        return report;
    }


    /**
     * Delete optional.
     *
     * @param reportSeq the report seq
     * @return the optional
     * @author [이소정]
     * @CreatedOn 2020. 7. 9. 오후 5:49:18
     * @Description
     */
    public Optional<Report> delete(final Long reportSeq) {
        Optional<Report> report = reportRepository.findById(reportSeq);
        report.ifPresent(value -> value.updateUseYn("N"));

        if (!report.get().getReportFileList().isEmpty()) {
            for (ReportFile reportFile : report.get().getReportFileList()) {
                reportFile.updateUseYn("N");
            }
        }

        return report;
    }

}
