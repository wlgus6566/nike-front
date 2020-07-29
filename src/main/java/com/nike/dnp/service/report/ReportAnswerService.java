package com.nike.dnp.service.report;

import com.nike.dnp.common.variable.FailCode;
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
     * @CreatedOn 2020. 7. 10. 오후 6:48:57
     * @Description
     */
    public List<ReportAnswer> findAll(final Long reportSeq) {
        log.info("ReportAnswerService.findAll");
        return reportAnswerRepository.findAllByReportSeqAndUseYn(reportSeq, "Y");
    }


    /**
     * Save report answer.
     *
     * @param reportAnswerSaveDTO the report answer save dto
     * @return the report answer
     * @author [이소정]
     * @CreatedOn 2020. 7. 10. 오전 11:29:37
     * @Description
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
     * @CreatedOn 2020. 7. 10. 오전 11:29:34
     * @Description
     */
    @Transactional
    public ReportAnswer delete(final Long answerSeq) {
        log.info("ReportAnswerService.delete");
        Optional<ReportAnswer> reportAnswer = this.findById(answerSeq);
        reportAnswer.ifPresent(value -> value.updateUseYn("Y"));
        return reportAnswer.get();

    }

    /**
     * Find by id optional.
     *
     * @param answerSeq the answer seq
     * @return the optional
     */
    public Optional<ReportAnswer> findById(final Long answerSeq) {
        return Optional.ofNullable(reportAnswerRepository.findById(answerSeq).orElseThrow((
                () -> new CodeMessageHandleException(FailCode.ExceptionError.NOT_FOUND.name(), MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name())))));
    }
}
