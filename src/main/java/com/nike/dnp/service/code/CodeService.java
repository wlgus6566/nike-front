package com.nike.dnp.service.code;

import com.nike.dnp.dto.code.CodeSaveDTO;
import com.nike.dnp.dto.code.CodeUpdateDTO;
import com.nike.dnp.entity.code.Code;
import com.nike.dnp.exception.NotFoundHandleException;
import com.nike.dnp.repository.code.CodeRepository;
import com.nike.dnp.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

/**
 * CodeService
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:55:50
 * @implNote Code(공통 코드) Service 작성
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CodeService {

    /**
     * RedisService
     *
     * @author [오지훈]
     */
    private final RedisService redisService;

    /**
     * CodeRepository
     *
     * @author [오지훈]
     */
    private final CodeRepository codeRepository;

    /**
     * Find codes by upper code list.
     *
     * @param upperCode 상위 코드
     * @return the list
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:55:50
     * @implNote 하위 코드 목록 조회
     */
    @Cacheable(value = "cache:codes", cacheManager = "cacheManager")
    public List<Code> findCodesByUpperCode(final String upperCode) {
        return codeRepository.findByUpperCodeAndUseYnOrderByCodeOrder(upperCode, "Y");
    }

    /**
     * Delete optional.
     *
     * @param code the code
     * @return the optional
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:55:50
     * @implNote 삭제
     */
    @Transactional
    public Optional<Code> delete(final String code) {
        final Optional<Code> codeEntity = codeRepository.findByCode(code);
        codeEntity.ifPresent(value -> value.delete("N"));
        codeEntity.ifPresent(value -> this.redisSaveUpperCode(value.getUpperCode()));
        return codeEntity;
    }

    /**
     * Save code.
     *
     * @param codeSaveDTO the code save dto
     * @return the code
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:55:50
     * @implNote 등록
     */
    @Transactional
    public Code save(final CodeSaveDTO codeSaveDTO) {
        final Code codeEntity = new Code();
        codeEntity.setCode(codeSaveDTO.getCode());
        codeEntity.setUpperCode(codeSaveDTO.getUpperCode());
        codeEntity.setCodeName(codeSaveDTO.getCodeName());
        codeEntity.setCodeDescription(codeSaveDTO.getCodeDescription());
        codeEntity.setCodeOrder(codeSaveDTO.getCodeOrder());
        codeEntity.setUseYn(codeSaveDTO.getUseYn());
        final Code returnCode = codeRepository.save(codeEntity);
        this.redisSaveUpperCode(returnCode.getUpperCode());
        return returnCode;
    }

    /**
     * Update optional.
     *
     * @param code          the code
     * @param codeUpdateDTO the code update dto
     * @return the optional
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:55:50
     * @implNote 수정
     */
    @Transactional
    public Optional<Code> update(
            final String code
            , final CodeUpdateDTO codeUpdateDTO
    ) {
        final Optional<Code> codeEntity = Optional.ofNullable(codeRepository.findByCode(code).orElseThrow(
                () -> new NotFoundHandleException()
        ));

        if (codeEntity.isPresent()) {
            if (ObjectUtils.isEmpty(codeEntity.get().getUpperCode())) {
                codeEntity.ifPresent(value -> value.update(
                        codeUpdateDTO.getCodeName()
                        , codeUpdateDTO.getCodeDescription()
                        , codeUpdateDTO.getCodeOrder()
                ));
            } else {
                codeEntity.ifPresent(value -> value.update(
                        codeUpdateDTO.getCodeName()
                        , codeUpdateDTO.getCodeDescription()
                        , codeUpdateDTO.getCodeOrder()
                        , codeUpdateDTO.getUpperCode()
                ));
            }
            this.redisSaveUpperCode(codeEntity.get().getUpperCode());
        }

        return codeEntity;
    }

    /**
     * Redis save upper code.
     *
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:55:50
     * @implNote Redis code 갱신
     */
    public void redisSaveUpperCode(final String upperCode) {
        log.info("CodeService.redisSaveUpperCode");
        redisService.set("cache:codes:"+upperCode, codeRepository.findByUpperCodeAndUseYnOrderByCodeOrder(upperCode, "Y"), 60);
    }

}
