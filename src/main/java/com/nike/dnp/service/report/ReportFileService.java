package com.nike.dnp.service.report;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.dto.contents.ContentsFileResultDTO;
import com.nike.dnp.dto.contents.ContentsFileSearchDTO;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.dto.report.*;
import com.nike.dnp.dto.user.UserContentsSearchDTO;
import com.nike.dnp.entity.report.Report;
import com.nike.dnp.entity.report.ReportFile;
import com.nike.dnp.entity.user.UserAuth;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.report.ReportFileRepository;
import com.nike.dnp.repository.report.ReportRepository;
import com.nike.dnp.repository.user.UserAuthRepository;
import com.nike.dnp.service.alarm.AlarmService;
import com.nike.dnp.service.auth.AuthService;
import com.nike.dnp.service.history.HistoryService;
import com.nike.dnp.service.user.UserContentsService;
import com.nike.dnp.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * The Class Report file service.
 *
 * @author [이소정]
 * @since 2020. 8. 13. 오후 7:35:20
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportFileService {

    /**
     * The Report file repository
     *
     * @author [이소정]
     */
    private ReportFileRepository reportFileRepository;

    /**
     * Find all report file paging page.
     *
     * @param reportFileSearchDTO the report file search dto
     * @return the page
     * @author [이소정]
     * @implNote
     * @since 2020. 8. 13. 오후 7:48:16
     */
    public Page<ReportFileResultDTO> findAllReportFilePaging(final ReportFileSearchDTO reportFileSearchDTO) {
        log.info("ReportFileService.findAllReportFilePaging");
        // QueryDsl 기능 이용
        return reportFileRepository.findAllReportFilePaging(
                reportFileSearchDTO,
                PageRequest.of(reportFileSearchDTO.getPage()
                        , reportFileSearchDTO.getSize()
                        , Sort.by(ServiceCode.SearchEnumCode.UPDATE_DT.getValue()).ascending()));
    }

}
