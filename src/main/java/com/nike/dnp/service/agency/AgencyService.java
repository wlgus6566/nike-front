package com.nike.dnp.service.agency;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.agency.AgencySaveDTO;
import com.nike.dnp.dto.agency.AgencyUpdateDTO;
import com.nike.dnp.entity.agency.Agency;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.agency.AgencyRepository;
import com.nike.dnp.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The Class Agency service.
 *
 * @author [이소정]
 * @since 2020. 7. 20. 오후 12:08:45
 * @implNote
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AgencyService {

    /**
     * The Agency repository
     * @author [이소정]
     */
    private final AgencyRepository agencyRepository;

    /**
     * Find all list.
     *
     * @return the list
     * @author [이소정]
     * @since 2020. 7. 20. 오후 12:08:40
     * @implNote
     */
    public List<Agency> findAll() {
        return agencyRepository.findAllByUseYn("Y");
    }

    /**
     * Save agency.
     *
     * @param agencySaveDTO the agency save dto
     * @return the agency
     * @author [이소정]
     * @since 2020. 7. 20. 오후 12:19:37
     * @implNote
     */
    @Transactional
    public Agency save(final AgencySaveDTO agencySaveDTO) {
        return agencyRepository.save(new Agency().save(agencySaveDTO));
    }

    /**
     * Find by agency seq optional.
     *
     * @param agencySeq the agency seq
     * @return the optional
     * @author [이소정]
     * @since 2020. 7. 20. 오후 12:26:58
     * @implNote
     */
    public Optional<Agency> findByAgencySeq(final Long agencySeq) {
        return Optional.ofNullable(agencyRepository.findByAgencySeqAndUseYn(agencySeq, "Y").orElseThrow(() ->
                new CodeMessageHandleException(FailCode.ExceptionError.NOT_FOUND.name(), MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name()))));
    }

    /**
     * Update optional.
     *
     * @param agencyUpdateDTO the agency update dto
     * @return the optional
     * @author [이소정]
     * @since 2020. 7. 20. 오후 2:05:32
     * @implNote
     */
    @Transactional
    public Optional<Agency> update(final AgencyUpdateDTO agencyUpdateDTO) {
        Optional<Agency> savedAgency = Optional.ofNullable(agencyRepository.findByAgencySeqAndUseYn(agencyUpdateDTO.getAgencySeq(), "Y").orElseThrow(() ->
                new CodeMessageHandleException(FailCode.ExceptionError.NOT_FOUND.name(), MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name()))));
        savedAgency.ifPresent(value -> value.update(agencyUpdateDTO));
        return savedAgency;
    }

    /**
     * Delete optional.
     *
     * @param agencySeq the agency seq
     * @return the optional
     * @author [이소정]
     * @since 2020. 7. 20. 오후 2:21:07
     * @implNote
     */
    @Transactional
    public Optional<Agency> delete(final long agencySeq) {
        Optional<Agency> findAgency =Optional.ofNullable(agencyRepository.findByAgencySeqAndUseYn(agencySeq, "Y").orElseThrow(() ->
                new CodeMessageHandleException(FailCode.ExceptionError.NOT_FOUND.name(), MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name()))));
        findAgency.ifPresent(value -> value.updateUseYn("N"));
        return findAgency;
    }
}
