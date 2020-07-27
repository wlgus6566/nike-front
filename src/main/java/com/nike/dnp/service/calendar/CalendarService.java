package com.nike.dnp.service.calendar;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.calendar.CalendarDaySearchDTO;
import com.nike.dnp.dto.calendar.CalendarSaveDTO;
import com.nike.dnp.dto.calendar.CalendarSearchDTO;
import com.nike.dnp.dto.calendar.CalendarUpdateDTO;
import com.nike.dnp.entity.calendar.Calendar;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.calendar.CalendarRepository;
import com.nike.dnp.util.LocalDateUtil;
import com.nike.dnp.util.MessageUtil;
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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


    /**
     * The Calendar repository
     *
     * @author [윤태호]
     */
    private final CalendarRepository calendarRepository;

    /**
     * Calendar 단건 조회.
     *
     * @param calendarSeq the calendar seq
     * @return the optional
     * @author [윤태호]
     * @CreatedOn 2020. 7. 22. 오후 4:45:45
     * @Description
     */
    public Optional<Calendar> findById(final Long calendarSeq) {
        return calendarRepository.findById(calendarSeq);
    }

    /**
     * Calendar 등록
     *
     * @param calendarSaveDTO the calendar save dto
     * @return the calendar
     * @author [윤태호]
     * @CreatedOn 2020. 7. 22. 오후 4:45:45
     * @Description
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
     * Calendar 목록 조회
     *
     * @param calendarSearchDTO the calendar search dto
     * @return the page
     * @author [윤태호]
     * @CreatedOn 2020. 7. 22. 오후 4:45:45
     * @Description
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
     * @param calendarUpdateDTO the calendar update dto
     * @return the optional
     * @author [윤태호]
     * @CreatedOn 2020. 7. 22. 오후 3:58:34
     * @Description
     */
    @Transactional
    public Calendar update(final CalendarUpdateDTO calendarUpdateDTO) {
        Optional<Calendar> calendarEntity = calendarRepository.findById(calendarUpdateDTO.getCalendarSeq());

        if(calendarEntity.isPresent()){
            calendarEntity.ifPresent(
                    value -> {

                        value.setCalendarSectionCode(calendarUpdateDTO.getCalendarSectionCode());
                        value.setScheduleName(calendarUpdateDTO.getScheduleName());
                        value.setContents(calendarUpdateDTO.getContents());
                        value.setBeginDt(LocalDateUtil.strToLocalDateTime(
                                calendarUpdateDTO.getBeginDt()+" 00:00:00","yyyy.MM.dd HH:mm:ss"));
                        value.setEndDt(LocalDateUtil.strToLocalDateTime(
                                calendarUpdateDTO.getEndDt()+" 23:59:59","yyyy.MM.dd HH:mm:ss"));

                        value.setRegisterSeq(SecurityUtil.currentUser().getUserSeq());
                        value.setUpdaterSeq(SecurityUtil.currentUser().getUserSeq());
                    }
            );
        }else{
            throw new CodeMessageHandleException(FailCode.ExceptionError.NOT_FOUND.name(), MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name()));
        }

        return calendarEntity.get();
    }

    /**
     * Calendar 삭제
     *
     * @param calendarSeq the calendar seq
     * @return the optional
     * @author [윤태호]
     * @CreatedOn 2020. 7. 22. 오후 4:45:45
     * @Description
     */
    @Transactional
    public Long delete(final Long calendarSeq) {
        calendarRepository.deleteById(calendarSeq);
        return calendarSeq;
    }

    /**
     * Find all day list.
     *
     * @param calendarDaySearchDTO the calendar day search dto
     * @return the list
     * @author [윤태호]
     * @CreatedOn 2020. 7. 22. 오후 4:45:45
     * @Description
     */
    public List<Calendar> findAllToday(final CalendarDaySearchDTO calendarDaySearchDTO) {
        LocalDateTime searchDt = LocalDateTime.now();
        if(!ObjectUtils.isEmpty(calendarDaySearchDTO.getSearchDt())){
            searchDt = LocalDateTime.of(
                    LocalDate.parse(calendarDaySearchDTO.getSearchDt() ,DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                    LocalTime.of(0,0,0));
        }
        return calendarRepository.findAllByBeginDtBeforeAndEndDtAfter(searchDt, searchDt);
    }
}
