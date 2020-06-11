package com.nike.dnp.service.log;

import com.nike.dnp.dto.example.auth.AuthUserDTO;
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
 * @Description EmailSendingLog(메일_발송_로그) Service 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailSendingLogService {

    /**
     * EmailSendingLog(메일_발송_로그) Repository
     * @author [오지훈]
     */
    private final EmailSendingLogRepository logRepository;

    /**
     * EmailSendingLog(메일_발송_로그) 등록
     *
     * @param saveDTO the save dto
     * @return email sending log
     * @author [오지훈]
     */
    @Transactional
    public EmailSendingLog save(
            final EmailSendingLogSaveDTO saveDTO
            , final AuthUserDTO authUserDTO
    ) {
        final EmailSendingLog saveLog = new EmailSendingLog();
        saveLog.setUserSeq(saveDTO.getUserSeq());
        saveLog.setTitle(saveDTO.getTitle());
        saveLog.setContents(saveDTO.getContents());
        saveLog.setRegisterSeq(authUserDTO.getUserSeq()); //TODO DTO field명 수정예정
        saveLog.setUpdaterSeq(authUserDTO.getUserSeq()); //TODO DTO field명 수정예정
        return logRepository.save(saveLog);
    }

}
