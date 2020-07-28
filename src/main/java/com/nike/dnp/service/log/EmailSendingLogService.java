package com.nike.dnp.service.log;

import com.nike.dnp.dto.log.EmailSendingLogSaveDTO;
import com.nike.dnp.entity.log.EmailSendingLog;
import com.nike.dnp.repository.log.EmailSendingLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EmailSendingLogService
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 5:58:31
 * @Description EmailSendingLog(메일_발송_로그) Service 작성
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailSendingLogService {

    /**
     * EmailSendingLogRepository
     *
     * @author [오지훈]
     */
    private final EmailSendingLogRepository logRepository;

    /**
     * Save email sending log.
     *
     * @param saveDTO the save dto
     * @return email sending log
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:58:31
     * @Description 메일 발송 로그 등록
     */
    @Transactional
    public EmailSendingLog save(final EmailSendingLogSaveDTO saveDTO) {
        return logRepository.save(EmailSendingLog.builder().emailSendingLogSaveDTO(saveDTO).build());
    }

}
