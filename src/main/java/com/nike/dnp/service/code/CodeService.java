package com.nike.dnp.service.code;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.code.CodeSaveDTO;
import com.nike.dnp.dto.code.CodeSearchDTO;
import com.nike.dnp.dto.code.CodeUpdateDTO;
import com.nike.dnp.entity.code.Code;
import com.nike.dnp.repository.code.CodeRepository;
import com.nike.dnp.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * CodeService
 *
 * @author [오지훈]
 * @Description Code(공통 코드) Service 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CodeService {

    /**
     * RedisService
     * @author [오지훈]
     */
    private final RedisService redisService;

    /**
     * CodeRepository
     * @author [오지훈]
     */
    private final CodeRepository codeRepository;

    /**
     * 전체 조회(paging)
     *
     * @param codeSearchDTO the code search dto
     * @return the list
     * @author [오지훈]
     */
    public Page<Code> findPages(final CodeSearchDTO codeSearchDTO) {
        return codeRepository.findPages(
                codeSearchDTO,
                PageRequest.of(codeSearchDTO.getPage()
                        , codeSearchDTO.getSize()
                        , Sort.by("code").descending()));
    }

    /**
     * 하위 코드 목록 조회
     *
     * @param upperCode 상위 코드
     * @return the list
     * @author [오지훈]
     */
    public List<Code> findCodesByUpperCode(final String upperCode) {
        final Optional<Code> topCode = codeRepository.findByCode(upperCode);
        return topCode.get().getSubCodes();
    }

    /**
     * 상세 조회
     *
     * @param code the code
     * @return code
     * @author [오지훈]
     */
    public Optional<Code> findByCode(final String code) {
        return codeRepository.findByCode(code);
    }

    /**
     * 삭제
     *
     * @param code        the code
     * @param authUserDTO the auth user dto
     * @return the optional
     * @author [오지훈]
     */
    @Transactional
    public Optional<Code> delete(
            final String code
            , final AuthUserDTO authUserDTO
    ) {
        final Optional<Code> codeEntity = codeRepository.findByCode(code);
        codeEntity.ifPresent(value -> value.delete("N", authUserDTO.getUserSeq()));
        this.redisSaveUpperCode();
        return codeEntity;
    }

    /**
     * 등록
     *
     * @param codeSaveDTO the code save dto
     * @param authUserDTO the auth user dto
     * @return the code
     * @author [오지훈]
     */
    @Transactional
    public Code save(
            final CodeSaveDTO codeSaveDTO
            , final AuthUserDTO authUserDTO
    ) {
        final Code codeEntity = new Code();
        codeEntity.setCode(codeSaveDTO.getCode());
        if(codeSaveDTO.getUpperYn().equals("N")) {
            codeEntity.setUpperCode(codeSaveDTO.getUpperCode());
        }
        codeEntity.setCodeName(codeSaveDTO.getCodeName());
        codeEntity.setCodeDescription(codeSaveDTO.getCodeDescription());
        codeEntity.setCodeOrder(codeSaveDTO.getCodeOrder());
        codeEntity.setUseYn(codeSaveDTO.getUseYn());
        codeEntity.setRegisterSeq(authUserDTO.getUserSeq());
        codeEntity.setUpdaterSeq(authUserDTO.getUserSeq());
        this.redisSaveUpperCode();
        return codeRepository.save(codeEntity);
    }

    /**
     * 수정
     *
     * @param code          the code
     * @param codeUpdateDTO the code update dto
     * @param authUserDTO   the auth user dto
     * @return the optional
     * @author [오지훈]
     */
    @Transactional
    public Optional<Code> update(
            final String code
            , final CodeUpdateDTO codeUpdateDTO
            , final AuthUserDTO authUserDTO
    ) {
        final Optional<Code> codeEntity = codeRepository.findByCode(code);
        if (codeEntity.get().getUpperCode().isEmpty()) {
            codeEntity.ifPresent(value -> value.update(
                    codeUpdateDTO.getCodeName()
                    , codeUpdateDTO.getCodeDescription()
                    , codeUpdateDTO.getCodeOrder()
                    , authUserDTO.getUserSeq()
            ));
        } else {
            codeEntity.ifPresent(value -> value.update(
                    codeUpdateDTO.getCodeName()
                    , codeUpdateDTO.getCodeDescription()
                    , codeUpdateDTO.getCodeOrder()
                    , authUserDTO.getUserSeq()
                    , codeUpdateDTO.getUpperCode()
            ));
        }
        this.redisSaveUpperCode();
        return codeEntity;
    }

    /**
     * Redis code 갱신
     * @author [오지훈]
     */
    public void redisSaveUpperCode() {
        log.info("findAllByUpperCodeIsNullOrderByCodeOrderAsc");
        List<Code> codes = codeRepository.findAllByUpperCodeIsNullOrderByCodeOrderAsc();

        HashMap<String, List<Code>> codeMap = new HashMap<>();
        for (Code code : codes) {
            if (!code.getCode().isEmpty()) {
                codeMap.put(code.getCode(), code.getSubCodes());
            }
        }

        redisService.delete("CODE_ARRAY");
        redisService.set("CODE_ARRAY", codeMap, 60);
    }

    /**
     * 하위 코드 목록(redis)
     *
     * @param upperCode the upper code
     * @return the list
     * @author [오지훈]
     */
    public List<Code> subCodes(String upperCode) {
        return ((HashMap<String, List<Code>>) redisService.get("CODE_ARRAY")).get(upperCode);
    }

}
