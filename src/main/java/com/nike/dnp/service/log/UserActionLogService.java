package com.nike.dnp.service.log;

import com.nike.dnp.dto.log.UserActionLogSaveDTO;
import com.nike.dnp.entity.log.UserActionLog;
import com.nike.dnp.repository.log.UserActionLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserActionLogService
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:58:49
 * @implNote UserActionLog(유저_활동_로그) Service 작성
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserActionLogService {

    /**
     * UserActionLogRepository
     *
     * @author [오지훈]
     */
    private final UserActionLogRepository logRepository;

    /**
     * Save user action log.
     *
     * @param saveDTO the save dto
     * @return user action log
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:58:49
     * @implNote 유저 활동 로그 등록
     */
    @Transactional
    public UserActionLog save(final UserActionLogSaveDTO saveDTO) {
        final UserActionLog saveLog = new UserActionLog();
        saveLog.setUserSeq(saveDTO.getUserSeq());
        saveLog.setUrl(saveDTO.getUrl());
        saveLog.setMethodTypeName(saveDTO.getMethodTypeName());
        saveLog.setMethodSignature(saveDTO.getMethodSignature());
        saveLog.setParameter(saveDTO.getParameter());
        saveLog.setDevice(saveDTO.getDevice());
        return logRepository.save(saveLog);
    }

}
