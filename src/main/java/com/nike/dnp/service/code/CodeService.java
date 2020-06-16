package com.nike.dnp.service.code;

import com.nike.dnp.dto.auth.AuthUserDTO;
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
     * @author [오지훈]
     * CodeRepository
     */
    private final CodeRepository codeRepository;

    /**
     * 전체조회(paging)
     *
     * @param codeSearchDTO the code search dto
     * @return the list
     */
    public Page<Code> findAlls(final CodeSearchDTO codeSearchDTO) {
        return codeRepository.findAlls(
                codeSearchDTO,
                PageRequest.of(codeSearchDTO.getPage()
                        , codeSearchDTO.getSize()
                        , Sort.by("code").descending()));
    }

    /**
     * 상세조회
     *
     * @param code the code
     * @return code
     */
    public Optional<Code> findCode(final String code) {
        return codeRepository.findById(code);
    }

    /**
     * 삭제
     *
     * @param code        the code
     * @param authUserDTO the auth user dto
     * @return the optional
     */
    @Transactional
    public Optional<Code> delete(
            final String code
            , final AuthUserDTO authUserDTO
    ) {
        final Optional<Code> codeEntity = codeRepository.findById(code);
        /*if(codeEntity.isPresent()) {
            codeEntity.get().delete("N", authUserDTO.getUserSeq());
        }*/
        codeEntity.ifPresent(value -> value.delete("N", authUserDTO.getUserSeq()));
        return codeEntity;
    }

    /**
     * 등록
     *
     * @param code            the code
     * @param upperCode       the upper code
     * @param codeName        the code name
     * @param codeDescription the code description
     * @param codeOrder       the code order
     * @param useYn           the use yn
     * @param registerSeq     the register seq
     * @return the code
     */
    @Transactional
    public Code save(
            final String code
            , final String upperCode
            , final String codeName
            , final String codeDescription
            , final Long codeOrder
            , final String useYn
            , final Long registerSeq
    ) {
        //final Optional<Code> upperCodeEntity = codeRepository.findById();
        //upperCodeEntity.ifPresent(codeEntity::setUpperCode);
        final Code codeEntity = new Code();
        codeEntity.setCode(code);
        codeEntity.setUpperCode(upperCode);
        codeEntity.setCodeName(codeName);
        codeEntity.setCodeDescription(codeDescription);
        codeEntity.setCodeOrder(codeOrder);
        codeEntity.setUseYn(useYn);
        codeEntity.setRegisterSeq(registerSeq);
        codeEntity.setUpdaterSeq(registerSeq);
        return codeRepository.save(codeEntity);
    }

    /**
     * 수정
     *
     * @param code          the code
     * @param codeUpdateDTO the code update dto
     * @param authUserDTO   the auth user dto
     * @return the optional
     */
    @Transactional
    public Optional<Code> update(
            final String code
            , final CodeUpdateDTO codeUpdateDTO
            , final AuthUserDTO authUserDTO
    ) {
        final Optional<Code> codeEntity = codeRepository.findById(code);
        /*if (codeEntity.isPresent()) {
            codeEntity.get().update(
                    codeUpdateDTO.getUpperCode()
                    , codeUpdateDTO.getCodeName()
                    , codeUpdateDTO.getCodeDescription()
                    , codeUpdateDTO.getCodeOrder()
                    , codeUpdateDTO.getUseYn()
                    , authUserDTO.getUserSeq()
            );
        }*/
        codeEntity.ifPresent(value -> value.update(
                codeUpdateDTO.getUpperCode()
                , codeUpdateDTO.getCodeName()
                , codeUpdateDTO.getCodeDescription()
                , codeUpdateDTO.getCodeOrder()
                , codeUpdateDTO.getUseYn()
                , authUserDTO.getUserSeq()
        ));

        return codeEntity;
    }

    /**
     * Redis code 갱신
     *
     * @param upperCode the upper code
     */
    public void redisSaveUpperCode(String upperCode) {
        List<Code> codes = codeRepository.findAllByUpperCodeOrderByCodeOrderAsc(upperCode);
        redisService.delete(upperCode);
        redisService.set(upperCode, codes, 60);
    }

}
