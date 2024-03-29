package com.nike.dnp.service.contents;

import com.nike.dnp.common.mail.MailService;
import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.dto.contents.*;
import com.nike.dnp.dto.email.SendCountDTO;
import com.nike.dnp.dto.email.SendDTO;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.dto.user.UserAuthSearchDTO;
import com.nike.dnp.dto.user.UserContentsSaveDTO;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.contents.ContentsFile;
import com.nike.dnp.entity.contents.ContentsNotificationMail;
import com.nike.dnp.entity.user.UserAuth;
import com.nike.dnp.entity.user.UserContents;
import com.nike.dnp.exception.CodeMessageHandleErrorException;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.exception.NotFoundHandleException;
import com.nike.dnp.repository.contents.ContentsFileRepository;
import com.nike.dnp.repository.contents.ContentsNotificationMailRepository;
import com.nike.dnp.repository.contents.ContentsRepository;
import com.nike.dnp.repository.user.UserAuthRepository;
import com.nike.dnp.service.DeviceService;
import com.nike.dnp.service.alarm.AlarmService;
import com.nike.dnp.service.auth.AuthService;
import com.nike.dnp.service.history.HistoryService;
import com.nike.dnp.service.user.UserContentsService;
import com.nike.dnp.service.user.UserService;
import com.nike.dnp.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Contents Service
 *
 * @author [이소정]
 * @implNote Contents Service 작성
 * @since 2020. 6. 11. 오후 3:25:23
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentsService {

    /**
     * PC Domain
     *
     * @author [오지훈]
     */
    @Value("${nike.url.pc.domain}")
    private String PC_DOMAIN;

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
     * The Contents notification mail repository
     *
     * @author [이소정]
     */
    private final ContentsNotificationMailRepository contentsNotificationMailRepository;

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
     * The Contents basket service
     *
     * @author [이소정]
     */
    private final ContentsBasketService contentsBasketService;

    /**
     * The User auth repository
     *
     * @author [이소정]
     */
    private final UserAuthRepository userAuthRepository;

    /**
     * The Auth service
     *
     * @author [이소정]
     */
    private final AuthService authService;

    /**
     * The User service
     *
     * @author [이소정]
     */
    private final UserService userService;

    /**
     * The Device service
     *
     * @author [이소정]
     */
    private final DeviceService deviceService;


    /**
     * Find all paging page.
     *
     * @param contentsSearchDTO the contents search dto
     * @param topMenuCode       the top menu code
     * @param menuCode          the menu code
     * @return the page
     * @author [이소정]
     * @implNote 콘텐츠 페이징 처리 한 목록
     * @since 2020. 7. 13. 오후 3:23:01
     */
    public Page<ContentsResultDTO> findAllPaging(final ContentsSearchDTO contentsSearchDTO, final String topMenuCode, final String menuCode) {
        log.info("ContentsService.findAllPaging");
        final Long authSeq = SecurityUtil.currentUser().getAuthSeq();
        // 권한 검사
        String searchMenuCode = topMenuCode+"_"+menuCode;
        if (ObjectUtils.isEmpty(menuCode) || ServiceCode.ContentsMenuCode.ALL.toString().equals(menuCode)) {
            searchMenuCode = topMenuCode+"_"+ServiceCode.ContentsMenuCode.ALL.toString();
            contentsSearchDTO.setExposureYn("Y".equals(this.isAuthForAssetAll(topMenuCode)) ? "Y" : null);
        } else {
            contentsSearchDTO.setExposureYn(userContentsService.isAuth(authSeq, searchMenuCode, ServiceCode.MenuSkillEnumCode.CREATE.toString()) ? null : "Y");
        }

        // 권한에 따른 조건문

        // QueryDsl 기능 이용
        contentsSearchDTO.setUserAuthSeq(authSeq);
        return contentsRepository.findPageContents(
            contentsSearchDTO,
            PageRequest.of(contentsSearchDTO.getPage()
                , contentsSearchDTO.getSize()
                , contentsSearchDTO.getOrderType().equals("START_DATE")
                        ? Sort.by(ServiceCode.SearchEnumCode.PERIOD_SECTION_CODE.getValue()).descending()
                            .and(Sort.by(ServiceCode.SearchEnumCode.START_DATE.getValue()).descending()) : Sort.by(ServiceCode.SearchEnumCode.LATEST.getValue()).descending()));
    }

    /**
     * Is auth for asset all boolean.
     *
     * @param topMenuCode the top menu code
     * @return the boolean
     * @author [이소정]
     * @implNote assetAll 관련 권한 조회
     * @since 2020. 8. 31. 오후 9:58:53
     */
    public String isAuthForAssetAll(final String topMenuCode) {
        UserAuthSearchDTO userAuthSearchDTO = new UserAuthSearchDTO();
        List<String> menuCodeList = new ArrayList<>();
        menuCodeList.add(topMenuCode+"_"+ServiceCode.AssetMenuCode.SP.toString());
        menuCodeList.add(topMenuCode+"_"+ServiceCode.AssetMenuCode.SU.toString());
        menuCodeList.add(topMenuCode+"_"+ServiceCode.AssetMenuCode.FA.toString());
        menuCodeList.add(topMenuCode+"_"+ServiceCode.AssetMenuCode.HO.toString());
        userAuthSearchDTO.setMenuCodeList(menuCodeList);
        userAuthSearchDTO.setSkillCode(ServiceCode.MenuSkillEnumCode.CREATE.toString());
        userAuthSearchDTO.setAuthSeq(SecurityUtil.currentUser().getAuthSeq());
        List<AuthReturnDTO> authList = authService.findByConfigForAssetAll(userAuthSearchDTO);
        if (ObjectUtils.isEmpty(authList)) {
            return "Y";
        } else {
            return "N";
        }
    }

    /**
     * Is auth for contents boolean.
     *
     * @param contentsSeq the contents seq
     * @param authSeq     the auth seq
     * @return the boolean
     * @author [이소정]
     * @implNote 콘텐츠 상세 권한 여부
     * @since 2020. 8. 28. 오후 6:16:12
     */
    public boolean isAuthForContents(final Long contentsSeq, final Long authSeq) {
        return userContentsService.isAuthForContents(contentsSeq, authSeq);
    }

    /**
     * Save contents.
     *
     * @param contentsSaveDTO the contents save dto
     * @param request         the request
     * @return the contents
     * @author [이소정]
     * @implNote 컨텐츠 저장
     * @since 2020. 6. 24. 오후 3:22:15
     */
    @Transactional
    public Contents save(final ContentsSaveDTO contentsSaveDTO, final HttpServletRequest request) {
        log.info("contentsService.save");

        // validation check
        this.checkContentsValidation(contentsSaveDTO);

        // device check
        contentsSaveDTO.setDevice(deviceService.checkDevice(request));

        // 썸네일 base64 -> file 정보로 변환
        if (!ObjectUtils.isEmpty(contentsSaveDTO.getImageBase64())) {
            final FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(
                    ServiceCode.FileFolderEnumCode.CONTENTS.getFolder(), contentsSaveDTO.getImageBase64());

            contentsSaveDTO.setImageFileName(fileResultDTO.getFileName());
            contentsSaveDTO.setImageFileSize(String.valueOf(fileResultDTO.getFileSize()));
            contentsSaveDTO.setImageFilePhysicalName(fileResultDTO.getFilePhysicalName());
        }

        final Contents savedContents = contentsRepository.save(new Contents().save(contentsSaveDTO));

        // 컨텐츠 파일 저장
        final List<ContentsFile> savedContentsFileList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(contentsSaveDTO.getContentsFileList()) && !contentsSaveDTO.getContentsFileList().isEmpty()) {
            // 비어있는 파일이 있는지 확인
            List<ContentsFileSaveDTO> checkedFileList = this.checkAndRemoveFile(contentsSaveDTO.getContentsFileList());

            for (final ContentsFileSaveDTO contentsFileSaveDTO : checkedFileList) {
                if (this.checkContentsFileValidation(contentsFileSaveDTO)) {
                    final ContentsFile savedContentsFile = contentsFileRepository.save(
                            new ContentsFile().save(savedContents.getContentsSeq(), this.s3FileCopySave(contentsFileSaveDTO))
                    );
                    savedContentsFileList.add(savedContentsFile);
                }
            }
        }
        savedContents.setContentsFileList(savedContentsFileList);

        // 사용자 컨텐츠 권한 저장
        List<UserContentsSaveDTO.AuthCheckDTO> saveCheckList = new ArrayList<>();
        List<UserContentsSaveDTO.AuthCheckDTO> transformAuthDTOList = this.transformAuthList(saveCheckList, contentsSaveDTO.getChecks());
        this.saveUserContentsAuth(savedContents.getContentsSeq(), transformAuthDTOList);

        // 권한 그룹에 알림 전송
        this.sendAlarm(savedContents.getContentsSeq(), contentsSaveDTO, transformAuthDTOList, ServiceCode.AlarmActionEnumCode.NEW.toString());

        // 최근 업로드 목록 추가
        historyService.saveRecentUploadHistory(savedContents.getContentsSeq(), contentsSaveDTO.getTopMenuCode());

        return savedContents;
    }


    /**
     * Transform auth list list.
     *
     * @param saveCheckList the save check list
     * @param checkList     the check list
     * @return the list
     * @author [이소정]
     * @implNote 권한 목록 형식 변환
     * @since 2020. 8. 14. 오후 2:59:37
     */
    public List<UserContentsSaveDTO.AuthCheckDTO> transformAuthList(
            final List<UserContentsSaveDTO.AuthCheckDTO> saveCheckList, final List<AuthReturnDTO> checkList
    ) {
        if (!ObjectUtils.isEmpty(checkList) && !checkList.isEmpty()) {
            for (AuthReturnDTO authReturnDTO : checkList) {
                if ("Y".equals(authReturnDTO.getViewYn()) && ServiceCode.YesOrNoEnumCode.Y.toString().equals(authReturnDTO.getCheckBoxYn())) {
                    UserContentsSaveDTO.AuthCheckDTO checkDTO = new UserContentsSaveDTO.AuthCheckDTO();
                    checkDTO.setAuthSeq(authReturnDTO.getAuthSeq());
                    checkDTO.setDetailAuthYn(authReturnDTO.getDetailAuthYn());
                    checkDTO.setEmailReceptionYn(authReturnDTO.getEmailReceptionYn());
                    saveCheckList.add(checkDTO);
                }

                if (!ObjectUtils.isEmpty(authReturnDTO.getSubAuths()) && !authReturnDTO.getSubAuths().isEmpty()) {
                    this.transformAuthList(saveCheckList, authReturnDTO.getSubAuths());
                }
            }
        }

        return saveCheckList;
    }

    /**
     * Send alarm.
     *
     * @param contentSeq           the content seq
     * @param contentsSaveDTO      the contents save dto
     * @param transformAuthDTOList the transform auth dto list
     * @param actionEnumCode       the action enum code
     * @author [이소정]
     * @implNote 노출인 경우 > 권한 그룹에 알림 전송
     * @since 2020. 8. 3. 오후 2:52:57
     */
    public void sendAlarm(
            final Long contentSeq
            , final ContentsSaveDTO contentsSaveDTO
            , final List<UserContentsSaveDTO.AuthCheckDTO> transformAuthDTOList
            , final String actionEnumCode
    ) {
        log.info("ContentsService.sendAlarm");
        if ("Y".equals(contentsSaveDTO.getExposureYn())) {
            alarmService.sendAlarmTargetList(
                    actionEnumCode
                    , contentsSaveDTO.getTopMenuCode()
                    , contentSeq
                    , null
                    , this.findAllAuthUser(transformAuthDTOList));
        }

    }

    /**
     * Find by contents seq contents.
     *
     * @param contentsSeq the contents seq
     * @param topMenuCode the top menu code
     * @param menuCode    the menu code
     * @return the contents
     * @author [이소정]
     * @implNote 컨텐츠 seq, 컨텐츠 메뉴 코드 로 컨텐츠 조회
     * @since 2020. 7. 2. 오후 2:25:43
     */
    @Transactional
    public ContentsResultDTO findByContentsSeq(final Long contentsSeq, final String topMenuCode, final String menuCode) {
        log.info("ContentsService.findByContentsSeq");
        // 상세 권한 여부 조회
        if (!this.isAuthForContents(contentsSeq, SecurityUtil.currentUser().getAuthSeq())) {
            throw new CodeMessageHandleErrorException();
        }

        final Optional<Contents> contents = contentsRepository.findByContentsSeqAndTopMenuCodeAndMenuCodeAndUseYn(contentsSeq, topMenuCode, menuCode, "Y");
        final Contents findContents = contents.orElseThrow(NotFoundHandleException::new);
         findContents.updateReadCount(findContents.getReadCount());

        // history 저장
        historyService.saveViewHistory(contentsSeq, topMenuCode);


        ContentsResultDTO contentsResultDTO = ObjectMapperUtil.map(findContents, ContentsResultDTO.class);

        // 사용자 계정 조회
        contentsResultDTO.setUserId(
                EmailPatternUtil.maskingEmail(userService.findByUserSeq(contentsResultDTO.getRegisterSeq()).getUserId())
        );

        // 메일 갯수 조회
        List<ContentsUserEmailDTO> emailAuthUserList = contentsRepository.findAllContentsMailAuthUser(contentsSeq);
        contentsResultDTO.setRecipientsCount(emailAuthUserList.size());
        return contentsResultDTO;
    }

    /**
     * Update contents.
     *
     * @param contentsSaveDTO the contents save dto
     * @return the contents
     * @author [이소정]
     * @implNote 컨텐츠 수정
     * @since 2020. 7. 3. 오후 4:01:24
     */
    @Transactional
    public Contents update(final ContentsSaveDTO contentsSaveDTO) {
        log.info("ContentsService.update");

        // validation check
        this.checkContentsValidation(contentsSaveDTO);

        final Optional<Contents> contents = this.findById(contentsSaveDTO.getContentsSeq());

        // 썸네일 base64 -> file 정보로 변환
        if(!ObjectUtils.isEmpty(contentsSaveDTO.getImageBase64()) && contentsSaveDTO.getImageBase64().contains("base64")){
            final FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(
                    ServiceCode.FileFolderEnumCode.CONTENTS.getFolder(), contentsSaveDTO.getImageBase64());

            contentsSaveDTO.setImageFileName(fileResultDTO.getFileName());
            contentsSaveDTO.setImageFileSize(String.valueOf(fileResultDTO.getFileSize()));
            contentsSaveDTO.setImageFilePhysicalName(fileResultDTO.getFilePhysicalName());
        }

        contents.ifPresent(value -> value.update(contentsSaveDTO));

        // contents File
        final List<ContentsFile> beforeFileList = contentsFileRepository.findByContentsSeqAndUseYn(contentsSaveDTO.getContentsSeq(), "Y");

        final List<ContentsFile> notUseFileList = new ArrayList<>();
        for (ContentsFile contentsFile : beforeFileList) {
            notUseFileList.add(contentsFile);
        }
        List<ContentsFileSaveDTO> newFileList = this.checkAndRemoveFile(contentsSaveDTO.getContentsFileList());
        List<Long> keepFileSeqList = new ArrayList<>();

        // 기존에 있는 파일 목록과 DTO받은 파일 목록 비교해서
        // case1.기본목록O, 새로운목록X : useYn = 'N' update
        // case2.기존목록X, 새로운목록O : save
        // case3.기존목록O, 새로운목록O : update
        if (!ObjectUtils.isEmpty(beforeFileList) && !beforeFileList.isEmpty()
                && !ObjectUtils.isEmpty(newFileList) && !newFileList.isEmpty()) {
            for (final ContentsFile beforeFile : beforeFileList) {
                for (final ContentsFileSaveDTO newFile : newFileList) {
                    if (beforeFile.getContentsFileSeq().equals(newFile.getContentsFileSeq())) {
//                        notUseFileList.remove(beforeFile);
                        keepFileSeqList.add(beforeFile.getContentsFileSeq());
                    }
                }
            }
        }

        for (Long fileSeq : keepFileSeqList) {
            for (int i = 0; i < notUseFileList.size(); i++) {
                if (fileSeq == notUseFileList.get(i).getContentsFileSeq()) {
                    notUseFileList.remove(i);
                    break;
                }
            }
        }

        if (!ObjectUtils.isEmpty(newFileList) && !newFileList.isEmpty()) {
            for (final ContentsFileSaveDTO contentsFileSaveDTO : newFileList) {
                final Long contentsFileSeq = null != contentsFileSaveDTO.getContentsFileSeq() ? contentsFileSaveDTO.getContentsFileSeq() : 0l;
                final Optional<ContentsFile> contentsFile = contentsFileRepository.findById(contentsFileSeq);

                this.checkContentsFileValidation(contentsFileSaveDTO);
                this.s3FileCopySave(contentsFileSaveDTO);
                final ContentsFile saveContentsFile = contentsFile.orElse(
                        new ContentsFile().save(contents.get().getContentsSeq(), contentsFileSaveDTO));

                if (0l != contentsFileSeq) {
                    contentsFile.ifPresent(value -> value.update(contentsFileSaveDTO));
                } else {
                    contentsFileRepository.save(saveContentsFile);
                }
            }
        }

        if (!ObjectUtils.isEmpty(notUseFileList) && !notUseFileList.isEmpty()) {
            for (final ContentsFile contentsFile : notUseFileList) {
                contentsFile.updateUseYn("N");
                // 관련 콘텐츠 장바구니 삭제
                contentsBasketService.deleteByContentsFileSeq(contentsFile.getContentsFileSeq());
            }
        }

        // 콘텐츠 미 노출 선택 한 경우  -> 장바구니 삭제처리
        if ("N".equals(contentsSaveDTO.getExposureYn())) {
            final List<ContentsFile> contentsFileList = contentsFileRepository.findByContentsSeq(contentsSaveDTO.getContentsSeq());
            for (final ContentsFile contentsFile : contentsFileList) {
                // 관련 콘텐츠 장바구니 삭제
                contentsBasketService.deleteByContentsFileSeq(contentsFile.getContentsFileSeq());
            }
        }

        // 사용자 컨텐츠 권한 저장
        List<UserContentsSaveDTO.AuthCheckDTO> saveCheckList = new ArrayList<>();
        List<UserContentsSaveDTO.AuthCheckDTO> transformAuthDTOList = this.transformAuthList(saveCheckList, contentsSaveDTO.getChecks());
        this.saveUserContentsAuth(contentsSaveDTO.getContentsSeq(), transformAuthDTOList);
        // 권한 그룹에 알림 전송
        this.sendAlarm(contentsSaveDTO.getContentsSeq(), contentsSaveDTO, transformAuthDTOList, ServiceCode.AlarmActionEnumCode.UPDATE.toString());

        return contents.get();
    }


    /**
     * Base 64 to file contents save dto.
     *
     * @param contentsSaveDTO the contents save dto
     * @return the contents save dto
     * @author [이소정]
     * @implNote base64 -> 파일 정보로 변환
     * @since 2020. 8. 3. 오후 2:57:11
     */
    public ContentsSaveDTO base64ToFile(final ContentsSaveDTO contentsSaveDTO) {
        log.info("ContentsService.base64ToFile");
        // 썸네일 base64 -> file 정보로 변환
        if (!ObjectUtils.isEmpty(contentsSaveDTO.getImageBase64())) {
            final FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(
                    ServiceCode.FileFolderEnumCode.CONTENTS.getFolder(), contentsSaveDTO.getImageBase64());

            contentsSaveDTO.setImageFileName(fileResultDTO.getFileName());
            contentsSaveDTO.setImageFileSize(String.valueOf(fileResultDTO.getFileSize()));
            contentsSaveDTO.setImageFilePhysicalName(fileResultDTO.getFilePhysicalName());
        }
        return contentsSaveDTO;
    }

    /**
     * Delete optional.
     *
     * @param contentsSeq the contents seq
     * @return the optional
     * @author [이소정]
     * @implNote 컨텐츠 삭제
     * @since 2020. 7. 7. 오전 10:59:29
     */
    @Transactional
    public Contents delete(final Long contentsSeq) {
        log.info("ContentsService.delete");

        final Optional<Contents> contents = this.findById(contentsSeq);
        contents.ifPresent(value -> value.delete());


        // 관련 콘텐츠 파일 삭제
        final List<ContentsFile> contentsFileList = contents.get().getContentsFileList();
        if (!ObjectUtils.isEmpty(contentsFileList) && !contentsFileList.isEmpty()) {
            for (final ContentsFile contentsFile : contentsFileList) {
                contentsFile.updateUseYn("N");
                // 관련 콘텐츠 장바구니 삭제
                contentsBasketService.deleteByContentsFileSeq(contentsFile.getContentsFileSeq());
            }
        }

        // 최근 본, 업로드 폴더 삭제
        historyService.deleteViewHistory(contents.get().getContentsSeq(), contents.get().getTopMenuCode());
        historyService.deleteUploadHistory(contents.get().getContentsSeq(), contents.get().getTopMenuCode());

        return contents.get();
    }

    /**
     * Find by id optional.
     *
     * @param contentsSeq the contents seq
     * @return the optional
     * @author [이소정]
     * @implNote 컨텐츠 seq 로 컨텐츠 조회 및 notFound 처리
     * @since 2020. 7. 30. 오후 12:01:26
     */
    public Optional<Contents> findById(final Long contentsSeq) {
        log.info("ContentsService.findById");
        return Optional.ofNullable(contentsRepository.findById(contentsSeq).orElseThrow(
                () -> new NotFoundHandleException()));
    }

    /**
     * Save user contents auth list.
     *
     * @param contentsSeq the contents seq
     * @param checks      the checks
     * @return the list
     * @author [이소정]
     * @implNote 컨텐츠 상세 보기 권한 저장
     * @since 2020. 7. 24. 오후 7:01:22
     */
    public List<UserContents> saveUserContentsAuth(
            final Long contentsSeq, final List<UserContentsSaveDTO.AuthCheckDTO> checks
    ) {
        log.info("ContentsService.saveUserContentsAuth");
        final UserContentsSaveDTO userContentsSaveDTO = new UserContentsSaveDTO();
        userContentsSaveDTO.setChecks(checks);
        return userContentsService.save(contentsSeq, userContentsSaveDTO);
    }

    /**
     * Send email.
     *
     * @param contentsMailSendDTO the contents mail send dto
     * @author [이소정]
     * @implNote 컨텐츠 저장, 수정 시 메일 보내기
     * @since 2020. 7. 30. 오후 12:01:26
     */
    @Transactional
    public void sendEmail(final ContentsMailSendDTO contentsMailSendDTO) {
        log.info("ContentsService.sendEmail");
        // 컨텐츠 조회
        final Optional<Contents> contents = this.findById(contentsMailSendDTO.getContentsSeq());

        // 수신자 목록 조회
        List<ContentsUserEmailDTO> emailAuthUserList = contentsRepository.findAllContentsMailAuthUser(contentsMailSendDTO.getContentsSeq());
        String[] emailList = new String[emailAuthUserList.size()];

        for (int i = 0; i < emailAuthUserList.size(); i++) {
            emailList[i] = emailAuthUserList.get(i).getUserId();
        }

        final SendDTO sendDTO = new SendDTO();
        sendDTO.setEmails(emailList);
        sendDTO.setContentsUrl(PC_DOMAIN + contentsMailSendDTO.getContentsUrl());
        sendDTO.setMailComment(contentsMailSendDTO.getComment());
        sendDTO.setContentsName(contents.get().getFolderName());

        mailService.sendMails(
                ServiceCode.EmailTypeEnumCode.CONTENTS_UPDATE.toString(),
                ServiceCode.EmailTypeEnumCode.CONTENTS_UPDATE.getMessage(),
                sendDTO
        );

//        컨텐츠 알림 메일 저장
        ContentsNotificationMail mail = new ContentsNotificationMail();
        mail.setContentsSeq(contentsMailSendDTO.getContentsSeq());
        mail.setContentsUrl(contentsMailSendDTO.getContentsUrl());
        mail.setMailComment(contentsMailSendDTO.getComment());
        contentsNotificationMailRepository.save(mail);

    }


    /**
     * Check contents validation.
     *
     * @param contentsSaveDTO the contents save dto
     * @author [이소정]
     * @implNote 콘텐츠 유효성 체크
     * @since 2020. 8. 3. 오후 3:08:15
     */
    public void checkContentsValidation(final ContentsSaveDTO contentsSaveDTO) {
        log.info("ContentsService.checkContentsValidation");
        // 등록인 경우, base64 필수
        if (ObjectUtils.isEmpty(contentsSaveDTO.getContentsSeq()) && ObjectUtils.isEmpty(contentsSaveDTO.getImageBase64())) {
            throw new CodeMessageHandleException(FailCode.ConfigureError.NULL_FOLDER_IMAGE.name(),
                    MessageUtil.getMessage(FailCode.ConfigureError.NULL_FOLDER_IMAGE.name()));
        }

        // 날짜 선택(CampaignPeriodSectionCode = SELECT) 인 경우 시작, 종료 날짜 필수
        if (contentsSaveDTO.getCampaignPeriodSectionCode().equals(ServiceCode.ContentsCampaignPeriodCode.SELECT.toString())) {
            if (ObjectUtils.isEmpty(contentsSaveDTO.getCampaignBeginDt())) {
                throw new CodeMessageHandleException(FailCode.ConfigureError.SELECT_CAMPAIGN_BEGIN_DT.name(),
                        MessageUtil.getMessage(FailCode.ConfigureError.SELECT_CAMPAIGN_BEGIN_DT.name()));
            } else if (ObjectUtils.isEmpty(contentsSaveDTO.getCampaignEndDt())) {
                throw new CodeMessageHandleException(FailCode.ConfigureError.SELECT_CAMPAIGN_END_DT.name(),
                        MessageUtil.getMessage(FailCode.ConfigureError.SELECT_CAMPAIGN_END_DT.name()));
            }
        }
    }

    /**
     * Check and remove file list.
     *
     * @param contentsFileList the contents file list
     * @return the list
     * @author [이소정]
     * @implNote 파일, 동영상 아무것도 선택하지 않은 경우 저장하지 않고 pass
     * @since 2020. 8. 14. 오후 9:36:19
     */
    public List<ContentsFileSaveDTO> checkAndRemoveFile(final List<ContentsFileSaveDTO> contentsFileList) {
        log.info("ContentsService.checkAndRemoveFile");
        List<ContentsFileSaveDTO> checkedContentsFileList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(contentsFileList) && !contentsFileList.isEmpty()) {
            for (ContentsFileSaveDTO contentsFile : contentsFileList) {
                if (
                    ((contentsFile.getFileKindCode().equals(ServiceCode.ContentsFileKindCode.VIDEO.toString())
                            || contentsFile.getFileKindCode().equals(ServiceCode.ContentsFileKindCode.VR.toString())
                        ) && !ObjectUtils.isEmpty(contentsFile.getTitle()) && !ObjectUtils.isEmpty(contentsFile.getUrl())
                    ) || (
                        contentsFile.getFileKindCode().equals(ServiceCode.ContentsFileKindCode.FILE.toString())
                            && !ObjectUtils.isEmpty(contentsFile.getFileName())
                            && !ObjectUtils.isEmpty(contentsFile.getFilePhysicalName())
                            && 0l != contentsFile.getFileSize()
                    )
                ) {
                    checkedContentsFileList.add(contentsFile);
                }
            }
        }
        return checkedContentsFileList;
    }

    /**
     * Check contents file validation.
     *
     * @param contentsFileSaveDTO the contents file save dto
     * @return the boolean
     * @author [이소정]
     * @implNote 콘텐츠 파일 유효성 체크
     * @since 2020. 8. 3. 오후 3:07:54
     */
    public boolean checkContentsFileValidation(final ContentsFileSaveDTO contentsFileSaveDTO) {
        log.info("ContentsService.checkContentsFileValidation");
        // 파일 종류가 FILE인 경우 파일 정보 필수
        if (ServiceCode.ContentsFileKindCode.FILE.toString().equals(contentsFileSaveDTO.getFileKindCode())) {
            // 새로 등록한 파일 인 경우에만 validation check
            if ((ObjectUtils.isEmpty(contentsFileSaveDTO.getContentsFileSeq()) || 0l == contentsFileSaveDTO.getContentsFileSeq())
                    && (ObjectUtils.isEmpty(contentsFileSaveDTO.getFileName())
                        || Objects.isNull(contentsFileSaveDTO.getFileSize())
                        || ObjectUtils.isEmpty(contentsFileSaveDTO.getFilePhysicalName()))) {
                log.error("File 콘텐츠 파일 정보 없음. fileName", contentsFileSaveDTO.getFileName(),
                        " / fileSize : ", contentsFileSaveDTO.getFileSize()
                        , " / filePhysicalName : ", contentsFileSaveDTO.getFilePhysicalName());
                return false;
            }
        } else {
            // 파일 종류가 VIDEO/VR 인 경우 타이틀, url 필수
            if (ObjectUtils.isEmpty(contentsFileSaveDTO.getTitle()) || ObjectUtils.isEmpty(contentsFileSaveDTO.getUrl())) {
                log.error("VIDEO/VR 콘텐츠 파일 정보 없음. title", contentsFileSaveDTO.getTitle(),
                        " / url : ", contentsFileSaveDTO.getUrl());
                return false;
            }
        }
        return true;
    }

    /**
     * S3 file copy contents file save dto.
     *
     * @param contentsFileSaveDTO the contents file save dto
     * @return the contents file save dto
     * @author [이소정]
     * @implNote 컨텐츠 저장 > 파일 경로(temp -> contents) 변경 후 set
     * @since 2020. 7. 28. 오후 4:01:22
     */
    public ContentsFileSaveDTO s3FileCopySave(final ContentsFileSaveDTO contentsFileSaveDTO) {
        log.info("ContentsService.s3FileCopySave");
        if (!ObjectUtils.isEmpty(contentsFileSaveDTO.getFilePhysicalName()) && contentsFileSaveDTO.getFilePhysicalName().contains("/temp/")) {
            contentsFileSaveDTO.setFilePhysicalName(this.fileMoveTempToRealPath(contentsFileSaveDTO.getFilePhysicalName()));
            contentsFileSaveDTO.setThumbnailFilePhysicalName(this.fileMoveTempToRealPath(contentsFileSaveDTO.getThumbnailFilePhysicalName()));
            contentsFileSaveDTO.setDetailThumbnailFilePhysicalName(this.fileMoveTempToRealPath(contentsFileSaveDTO.getDetailThumbnailFilePhysicalName()));
        }
        return contentsFileSaveDTO;
    }

    /**
     * Temp to real path file move string.
     *
     * @param filePhysicalName the file physical name
     * @return the string
     * @author [이소정]
     * @implNote 콘텐츠 파일 경로 temp -> contents
     * @since 2020. 7. 28. 오후 3:59:37
     */
    public String fileMoveTempToRealPath(final String filePhysicalName) {
        log.info("ContentsService.fileMoveTempToRealPath");
        String imgPath = filePhysicalName;
        if (null  != filePhysicalName) {
            imgPath = S3Util.fileCopyAndOldFileDelete(filePhysicalName, ServiceCode.FileFolderEnumCode.CONTENTS.getFolder(), true);
        }
        return imgPath;
    }

    /**
     * Find all auth user list.
     *
     * @param authCheckList the auth check list
     * @return the list
     * @author [이소정]
     * @implNote 컨텐츠 상세 권한 있는 authSeq에 포함된 사용자 seq 목록 조회
     * @since 2020. 7. 24. 오후 7:30:13
     */
    public List<Long> findAllAuthUser(final List<UserContentsSaveDTO.AuthCheckDTO> authCheckList) {
        log.info("ContentsService.findAllAuthUser");
        final List<Long> userSeqList = new ArrayList<>();
        for (final UserContentsSaveDTO.AuthCheckDTO authCheckDTO : authCheckList) {
            if ("Y".equals(authCheckDTO.getDetailAuthYn())) {
                // authSeq 를 가지고 userSeq 목록 가져오기
                final List<UserAuth> userAuthList = userAuthRepository.findAllByAuthSeq(authCheckDTO.getAuthSeq());
                for (final UserAuth userAuth : userAuthList) {
                    userSeqList.add(userAuth.getUserSeq());
                }
            }
        }
        return userSeqList;
    }

    /**
     * Delete contents.
     * 수정일 기준 일정기간 이전 콘텐츠 삭제 - 배치용
     *
     * @param beforeDate  the before date
     * @param topMenuCode the top menu code
     * @author [이소정]
     * @implNote 수정일 기준 일정기간 이전 콘텐츠 삭제 - 배치용
     * @since 2020. 7. 30. 오후 6:29:17
     */
    @Transactional
    public void deleteContents(final LocalDateTime beforeDate, final String topMenuCode) {
        log.info("ContentsService.deleteContents");
        final List<Contents> contentsList
                = contentsRepository.findByUpdateDtBeforeAndTopMenuCode(beforeDate, topMenuCode);
        contentsRepository.deleteAll(contentsList);
    }

    /**
     * Load auth list list.
     *
     * @param topMenuCode the top menu code
     * @param menuCode    the menu code
     * @param contentsSeq the contents seq
     * @return the list
     * @author [이소정]
     * @implNote 콘텐츠 권한 목록 조회 등록/수정 분기
     * @since 2020. 9. 9. 오후 5:35:01
     */
    public List<AuthReturnDTO> loadAuthList(final String topMenuCode, final String menuCode, final Long contentsSeq) {
        final String searchMenuCode = topMenuCode+"_"+menuCode;
        UserAuthSearchDTO userAuthSearchDTO = new UserAuthSearchDTO();
        userAuthSearchDTO.setMenuCode(searchMenuCode);
        userAuthSearchDTO.setSkillCode(ServiceCode.MenuSkillEnumCode.VIEW.toString());

        if (ObjectUtils.isEmpty(contentsSeq) || 0 == contentsSeq) {
            return this.loadDefaultAuthList(userAuthSearchDTO);
        } else {
            userAuthSearchDTO.setContentsSeq(contentsSeq);
            return authService.getAuthListWithoutN(userAuthSearchDTO, "Y");
        }
    }

    /**
     * Load auth list list.
     *
     * @param userAuthSearchDTO the user auth search dto
     * @return the list
     * @author [이소정]
     * @implNote 콘텐츠 권한 목록 조회 (등록시 사용)
     * @since 2020. 8. 13. 오후 9:26:08
     */
    public List<AuthReturnDTO> loadDefaultAuthList(final UserAuthSearchDTO userAuthSearchDTO) {
        // 상세 권한 조회
        List<AuthReturnDTO> authReturnDTOList = authService.getAuthListWithoutN(userAuthSearchDTO, "Y");

        // 등록/수정권한 목록 조회
        UserAuthSearchDTO createAuthSearchDTO = new UserAuthSearchDTO();
        createAuthSearchDTO.setMenuCode(userAuthSearchDTO.getMenuCode());
        createAuthSearchDTO.setSkillCode(ServiceCode.MenuSkillEnumCode.CREATE.toString());
        List<AuthReturnDTO> createAuthList = authService.getAuthListWithoutN(createAuthSearchDTO, "Y");

        // 등록/수정 권한 목록을 depth -> list 로 변환
        List<Long> oneDepthAuthSeqList = new ArrayList<>();
        List<Long> twoDepthAuthSeqList = new ArrayList<>();
        List<Long> threeDepthAuthSeqList = new ArrayList<>();
        // 1depth
        for (AuthReturnDTO oneDepth : createAuthList) {
            if ("Y".equals(oneDepth.getCheckBoxYn())) {
                oneDepthAuthSeqList.add(oneDepth.getAuthSeq());
            }
            // 2depth
            for (AuthReturnDTO secondDepth : oneDepth.getSubAuths()) {
                if ("Y".equals(secondDepth.getCheckBoxYn())) {
                    twoDepthAuthSeqList.add(secondDepth.getAuthSeq());
                }

                // 3depth
                for (AuthReturnDTO thirdDepth : secondDepth.getSubAuths()) {
                    if ("Y".equals(thirdDepth.getCheckBoxYn())) {
                        threeDepthAuthSeqList.add(thirdDepth.getAuthSeq());
                    }
                }
            }
        }

        return this.applyCreateAuth(authReturnDTOList, oneDepthAuthSeqList, twoDepthAuthSeqList, threeDepthAuthSeqList);
    }

    /**
     * Apply create auth list.
     *
     * @param viewAuthList      the view auth list
     * @param oneDepthSeqList   the one depth seq list
     * @param twoDepthSeqList   the two depth seq list
     * @param threeDepthSeqList the three depth seq list
     * @return the list
     * @author [이소정]
     * @implNote [method 설명]
     * @since 2020. 11. 24. 오후 6:42:00
     */
    public List<AuthReturnDTO> applyCreateAuth(
            final List<AuthReturnDTO> viewAuthList
            , final List<Long> oneDepthSeqList, final List<Long> twoDepthSeqList, final List<Long> threeDepthSeqList
    ) {
        // 1depth
        for (AuthReturnDTO oneDepth : viewAuthList) {
            for (Long authSeq : oneDepthSeqList) {
                if (oneDepth.getAuthSeq().equals(authSeq)) {
                    oneDepth.setDetailAuthYn("Y");
                    oneDepth.setEmailReceptionYn("Y");
                }
            }

            // 2depth
            for (AuthReturnDTO secondDepth : oneDepth.getSubAuths()) {
                for (Long authSeq : twoDepthSeqList) {
                    if (secondDepth.getAuthSeq().equals(authSeq)) {
                        secondDepth.setDetailAuthYn("Y");
                        secondDepth.setEmailReceptionYn("Y");
                    }
                }

                // 3depth
                for (AuthReturnDTO threeDepth : secondDepth.getSubAuths()) {
                    for (Long authSeq : threeDepthSeqList) {
                        if (threeDepth.getAuthSeq().equals(authSeq)) {
                            threeDepth.setDetailAuthYn("Y");
                            threeDepth.setEmailReceptionYn("Y");
                        }
                    }
                }
            }
        }
        return viewAuthList;
    }

    /**
     * Check send email list.
     *
     * @return list
     * @author [이소정]
     * @implNote 메일 발송 확인
     * @since 2021. 4. 8. 오후 5:16:36
     */
    public List<SendCountDTO> checkSendEmail() {
        return mailService.checkSendMail();
    }
}
