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
import com.nike.dnp.util.SecurityUtil;
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
 * @implNote 콘텐츠 장바구니 서비스
 * @since 2020. 7. 14. 오후 6:24:23
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentsBasketService {

    /**
     * The Contents basket repository
     *
     * @author [이소정]
     */
    private final ContentsBasketRepository contentsBasketRepository;

    /**
     * The Contents file repository
     *
     * @author [이소정]
     */
    private final ContentsFileRepository contentsFileRepository;

    /**
     * Gets all contents basket.
     *
     * @return the all contents basket
     * @author [이소정]
     * @implNote 콘텐츠 장바구니 모든 목록
     * @since 2020. 7. 14. 오후 6:24:19
     */
    public List<ContentsBasketResultDTO> findAllContentsBasket() {
        return contentsBasketRepository.findAllWithContentsFile(SecurityUtil.currentUser().getUserSeq());
    }

    /**
     * Save list.
     *
     * @param contentsFileSeqList the contents file seq list
     * @return the list
     * @author [이소정]
     * @implNote 콘텐츠 장바구니 저장
     * @since 2020. 7. 15. 오후 12:02:32
     */
    @Transactional
    public List<ContentsBasket> save(final List<Long> contentsFileSeqList) {
        log.info("contentsBasketService.save");
        List<ContentsBasket> savedBasketList = new ArrayList<>();
        for (Long contentsFileSeq : contentsFileSeqList) {
            Optional<ContentsFile> contentsFile = contentsFileRepository.findById(contentsFileSeq);
            if (contentsFile.isPresent()) {
                ContentsBasket contentsBasket = contentsBasketRepository.save(new ContentsBasket().save(contentsFileSeq, SecurityUtil.currentUser()));
                savedBasketList.add(contentsBasket);
            }
        }
        return savedBasketList;
    }

    /**
     * Find by id optional.
     *
     * @param contentsBasketSeq the contents basket seq
     * @return the optional
     * @author [이소정]
     * @implNote 콘텐츠 장바구니 seq로 상세 조회
     * @since 2020. 7. 30. 오후 2:51:30
     */
    public Optional<ContentsBasket> findById(final Long contentsBasketSeq) {
        return Optional.ofNullable(contentsBasketRepository.findById(contentsBasketSeq).orElseThrow(
                () -> new CodeMessageHandleException(FailCode.ExceptionError.NOT_FOUND.name(), MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name())))
        );
    }

    /**
     * Delete.
     *
     * @param contentsBasketSeq the contents basket seq
     * @return the contents basket
     * @author [이소정]
     * @implNote 콘텐츠 장바구니 삭제
     * @since 2020. 7. 15. 오후 2:38:45
     */
    @Transactional
    public ContentsBasket delete(final Long contentsBasketSeq) {
        log.info("ContentsBasketService.delete");
        Optional<ContentsBasket> contentsBasket = this.findById(contentsBasketSeq);
        final ContentsBasket savedContentsBasket = contentsBasket.get();
        contentsBasketRepository.delete(savedContentsBasket);
        return savedContentsBasket;
    }

    /**
     * Delete all.
     *
     * @author [이소정]
     * @implNote 콘텐츠 장바구니 전체 삭제 - 배치용
     * @since 2020. 7. 30. 오후 5:44:43
     */
    @Transactional
    public void deleteAll() {
        log.info("ContentsBasketService.deleteAll");
        contentsBasketRepository.deleteAll();
    }
}
