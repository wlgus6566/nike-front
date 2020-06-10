package com.nike.dnp.service.log;

import com.nike.dnp.dto.log.UserLoginLogSaveDTO;
import com.nike.dnp.entity.log.UserLoginLog;
import com.nike.dnp.repository.log.UserLoginLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserLoginLogService
 *
 * @author [오지훈]
 * @Description UserLoginLog(유저_로그인_로그) Service 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserLoginLogService {

    /**
     * UserLoginLog(유저_로그인_로그) Repository
     * @author [오지훈]
     */
    private final UserLoginLogRepository logRepository;

    /**
     * UserLoginLog(유저_로그인_로그) 등록
     *
     * @param saveDTO the save dto
     * @return user login log
     * @author [오지훈]
     */
    @Transactional
    public UserLoginLog save(final UserLoginLogSaveDTO saveDTO) {
        final UserLoginLog saveLog = new UserLoginLog();
        saveLog.setUserSeq(saveDTO.getUserSeq());
        saveLog.setLoginIp(saveDTO.getLoginIp());
        saveLog.setRegisterSeq(saveDTO.getUserSeq());
        saveLog.setUpdaterSeq(saveDTO.getUserSeq());
        return logRepository.save(saveLog);
    }

}
