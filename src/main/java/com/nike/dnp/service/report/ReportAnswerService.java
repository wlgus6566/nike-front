package com.nike.dnp.service.report;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.report.ReportAnswerResultDTO;
import com.nike.dnp.dto.report.ReportAnswerSaveDTO;
import com.nike.dnp.entity.report.ReportAnswer;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.report.ReportAnswerRepository;
import com.nike.dnp.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The Class Report answer service.
 *
 * @author [이소정]
 * @implNote 보고서 피드백 서비스
 * @since 2020. 7. 30. 오후 2:52:51
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportAnswerService {

    /**
     * The Report answer repository
     *
     * @author [이소정]
     */
    private final ReportAnswerRepository reportAnswerRepository;

    /**
     * Find all list.
     *
     * @param reportSeq the report seq
     * @return the list
     * @author [이소정]
     * @implNote 보고서 피드백 전체 목록 조회
     * @since 2020. 7. 10. 오후 6:48:57
     */
    public List<ReportAnswerResultDTO> findAll(final Long reportSeq) {
        log.info("ReportAnswerService.findAll");
        return reportAnswerRepository.findAllReportAnswerList(reportSeq);
    }


    /**
     * Save report answer.
     *
     * @param reportAnswerSaveDTO the report answer save dto
     * @return the report answer
     * @author [이소정]
     * @implNote 보고서 피드백 저장
     * @since 2020. 7. 10. 오전 11:29:37
     */
    @Transactional
    public ReportAnswer save(final ReportAnswerSaveDTO reportAnswerSaveDTO) {
        log.info("ReportAnswerService.save");
        final ReportAnswer savedReportAnswer = reportAnswerRepository.save(new ReportAnswer().save(reportAnswerSaveDTO));
        return savedReportAnswer;
    }

    /**
     * Delete.
     *
     * @param answerSeq the answer seq
     * @return the report answer
     * @author [이소정]
     * @implNote 보고서 피드백 삭제
     * @since 2020. 7. 10. 오전 11:29:34
     */
    @Transactional
    public ReportAnswer delete(final Long answerSeq) {
        log.info("ReportAnswerService.delete");
        final Optional<ReportAnswer> reportAnswer = this.findById(answerSeq);
        reportAnswer.ifPresent(value -> value.updateUseYn("N"));
        return reportAnswer.get();

    }

    /**
     * Find by id optional.
     *
     * @param answerSeq the answer seq
     * @return the optional
     * @author [이소정]
     * @implNote 보고서 피드백 seq로 상세 조회
     * @since 2020. 7. 30. 오후 2:52:41
     */
    public Optional<ReportAnswer> findById(final Long answerSeq) {
        log.info("ReportAnswerService.findById");
        return Optional.ofNullable(reportAnswerRepository.findById(answerSeq).orElseThrow((
                () -> new CodeMessageHandleException(FailCode.ExceptionError.NOT_FOUND.name(), MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name())))));
    }
}
