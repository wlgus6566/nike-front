package com.nike.dnp.service.log;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.log.DownloadLogSaveDTO;
import com.nike.dnp.entity.log.DownloadLog;
import com.nike.dnp.repository.log.DownloadLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DownloadLogService
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:58:41
 * @implNote DownloadLog(다운로드 로그) Service 작성
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DownloadLogService {

    /**
     * ErrorLogRepository
     *
     * @author [오지훈]
     */
    private final DownloadLogRepository logRepository;

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
    public List<DownloadLog> save(final DownloadLogSaveDTO saveDTO) {
        log.info("DownloadLogService.save");
        final List<DownloadLog> downloadLogs = new ArrayList<>();
        for (ServiceCode.DownloadTypeEnumCode code : ServiceCode.DownloadTypeEnumCode.values()) {
            if (code.name().equals(saveDTO.getDownloadType())) {
                for (Long seq : saveDTO.getSeqArray()) {
                    downloadLogs.add(logRepository.save(
                            DownloadLog.builder()
                                    .downloadType(saveDTO.getDownloadType())
                                    .fileSeq(seq)
                                    .fileSeqArray(Arrays.toString(saveDTO.getSeqArray()))
                            .build()
                    ));
                }
            }
        }
        return downloadLogs;
    }

}
