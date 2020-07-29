package com.nike.dnp.service.log;

import com.nike.dnp.dto.log.ErrorLogSaveDTO;
import com.nike.dnp.entity.log.ErrorLog;
import com.nike.dnp.repository.log.ErrorLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ErrorLogService
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:58:41
 * @implNote ErrorLog(오류 로그) Service 작성
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ErrorLogService {

    /**
     * ErrorLogRepository
     *
     * @author [오지훈]
     */
    private final ErrorLogRepository logRepository;

    /**
     * Save error log.
     *
     * @param saveDTO the save dto
     * @return error log
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:58:41
     * @implNote 오류 로그 등록
     */
    @Transactional
    public ErrorLog save(final ErrorLogSaveDTO saveDTO) {
        return logRepository.save(ErrorLog.builder().errorLogSaveDTO(saveDTO).build());
    }

}
