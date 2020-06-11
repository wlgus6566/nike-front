package com.nike.dnp.service.log;

import com.nike.dnp.dto.auth.AuthUserDTO;
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
 * @Description ErrorLog(오류 로그) Service 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ErrorLogService {

    /**
     * ErrorLog(오류 로그) Repository
     * @author [오지훈]
     */
    private final ErrorLogRepository logRepository;

    /**
     * ErrorLog(오류 로그) 등록
     *
     * @param saveDTO the save dto
     * @return error log
     * @author [오지훈]
     */
    @Transactional
    public ErrorLog save(
            final ErrorLogSaveDTO saveDTO
            , final AuthUserDTO authUserDTO
    ) {
        final ErrorLog saveLog = new ErrorLog();
        saveLog.setUrl(saveDTO.getUrl());
        saveLog.setErrorContents(saveDTO.getErrorContents());
        saveLog.setRegisterSeq(authUserDTO.getUserSeq()); //TODO[ojh] DTO field명 수정예정
        saveLog.setUpdaterSeq(authUserDTO.getUserSeq()); //TODO[ojh] DTO field명 수정예정
        return logRepository.save(saveLog);
    }

}
