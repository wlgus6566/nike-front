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
 * @Description UserActionLog(유저_활동_로그) Service 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserActionLogService {

    /**
     * UserActionLog(유저_활동_로그) Repository
     * @author [오지훈]
     */
    private final UserActionLogRepository logRepository;

    /**
     * UserActionLog(유저_활동_로그) 등록
     *
     * @param saveDTO the save dto
     * @return user action log
     * @author [오지훈]
     */
    @Transactional
    public UserActionLog save(final UserActionLogSaveDTO saveDTO) {
        final UserActionLog saveLog = new UserActionLog();
        saveLog.setUserSeq(saveDTO.getUserSeq());
        saveLog.setUrl(saveDTO.getUrl());
        saveLog.setParameter(saveDTO.getParameter());
        saveLog.setRegisterSeq(saveDTO.getUserSeq());
        saveLog.setUpdaterSeq(saveDTO.getUserSeq());
        return logRepository.save(saveLog);
    }

}
