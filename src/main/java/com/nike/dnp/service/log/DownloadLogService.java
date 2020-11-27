package com.nike.dnp.service.log;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.log.DownloadLogSaveDTO;
import com.nike.dnp.entity.log.DownloadLog;
import com.nike.dnp.repository.log.DownloadLogRepository;
import com.nike.dnp.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DownloadLogService
 *
 * @author [오지훈]
 * @implNote DownloadLog(다운로드 로그) Service 작성
 * @since 2020. 6. 24. 오후 5:58:41
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DownloadLogService {

    /**
     * DownloadLogRepository
     *
     * @author [오지훈]
     */
    private final DownloadLogRepository logRepository;

    /**
     * The Device service
     *
     * @author [이소정]
     */
    private final DeviceService deviceService;

    /**
     * Save download log.
     *
     * @param saveDTO the save dto
     * @param request the request
     * @return download log
     * @author [오지훈]
     * @implNote 다운로드 로그 등록
     * @since 2020. 6. 24. 오후 5:58:41
     */
    @Transactional
    public List<DownloadLog> save(final DownloadLogSaveDTO saveDTO, final HttpServletRequest request) {
        log.info("DownloadLogService.save");
        // device check
        String device = deviceService.checkDevice(request);

        final List<DownloadLog> downloadLogs = new ArrayList<>();
        for (ServiceCode.DownloadTypeEnumCode code : ServiceCode.DownloadTypeEnumCode.values()) {
            if (code.name().equals(saveDTO.getDownloadType())) {
                for (Long seq : saveDTO.getSeqArray()) {
                    downloadLogs.add(logRepository.save(
                            DownloadLog.builder()
                                    .downloadType(saveDTO.getDownloadType())
                                    .fileSeq(seq)
                                    .fileSeqArray(Arrays.toString(saveDTO.getSeqArray()))
                                    .device(device)
                            .build()
                    ));
                }
            }
        }
        return downloadLogs;
    }

}
