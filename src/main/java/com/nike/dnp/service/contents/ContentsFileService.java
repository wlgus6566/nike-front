package com.nike.dnp.service.contents;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.contents.ContentsFileResultDTO;
import com.nike.dnp.dto.contents.ContentsFileSearchDTO;
import com.nike.dnp.entity.contents.ContentsFile;
import com.nike.dnp.exception.NotFoundHandleException;
import com.nike.dnp.repository.contents.ContentsFileRepository;
import com.nike.dnp.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;


/**
 * The Class Contents file service.
 *
 * @author [이소정]
 * @since 2020. 7. 13. 오후 5:42:58
 * @implNote 컨텐츠 파일 서비스
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ContentsFileService {

    /**
     * The Contents repository
     *
     * @author [이소정]
     */
    private final ContentsFileRepository contentsFileRepository;

    /**
     * Find all contents file list.
     *
     * @param contentsFileSearchDTO the contents file search dto
     * @return the list
     * @author [이소정]
     * @implNote 콘텐츠 파일 페이징 처리 한 목록
     * @since 2020. 7. 13. 오후 6:01:00
     */
    public Page<ContentsFileResultDTO> findAllContentsFilePaging(final ContentsFileSearchDTO contentsFileSearchDTO) {
        log.info("ContentsFileService.findAllContentsFilePaging");
        // QueryDsl 기능 이용
        return contentsFileRepository.findAllContentsFilePaging(
                contentsFileSearchDTO,
                PageRequest.of(contentsFileSearchDTO.getPage()
                , contentsFileSearchDTO.getSize()
                , contentsFileSearchDTO.getOrderType().equals(ServiceCode.ContentsFileSearchCode.ORDER.toString())
                        ? Sort.by(ServiceCode.ContentsFileSearchCode.ORDER.getValue()).ascending() : Sort.by(ServiceCode.ContentsFileSearchCode.FILE_NAME.getValue()).ascending()));
    }

    /**
     * Download contents file string.
     *
     * @param contentsFileSeq the contents file seq
     * @return the string
     * @author [이소정]
     * @implNote 컨텐츠 파일 다운로드
     * @since 2020. 7. 16. 오후 2:51:01
     */
    @Transactional
    public ResponseEntity<Resource> downloadFile(final Long contentsFileSeq) throws IOException {
        log.info("ContentsService.downloadFile");
        final Optional<ContentsFile> contentsFile = this.findById(contentsFileSeq);
        if (contentsFile.isPresent()) {
            contentsFile.ifPresent(value -> value.updateDownloadCount(contentsFile.get().getDownloadCount()));
            return FileUtil.s3FileDownload(contentsFile.get().getFilePhysicalName(), contentsFile.get().getFileName());

        } else {
            return null;
        }
    }

    /**
     * Find by id optional.
     *
     * @param contentsFileSeq the contents file seq
     * @return the optional
     * @author [이소정]
     * @implNote 콘텐츠 파일 찾기
     * @since 2020. 8. 27. 오후 9:28:49
     */
    public Optional<ContentsFile> findById(final Long contentsFileSeq) {
        log.info("ContentsFileService.findById");
        return Optional.ofNullable(contentsFileRepository.findById(contentsFileSeq).orElseThrow(
                () -> new NotFoundHandleException()));
    }
}
