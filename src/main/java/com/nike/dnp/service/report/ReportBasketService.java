package com.nike.dnp.service.report;

import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.report.ReportBasketResultDTO;
import com.nike.dnp.entity.report.ReportBasket;
import com.nike.dnp.entity.report.ReportFile;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.report.ReportBasketRepository;
import com.nike.dnp.repository.report.ReportFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Class C report basket service.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 17. 오후 6:18:20
 * @Description
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportBasketService {

    /**
     * The Report basket repository
     *
     * @author [이소정]
     */
    private final ReportBasketRepository reportBasketRepository;

    /**
     * The Report file repository
     * @author [이소정]
     */
    private final ReportFileRepository reportFileRepository;


    /**
     * Find all report basket list.
     *
     * @param authUserDTO the auth user dto
     * @return the list
     * @author [이소정]
     * @CreatedOn 2020. 7. 17. 오후 6:50:47
     * @Description
     */
    public List<ReportBasketResultDTO> findAllReportBasket(final AuthUserDTO authUserDTO) {
        return reportBasketRepository.findAllReportBasket(authUserDTO.getUserSeq());
    }

    /**
     * Save list.
     *
     * @param reportFileSeqList the report file seq list
     * @param authUserDTO       the auth user dto
     * @return the list
     * @author [이소정]
     * @CreatedOn 2020. 7. 17. 오후 7:06:01
     * @Description
     */
    @Transactional
    public List<ReportBasket> save(final List<Long> reportFileSeqList, final AuthUserDTO authUserDTO) {
        List<ReportBasket> reportBasketList = new ArrayList<>();
        for (Long reportFileSeq : reportFileSeqList) {
            Optional<ReportFile> reportFile = reportFileRepository.findById(reportFileSeq);
            if (reportFile.isPresent()) {
                ReportBasket savedReportBasket = reportBasketRepository.save(new ReportBasket().save(reportFileSeq, authUserDTO.getUserSeq()));
                reportBasketList.add(savedReportBasket);
            }
        }
        return reportBasketList;
    }


    /**
     * Delete report basket.
     *
     * @param reportBasketSeq the report basket seq
     * @return the report basket
     * @author [이소정]
     * @CreatedOn 2020. 7. 17. 오후 6:58:54
     * @Description
     */
    @Transactional
    public ReportBasket delete(final Long reportBasketSeq) {
        Optional<ReportBasket> reportBasket = reportBasketRepository.findById(reportBasketSeq);
        ReportBasket savedReportBasket = reportBasket.orElseThrow(() -> new CodeMessageHandleException(ErrorEnumCode.ReportBasketError.NOT_FOUND_BASKET.toString(), ErrorEnumCode.ReportBasketError.NOT_FOUND_BASKET.getMessage()));
        reportBasketRepository.delete(savedReportBasket);
        return savedReportBasket;
    }

}
