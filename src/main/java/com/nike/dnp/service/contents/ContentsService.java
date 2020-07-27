package com.nike.dnp.service.contents;

import com.nike.dnp.common.mail.MailService;
import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.contents.*;
import com.nike.dnp.dto.email.SendDTO;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.dto.user.UserContentsSaveDTO;
import com.nike.dnp.dto.user.UserContentsSearchDTO;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.contents.ContentsFile;
import com.nike.dnp.entity.user.UserAuth;
import com.nike.dnp.entity.user.UserContents;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.contents.ContentsFileRepository;
import com.nike.dnp.repository.contents.ContentsRepository;
import com.nike.dnp.repository.user.UserAuthRepository;
import com.nike.dnp.service.alarm.AlarmService;
import com.nike.dnp.service.history.HistoryService;
import com.nike.dnp.service.user.UserContentsService;
import com.nike.dnp.util.FileUtil;
import com.nike.dnp.util.ImageUtil;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Contents Service
 *
 * @author [이소정]
 * @CreatedOn 2020. 6. 11. 오후 3:25:23
 * @Description Contents Service 작성
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentsService {

    /**
     * The Contents repository
     *
     * @author [이소정]
     */
    private final ContentsRepository contentsRepository;

    /**
     * The Contents file repository
     *
     * @author [이소정]
     */
    private final ContentsFileRepository contentsFileRepository;

    /**
     * The Mail service.
     *
     * @author [이소정]
     */
    private final MailService mailService;

    /**
     * The User contents service.
     *
     * @author [이소정]
     */
    private final UserContentsService userContentsService;

    /**
     * The History service.
     *
     * @author [이소정]
     */
    private final HistoryService historyService;

    /**
     * The Alarm service
     *
     * @author [이소정]
     */
    private final AlarmService alarmService;

    /**
     * The User auth repository
     *
     * @author [이소정]
     */
    private final UserAuthRepository userAuthRepository;

    /**
     * Find all paging page.
     *
     * @param contentsSearchDTO the contents search dto
     * @return the page
     * @author [이소정]
     * @CreatedOn 2020. 7. 13. 오후 3:23:01
     * @Description
     */
    public Page<ContentsResultDTO> findAllPaging(final ContentsSearchDTO contentsSearchDTO, final AuthUserDTO authUserDTO, final String topMenuCode, final String menuCode) {
        // 권한 검사
        String searchMenuCode = menuCode.equals(ServiceEnumCode.ContentsMenuCode.ALL.toString()) ? topMenuCode : topMenuCode + "_" + menuCode;
        UserContentsSearchDTO userContentsSearchDTO = new UserContentsSearchDTO();
        userContentsSearchDTO.setMenuCode(searchMenuCode);
        userContentsSearchDTO.setSkillCode(ServiceEnumCode.MenuSkillEnumCode.CREATE.toString());

        // 권한에 따른 조건문
        contentsSearchDTO.setExposureYn(userContentsService.isAuth(authUserDTO.getAuthSeq(), userContentsSearchDTO) ? null : "Y");

        // QueryDsl 기능 이용
        contentsSearchDTO.setUserAuthSeq(authUserDTO.getAuthSeq());
        return contentsRepository.findPageContents(
                contentsSearchDTO,
                PageRequest.of(contentsSearchDTO.getPage()
                        , contentsSearchDTO.getSize()
                        , contentsSearchDTO.equals(ServiceEnumCode.SearchEnumCode.START_DATE.toString())
                                ? Sort.by("campaignBeginDt").ascending() : Sort.by("contentsSeq").descending()));
    }

    /**
     * Save contents.
     *
     * @param contentsSaveDTO the contents save dto
     * @return the contents
     * @author [이소정]
     * @CreatedOn 2020. 6. 24. 오후 3:22:15
     * @Description
     */
    @Transactional
    public Contents save(final ContentsSaveDTO contentsSaveDTO) {
        log.info("contentsService.save");

        // 썸네일 base64 -> file 정보로 변환
        if (!ObjectUtils.isEmpty(contentsSaveDTO.getImageBase64())) {
            FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(ServiceEnumCode.FileFolderEnumCode.CONTENTS.getFolder(), contentsSaveDTO.getImageBase64());

            contentsSaveDTO.setImageFileName(fileResultDTO.getFileName());
            contentsSaveDTO.setImageFileSize(String.valueOf(fileResultDTO.getFileSize()));
            contentsSaveDTO.setImageFilePhysicalName(fileResultDTO.getFilePhysicalName());
        }
        final Contents savedContents = contentsRepository.save(new Contents().save(contentsSaveDTO));
        List<ContentsFile> savedContentsFileList = new ArrayList<>();

        // 컨텐츠 파일 저장
        if (!contentsSaveDTO.getContentsFileList().isEmpty()) {
            for (ContentsFileSaveDTO contentsFileSaveDTO : contentsSaveDTO.getContentsFileList()) {
                ContentsFile savedContentsFile = contentsFileRepository.save(new ContentsFile().save(savedContents, contentsFileSaveDTO));
                savedContentsFileList.add(savedContentsFile);
            }
        }
        savedContents.setContentsFileList(savedContentsFileList);

        // 사용자 컨텐츠 권한 저장
        UserContentsSaveDTO userContentsSaveDTO = new UserContentsSaveDTO();
        userContentsSaveDTO.setChecks(contentsSaveDTO.getChecks());
        this.saveUserContentsAuth(savedContents.getContentsSeq(), userContentsSaveDTO);

        // 노출인 경우 > 권한 그룹에 알림 전송
        if ("Y".equals(contentsSaveDTO.getExposureYn())) {
            alarmService.sendAlarmTargetList(
                    ServiceEnumCode.AlarmActionEnumCode.NEW.toString()
                    , contentsSaveDTO.getTopMenuCode()
                    , savedContents.getContentsSeq()
                    , null
                    , this.findAllAuthUser(contentsSaveDTO.getChecks()));

        }

        return savedContents;
    }

    /**
     * Find all auth user list.
     *
     * @param authCheckList the auth check list
     * @return the list
     * @author [이소정]
     * @CreatedOn 2020. 7. 24. 오후 7:30:13
     * @Description
     */
    public List<Long> findAllAuthUser(final List<UserContentsSaveDTO.AuthCheckDTO> authCheckList) {
        List<Long> userSeqList = new ArrayList<>();
        for (UserContentsSaveDTO.AuthCheckDTO authCheckDTO : authCheckList) {
            if ("Y".equals(authCheckDTO.getDetailAuthYn())) {
                // authSeq 를 가지고 userSeq 목록 가져오기
                List<UserAuth> userAuthList = userAuthRepository.findAllByAuthSeq(authCheckDTO.getAuthSeq());
                for (UserAuth userAuth : userAuthList) {
                    userSeqList.add(userAuth.getUserSeq());
                }
            }
        }
        return userSeqList;
    }


    /**
     * Find by contents seq contents.
     *
     * @param contentsSeq the contents seq
     * @param topMenuCode the top menu code
     * @param menuCode    the menu code
     * @return the contents
     * @author [이소정]
     * @CreatedOn 2020. 7. 2. 오후 2:25:43
     * @Description
     */
    @Transactional
    public Contents findByContentsSeq(final Long contentsSeq, final String topMenuCode, final String menuCode) {
        Optional<Contents> contents = contentsRepository.findByContentsSeqAndTopMenuCodeAndMenuCodeAndUseYn(contentsSeq, topMenuCode, menuCode, "Y");
        final Contents findContents = contents.orElseThrow(() -> new CodeMessageHandleException(ErrorEnumCode.ContentsError.NOT_FOUND.toString(), ErrorEnumCode.ContentsError.NOT_FOUND.getMessage()));
        findContents.updateReadCount(findContents.getReadCount());

        // history 저장
        historyService.save(contentsSeq, topMenuCode);

        return findContents;
    }

    /**
     * Update contents.
     *
     * @param contentsUpdateDTO the contents update dto
     * @return the contents
     * @author [이소정]
     * @CreatedOn 2020. 7. 3. 오후 4:01:24
     * @Description
     */
    @Transactional
    public Optional<Contents> update(final Long contentsSeq, final ContentsUpdateDTO contentsUpdateDTO) {
        log.info("contentsService.update");

        // contents Update
        contentsUpdateDTO.setContentsSeq(contentsSeq);
        final Optional<Contents> contents = Optional.ofNullable(contentsRepository.findById(contentsUpdateDTO.getContentsSeq()).orElseThrow(() ->
                new CodeMessageHandleException(ErrorEnumCode.ContentsError.NOT_FOUND.toString(), ErrorEnumCode.ContentsError.NOT_FOUND.getMessage())));

        // 썸네일 base64 -> file 정보로 변환
        if (!ObjectUtils.isEmpty(contentsUpdateDTO.getImageBase64())) {
            FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(ServiceEnumCode.FileFolderEnumCode.CONTENTS.getFolder(), contentsUpdateDTO.getImageBase64());

            contentsUpdateDTO.setFolderName(fileResultDTO.getFileName());
            contentsUpdateDTO.setImageFileSize(String.valueOf(fileResultDTO.getFileSize()));
            contentsUpdateDTO.setImageFilePhysicalName(fileResultDTO.getFilePhysicalName());
        }

        contents.ifPresent(value -> value.update(contentsUpdateDTO));

        // contents File
        final List<ContentsFile> beforeFileList = contentsFileRepository.findByContentsSeqAndUseYn(contents.get().getContentsSeq(), "Y");
        final List<ContentsFile> lastBeforeFileList = contentsFileRepository.findByContentsSeqAndUseYn(contents.get().getContentsSeq(), "Y");
        List<ContentsFileUpdateDTO> newFileList = contentsUpdateDTO.getContentsFileList();

        // 기존에 있는 파일 목록과 DTO받은 파일 목록 비교해서
        // case1.기본목록O, 새로운목록X : useYn = 'N' update
        // case2.기존목록X, 새로운목록O : save
        // case3.기존목록O, 새로운목록O : update
        if (!beforeFileList.isEmpty() && !newFileList.isEmpty()) {
            for (ContentsFile beforeFile : beforeFileList) {
                for (ContentsFileUpdateDTO newFile : newFileList) {
                    if (beforeFile.getContentsFileSeq() == newFile.getContentsFileSeq()) {
                        lastBeforeFileList.remove(beforeFile);
                    }
                }
            }
        }

        if (!newFileList.isEmpty()) {
            for (ContentsFileUpdateDTO contentsFileUpdateDTO : newFileList) {
                Long contentsFileSeq = contentsFileUpdateDTO.getContentsFileSeq();
                ContentsFile saveContentsFile = new ContentsFile().newContentsFile(contents.get().getContentsSeq(), contentsFileUpdateDTO);
                if (null != contentsFileSeq) {
                    Optional<ContentsFile> contentsFile = contentsFileRepository.findById(contentsFileUpdateDTO.getContentsFileSeq());
                    contentsFile.ifPresent(value -> value.update(contentsFileUpdateDTO));
                } else {
                    contentsFileRepository.save(saveContentsFile);
                }
            }
        }
        if (!lastBeforeFileList.isEmpty()) {
            for (ContentsFile contentsFile : beforeFileList) {
                contentsFile.updateUseYn("N");
            }
        }

        // 사용자 컨텐츠 권한 저장
        UserContentsSaveDTO userContentsSaveDTO = new UserContentsSaveDTO();
        userContentsSaveDTO.setChecks(contentsUpdateDTO.getChecks());
        this.saveUserContentsAuth(contentsUpdateDTO.getContentsSeq(), userContentsSaveDTO);

        // 노출인 경우 > 권한 그룹에 알림 전송
        if ("Y".equals(contentsUpdateDTO.getExposureYn())) {
            alarmService.sendAlarmTargetList(
                    ServiceEnumCode.AlarmActionEnumCode.UPDATE.toString()
                    , contentsUpdateDTO.getTopMenuCode()
                    , contentsUpdateDTO.getContentsSeq()
                    , null
                    , this.findAllAuthUser(contentsUpdateDTO.getChecks()));
        }

        return contents;
    }


    /**
     * Delete optional.
     *
     * @param contentsSeq the contents seq
     * @return the optional
     * @author [이소정]
     * @CreatedOn 2020. 7. 7. 오전 10:59:29
     * @Description
     */
    @Transactional
    public Optional<Contents> delete(final Long contentsSeq) {
        log.info("contentsService.delete");

        Optional<Contents> contents = contentsRepository.findById(contentsSeq);
        contents.ifPresent(value -> value.delete());

        List<ContentsFile> contentsFileList = contents.get().getContentsFileList();
        if (!contentsFileList.isEmpty()) {
            for (ContentsFile contentsFile : contentsFileList) {
                contentsFile.updateUseYn("N");
            }
        }

        return contents;
    }

    /**
     * Download contents file string.
     *
     * @param contentsFileSeq the contents file seq
     * @return the string
     * @author [이소정]
     * @CreatedOn 2020. 7. 16. 오후 2:51:01
     * @Description
     */
    @Transactional
    public ResponseEntity<Resource> downloadContentsFile(final Long contentsFileSeq) {
        Optional<ContentsFile> contentsFile = contentsFileRepository.findById(contentsFileSeq);
        if (contentsFile.isPresent()) {
            contentsFile.ifPresent(value -> value.updateDownloadCount(contentsFile.get().getDownloadCount()));
            return FileUtil.fileDownload(contentsFile.get().getFilePhysicalName());
        } else {
            return null;
        }
    }


    /**
     * Save user contents auth list.
     *
     * @param contentsSeq         the contents seq
     * @param userContentsSaveDTO the user contents save dto
     * @return the list
     * @author [이소정]
     * @CreatedOn 2020. 7. 24. 오후 7:01:22
     * @Description
     */
    public List<UserContents> saveUserContentsAuth(final Long contentsSeq, final UserContentsSaveDTO userContentsSaveDTO) {
        return userContentsService.save(contentsSeq, userContentsSaveDTO);
    }



    /**
     * Send email.
     *
     * @param contentsMailSendDTO the contents mail send dto
     */
    public void sendEmail(final ContentsMailSendDTO contentsMailSendDTO) {

        // 컨텐츠 조회
        Optional<Contents> contents = Optional.ofNullable(contentsRepository.findById(contentsMailSendDTO.getContentsSeq()).orElseThrow(()
                -> new CodeMessageHandleException(ErrorEnumCode.ContentsError.NOT_FOUND.toString(), ErrorEnumCode.ContentsError.NOT_FOUND.getMessage())));

        // 수신자 목록 조회
        List<ContentsUserEmailDTO> emailAuthUserList = contentsRepository.findAllContentsMailAuthUser(contentsMailSendDTO.getContentsSeq());

        // 이메일 발송
        if (!emailAuthUserList.isEmpty()) {
            for (ContentsUserEmailDTO userEmailDTO : emailAuthUserList) {
                SendDTO sendDTO = new SendDTO();
                sendDTO.setEmail(userEmailDTO.getUserId());
                sendDTO.setContentsUrl(contentsMailSendDTO.getContentsUrl());
                sendDTO.setContentsImg(userEmailDTO.getImageFilePhysicalName());

                sendDTO.setContentsName(contents.get().getFolderName());
                mailService.sendMail(
                        ServiceEnumCode.EmailTypeEnumCode.CONTENTS_UPDATE.toString(),
                        ServiceEnumCode.EmailTypeEnumCode.CONTENTS_UPDATE.getMessage(),
                        sendDTO
                );
            }
        }

    }
}
