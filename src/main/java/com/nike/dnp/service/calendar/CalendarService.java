package com.nike.dnp.service.calendar;

import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.dto.calendar.CalendarSaveDTO;
import com.nike.dnp.dto.calendar.CalendarSearchDTO;
import com.nike.dnp.entity.calendar.Calendar;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.calendar.CalendarRepository;
import com.nike.dnp.util.LocalDateUtil;
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * CalendarService
 *
 * @author [김형욱]
 * @CreatedOn 2020. 6. 29. 오후 8:58:21
 * @Description 달력 Service
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {


    private final CalendarRepository calendarRepository;

    /**
     * Calendar 단건 조회.
     *
     * @param calendarSeq the calendar seq
     * @return the optional
     */
    public Optional<Calendar> findById(final Long calendarSeq) {
        return calendarRepository.findById(calendarSeq);
    }

    /**
     * Calendar 등록
     *
     * @param calendarSaveDTO the calendar save dto
     * @return the calendar
     */
    public Calendar save(final CalendarSaveDTO calendarSaveDTO){

        ModelMapper modelMapper = new ModelMapper();
        Calendar calendar   = modelMapper.map(calendarSaveDTO, Calendar.class);

        calendar.setBeginDt(LocalDateUtil.strToLocalDateTime(
                calendarSaveDTO.getBeginDt()+" 00:00:00","yyyy.MM.dd HH:mm:ss"));
        calendar.setEndDt(LocalDateUtil.strToLocalDateTime(
                calendarSaveDTO.getEndDt()+" 23:59:59","yyyy.MM.dd HH:mm:ss"));

        calendar.setRegisterSeq(SecurityUtil.currentUser().getUserSeq());
        calendar.setUpdaterSeq(SecurityUtil.currentUser().getUserSeq());

        return calendarRepository.save(calendar);
    }

    /**
     *  Calendar 목록 조회
     *
     * @param calendarSearchDTO the calendar search dto
     * @return the page
     */
    public List<Calendar> findAll(final CalendarSearchDTO calendarSearchDTO) {
        //월의 마지막 날짜 구하기
        YearMonth targetYearMonth = YearMonth.from(LocalDate.parse(calendarSearchDTO.getYyyyMm()+".01", DateTimeFormatter.ofPattern("yyyy.MM.dd")));

        LocalDateTime beginDt = LocalDateUtil.strToLocalDateTime(
                calendarSearchDTO.getYyyyMm()+".01 00:00:00","yyyy.MM.dd HH:mm:ss");

        LocalDateTime endDt = LocalDateUtil.strToLocalDateTime(
                calendarSearchDTO.getYyyyMm()+"."+targetYearMonth.lengthOfMonth()+" 23:59:59","yyyy.MM.dd HH:mm:ss");

        return calendarRepository.findByBeginDtGreaterThanEqualAndEndDtLessThanEqual(beginDt, endDt);
    }

    /**
     * Calendar 수정
     *
     * @param calendarSaveDTO the calendar save dto
     * @return the optional
     */
    @Transactional
    public Optional<Calendar> update(final CalendarSaveDTO calendarSaveDTO) {
        Optional<Calendar> calendarEntity = calendarRepository.findById(calendarSaveDTO.getCalendarSeq());

        if(calendarEntity.isPresent()){
            calendarEntity.ifPresent(
                    value -> {

                        value.setCalendarSectionCode(calendarSaveDTO.getCalendarSectionCode());
                        value.setScheduleName(calendarSaveDTO.getScheduleName());
                        value.setContents(calendarSaveDTO.getContents());
                        value.setBeginDt(LocalDateUtil.strToLocalDateTime(
                                calendarSaveDTO.getBeginDt()+" 00:00:00","yyyy.MM.dd HH:mm:ss"));
                        value.setEndDt(LocalDateUtil.strToLocalDateTime(
                                calendarSaveDTO.getEndDt()+" 23:59:59","yyyy.MM.dd HH:mm:ss"));

                        value.setRegisterSeq(SecurityUtil.currentUser().getUserSeq());
                        value.setUpdaterSeq(SecurityUtil.currentUser().getUserSeq());
                    }
            );
        }else{
            throw new CodeMessageHandleException(ErrorEnumCode.DataError.NOT_FOUND.name(),ErrorEnumCode.DataError.NOT_FOUND.getMessage());
        }

        return calendarEntity;
    }

    /**
     * Calendar 삭제
     *
     * @param calendarSeq the calendar seq
     * @return the optional
     */
    @Transactional
    public Long delete(final Long calendarSeq) {
        calendarRepository.deleteById(calendarSeq);
        return calendarSeq;
    }

}
