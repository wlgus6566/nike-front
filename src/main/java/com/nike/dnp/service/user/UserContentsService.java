package com.nike.dnp.service.user;

import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.dto.user.UserContentsSaveDTO;
import com.nike.dnp.dto.user.UserContentsSearchDTO;
import com.nike.dnp.entity.user.UserContents;
import com.nike.dnp.repository.user.UserContentsRepository;
import com.nike.dnp.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * UserContentsService
 *
 * @author [오지훈]
 * @implNote UserContents(유저 컨텐츠 권한) Service 작성
 * @since 2020. 6. 22. 오후 2:40:43
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserContentsService {

    /**
     * The User contents repository
     *
     * @author [오지훈]
     */
    private final UserContentsRepository userContentsRepository;

    /**
     * The Auth repository
     *
     * @author [오지훈]
     */
    private final AuthService authService;

    /**
     * Save list.
     *
     * @param contentsSeq         the contents seq
     * @param userContentsSaveDTO the user contents save dto
     * @return the list
     * @author [오지훈]
     * @implNote 컨텐츠 권한 저장
     * @since 2020. 7. 20. 오후 12:30:08
     */
    public List<UserContents> save (final Long contentsSeq, final UserContentsSaveDTO userContentsSaveDTO) {
        log.info("UserContentsService.save");
        userContentsRepository.deleteByContentsSeq(contentsSeq);

        final List<UserContents> contents = new ArrayList<>();
        for (final UserContentsSaveDTO.AuthCheckDTO authCheckDTO : userContentsSaveDTO.getChecks()) {
            final UserContents userContents = userContentsRepository.save(
                    new UserContents().save(
                        contentsSeq
                        , authCheckDTO.getAuthSeq()
                        , authCheckDTO.getDetailAuthYn()
                        , authCheckDTO.getEmailReceptionYn()
                    )
            );
            contents.add(userContents);
        }

        return contents;
    }

    /**
     * Is auth boolean.
     *
     * @param authSeq               the auth seq
     * @param userContentsSearchDTO the user contents search dto
     * @return the boolean
     * @author [오지훈]
     * @implNote 권한 존재 여부
     * @since 2020. 7. 20. 오후 4:25:14
     */
    public boolean isAuth(final Long authSeq, final UserContentsSearchDTO userContentsSearchDTO) {
        log.info("UserContentsService.isAuth");
        boolean result = false;
        for (final AuthReturnDTO authReturnDTO : authService.getAuthList(userContentsSearchDTO)) {
            if (authSeq.equals(authReturnDTO.getAuthSeq())) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Is auth for contents boolean.
     *
     * @param contentsSeq the contents seq
     * @param authSeq     the auth seq
     * @return the boolean
     * @author [이소정]
     * @implNote 콘텐츠 상세 권한 여부
     * @since 2020. 8. 28. 오후 6:13:54
     */
    public boolean isAuthForContents(final Long contentsSeq, final Long authSeq) {
        List<UserContents> userContentsList = userContentsRepository.findAllByContentsSeqAndAuthSeqAndDetailAuthYn(contentsSeq, authSeq, "Y");
        if (!userContentsList.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

}
