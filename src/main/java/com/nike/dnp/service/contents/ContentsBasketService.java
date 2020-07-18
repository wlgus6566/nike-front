package com.nike.dnp.service.contents;

import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.contents.ContentsBasketResultDTO;
import com.nike.dnp.entity.contents.ContentsBasket;
import com.nike.dnp.entity.contents.ContentsFile;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.contents.ContentsBasketRepository;
import com.nike.dnp.repository.contents.ContentsFileRepository;
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
 * @CreatedOn 2020. 7. 14. 오후 6:24:23
 * @Description
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
     * @CreatedOn 2020. 7. 14. 오후 6:24:19
     * @Description
     */
    public List<ContentsBasketResultDTO> getAllContentsBasket(final AuthUserDTO authUserDTO) {
        return contentsBasketRepository.findAllWithContentsFile(authUserDTO.getUserSeq());
    }

    /**
     * Save list.
     *
     * @param contentsFileSeqList the contents file seq list
     * @param authUserDTO         the auth user dto
     * @return the list
     * @author [이소정]
     * @CreatedOn 2020. 7. 15. 오후 12:02:32
     * @Description
     */
    @Transactional
    public List<ContentsBasket> save(final List<Long> contentsFileSeqList, final AuthUserDTO authUserDTO) {
        log.info("contentsBasketService.save");
        List<ContentsBasket> savedBasketList = new ArrayList<>();
        for (Long contentsFileSeq : contentsFileSeqList) {
            Optional<ContentsFile> contentsFile = contentsFileRepository.findById(contentsFileSeq);
            if (contentsFile.isPresent()) {
                ContentsBasket contentsBasket = contentsBasketRepository.save(new ContentsBasket().save(contentsFileSeq, authUserDTO));
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
     * @CreatedOn 2020. 7. 15. 오후 2:38:45
     * @Description
     */
    @Transactional
    public Optional<ContentsBasket> delete(final Long contentsBasketSeq) {
        log.info("ContentsBasketService.delete");
        Optional<ContentsBasket> contentsBasket = contentsBasketRepository.findById(contentsBasketSeq);
        final ContentsBasket savedContentsBasket = contentsBasket.orElseThrow(() -> new CodeMessageHandleException(
                ErrorEnumCode.ContentsBasketError.NOT_FOUND_BASKET.toString(), ErrorEnumCode.ContentsBasketError.NOT_FOUND_BASKET.getMessage()));
        contentsBasketRepository.delete(savedContentsBasket);
        return contentsBasket;
    }
}
