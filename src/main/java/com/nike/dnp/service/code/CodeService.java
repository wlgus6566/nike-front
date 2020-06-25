package com.nike.dnp.service.code;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.nike.dnp.common.variable.ErrorEnumCode;
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
 * @CreatedOn 2020. 6. 24. 오후 5:55:50
 * @Description Code(공통 코드) Service 작성
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
     * Find pages page.
     *
     * @param codeSearchDTO the code search dto
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:55:50
     * @Description 전체 조회(paging)
     */
    public Page<Code> findPages(final CodeSearchDTO codeSearchDTO) {
        return codeRepository.findPages(
                codeSearchDTO,
                PageRequest.of(codeSearchDTO.getPage()
                        , codeSearchDTO.getSize()
                        , Sort.by("code").descending()));
    }

    /**
     * Find codes by upper code list.
     *
     * @param upperCode 상위 코드
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:55:50
     * @Description 하위 코드 목록 조회
     */
    public List<Code> findCodesByUpperCode(final String upperCode) {
        final Optional<Code> topCode = codeRepository.findByCode(upperCode);
        return topCode.get().getSubCodes();
    }

    /**
     * Find by code optional.
     *
     * @param code the code
     * @return code optional
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:55:50
     * @Description 상세 조회
     */
    public Optional<Code> findByCode(final String code) {
        return codeRepository.findByCode(code);
    }

    /**
     * Delete optional.
     *
     * @param code        the code
     * @param authUserDTO the auth user dto
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:55:50
     * @Description 삭제
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
     * Save code.
     *
     * @param codeSaveDTO the code save dto
     * @param authUserDTO the auth user dto
     * @return the code
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:55:50
     * @Description 등록
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
     * Update optional.
     *
     * @param code          the code
     * @param codeUpdateDTO the code update dto
     * @param authUserDTO   the auth user dto
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:55:50
     * @Description 수정
     */
    @Transactional
    public Optional<Code> update(
            final String code
            , final CodeUpdateDTO codeUpdateDTO
            , final AuthUserDTO authUserDTO
    ) {
        final Optional<Code> codeEntity = Optional.ofNullable(codeRepository.findByCode(code).orElseThrow(
                () -> new UserNotFoundException(ErrorEnumCode.DataError.NOT_FOUND.toString())
        ));

        if (codeEntity.isPresent()) {
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
        }

        return codeEntity;
    }

    /**
     * Redis save upper code.
     *
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:55:50
     * @Description Redis code 갱신
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
     * Sub codes list.
     *
     * @param upperCode the upper code
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:55:50
     * @Description 하위 코드 목록(redis)
     */
    public List<Code> subCodes(String upperCode) {
        return ((HashMap<String, List<Code>>) redisService.get("CODE_ARRAY")).get(upperCode);
    }

}
