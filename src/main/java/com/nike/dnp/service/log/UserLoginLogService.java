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
 * @CreatedOn 2020. 6. 24. 오후 5:59:00
 * @Description UserLoginLog(유저_로그인_로그) Service 작성
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserLoginLogService {

    /**
     * UserLoginLogRepository
     *
     * @author [오지훈]
     */
    private final UserLoginLogRepository logRepository;

    /**
     * Save user login log.
     *
     * @param saveDTO the save dto
     * @return user login log
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:59:00
     * @Description 유저 로그인 로그 등록
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
