package com.nike.dnp.service.contents;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.contents.ContentsBasketResultDTO;
import com.nike.dnp.entity.contents.ContentsBasket;
import com.nike.dnp.entity.contents.ContentsFile;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.contents.ContentsBasketRepository;
import com.nike.dnp.repository.contents.ContentsFileRepository;
import com.nike.dnp.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Class Contents basket service.
 *
 * @author [이소정]
 * @since 2020. 7. 14. 오후 6:24:23
 * @implNote
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentsBasketService {

    /**
     * The Contents basket repository
     * @author [이소정]
     */
    private final ContentsBasketRepository contentsBasketRepository;

    /**
     * The Contents file repository
     * @author [이소정]
     */
    private final ContentsFileRepository contentsFileRepository;

    /**
     * Gets all contents basket.
     *
     * @param authUserDTO the auth user dto
     * @return the all contents basket
     * @author [이소정]
     * @since 2020. 7. 14. 오후 6:24:19
     * @implNote
     */
    public List<ContentsBasketResultDTO> findAllContentsBasket(final AuthUserDTO authUserDTO) {
        return contentsBasketRepository.findAllWithContentsFile(authUserDTO.getUserSeq());
    }

    /**
     * Save list.
     *
     * @param contentsFileSeqList the contents file seq list
     * @param authUserDTO         the auth user dto
     * @return the list
     * @author [이소정]
     * @since 2020. 7. 15. 오후 12:02:32
     * @implNote
     */
    @Transactional
    public List<ContentsBasket> save(final List<Long> contentsFileSeqList, final AuthUserDTO authUserDTO) {
        log.info("contentsBasketService.save");
        final List<ContentsBasket> savedBasketList = new ArrayList<>();
        for (final Long contentsFileSeq : contentsFileSeqList) {
            final Optional<ContentsFile> contentsFile = contentsFileRepository.findById(contentsFileSeq);
            if (contentsFile.isPresent()) {
                final ContentsBasket contentsBasket = contentsBasketRepository.save(new ContentsBasket().save(contentsFileSeq, authUserDTO));
                savedBasketList.add(contentsBasket);
            }
        }
        return savedBasketList;
    }

    /**
     * Delete.
     *
     * @param contentsBasketSeq the contents basket seq
     * @return the optional
     * @author [이소정]
     * @since 2020. 7. 15. 오후 2:38:45
     * @implNote
     */
    @Transactional
    public Optional<ContentsBasket> delete(final Long contentsBasketSeq) {
        log.info("ContentsBasketService.delete");
        final Optional<ContentsBasket> contentsBasket = contentsBasketRepository.findById(contentsBasketSeq);
        final ContentsBasket savedContentsBasket = contentsBasket.orElseThrow(() -> new CodeMessageHandleException(
                FailCode.ExceptionError.NOT_FOUND.name()
                , MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name())));
        contentsBasketRepository.delete(savedContentsBasket);
        return contentsBasket;
    }
}
