package com.nike.dnp.service.report;

import com.google.firebase.database.core.Repo;
import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.report.ReportBasketResultDTO;
import com.nike.dnp.entity.report.ReportBasket;
import com.nike.dnp.entity.report.ReportFile;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.report.ReportBasketRepository;
import com.nike.dnp.repository.report.ReportFileRepository;
import com.nike.dnp.util.MessageUtil;
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Class Report basket service.
 *
 * @author [이소정]
 * @implNote 보고서 장바구니 서비스
 * @since 2020. 7. 17. 오후 6:18:20
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
     *
     * @author [이소정]
     */
    private final ReportFileRepository reportFileRepository;


    /**
     * Find all report basket list.
     *
     * @return the list
     * @author [이소정]
     * @implNote 보고서 장바구니 목록 전체 조회
     * @since 2020. 7. 17. 오후 6:50:47
     */
    public List<ReportBasketResultDTO> findAllReportBasket() {
        return reportBasketRepository.findAllReportBasket(SecurityUtil.currentUser().getUserSeq());
    }

    /**
     * Save list.
     *
     * @param reportFileSeqList the report file seq list
     * @return the list
     * @author [이소정]
     * @implNote 보고서 장바구니 저장
     * @since 2020. 7. 17. 오후 7:06:01
     */
    @Transactional
    public List<ReportBasket> save(final List<Long> reportFileSeqList) {
        List<ReportBasket> reportBasketList = new ArrayList<>();
        for (Long reportFileSeq : reportFileSeqList) {
            Optional<ReportFile> reportFile = reportFileRepository.findById(reportFileSeq);
            if (reportFile.isPresent()) {
                ReportBasket savedReportBasket = reportBasketRepository.save(new ReportBasket().save(reportFileSeq, SecurityUtil.currentUser().getUserSeq()));
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
     * @implNote 보고서 장바구니 삭제
     * @since 2020. 7. 17. 오후 6:58:54
     */
    @Transactional
    public ReportBasket delete(final Long reportBasketSeq) {
        Optional<ReportBasket> reportBasket = this.findById(reportBasketSeq);
        ReportBasket savedReportBasket = reportBasket.get();
        reportBasketRepository.delete(savedReportBasket);
        return savedReportBasket;
    }

    /**
     * Find by id optional.
     *
     * @param reportBasketSeq the report basket seq
     * @return the optional
     * @author [이소정]
     * @implNote 보고서 장바구니 seq 로 상세 조회
     * @since 2020. 7. 30. 오후 2:56:21
     */
    public Optional<ReportBasket> findById(final Long reportBasketSeq) {
        return Optional.ofNullable(reportBasketRepository.findById(reportBasketSeq).orElseThrow(() -> new CodeMessageHandleException(
                        FailCode.ExceptionError.NOT_FOUND.name() , MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name()
        ))));
    }

}
