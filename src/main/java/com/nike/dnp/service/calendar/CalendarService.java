package com.nike.dnp.service.calendar;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.calendar.*;
import com.nike.dnp.entity.calendar.Calendar;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.exception.NotFoundHandleException;
import com.nike.dnp.repository.calendar.CalendarRepository;
import com.nike.dnp.util.LocalDateUtil;
import com.nike.dnp.util.MessageUtil;
import com.nike.dnp.util.ObjectMapperUtil;
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * CalendarService
 *
 * @author [김형욱]
 * @implNote 달력 Service
 * @since 2020. 6. 29. 오후 8:58:21
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {

    /**
     * The constant DATE_FORMAT
     *
     * @author [오지훈]
     */
    private final static String DATE_FORMAT = "yyyy.MM.dd HH:mm:ss";

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
     * @implNote Calendar 단건 조회.
     * @since 2020. 7. 22. 오후 4:45:45
     */
    public Optional<Calendar> findById(final Long calendarSeq) {
        log.info("CalendarService.findById");
        return calendarRepository.findById(calendarSeq);
    }

    /**
     * Calendar 등록
     *
     * @param calendarSaveDTO the calendar save dto
     * @return the calendar
     * @author [윤태호]
     * @implNote Calendar 등록
     * @since 2020. 7. 22. 오후 4:45:45
     */
    @Transactional
    public Calendar save(final CalendarSaveDTO calendarSaveDTO){
        log.info("CalendarService.save");
        final ModelMapper modelMapper = new ModelMapper();
        final Calendar calendar = modelMapper.map(calendarSaveDTO, Calendar.class);
        calendar.setBeginDt(LocalDateUtil.strToLocalDateTime(calendarSaveDTO.getBeginDt() + " 00:00:00", DATE_FORMAT));
        calendar.setEndDt(LocalDateUtil.strToLocalDateTime(calendarSaveDTO.getEndDt() + " 23:59:59", DATE_FORMAT));
        String [] beginDts = calendarSaveDTO.getBeginDt().split("\\.");
        String [] endDts = calendarSaveDTO.getEndDt().split("\\.");
        LocalDate beginDt = LocalDate.of(Integer.parseInt(beginDts[0]), Integer.parseInt(beginDts[1]), Integer.parseInt(beginDts[2]));
        LocalDate endDt = LocalDate.of(Integer.parseInt(endDts[0]), Integer.parseInt(endDts[1]), Integer.parseInt(endDts[2])).plusDays(1);
        while(beginDt.isBefore(endDt)){
            long cnt = calendarRepository.countByBeginDtBeforeAndEndDtAfter(beginDt.atTime(1,0,0), beginDt.atTime(1, 0, 0));
            if(cnt >= 3){
                throw new CodeMessageHandleException(FailCode.ConfigureError.MAX_INSERT_CALENDAR.name(),
                                                     MessageUtil.getMessage(FailCode.ConfigureError.MAX_INSERT_CALENDAR.name(), new String[] {beginDt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}));
            }
            beginDt = beginDt.plusDays(1);
        }
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
     * @implNote Calendar 목록 조회
     * @since 2020. 7. 22. 오후 4:45:45
     */
    public List<CalendarResultDTO> findAll(final CalendarSearchDTO calendarSearchDTO) {
        log.info("CalendarService.findAll");
        String searchYyyyMm = calendarSearchDTO.getYyyyMm();
//        String searchCalendar = calendarSearchDTO.getYyyyMm().replace(".", "");
        calendarSearchDTO.setYyyyMm(searchYyyyMm.replace(".", ""));

        java.util.Calendar cal = java.util.Calendar.getInstance();
        String yyyy = searchYyyyMm.substring(0, 4);
        String mm = searchYyyyMm.substring(5, 7);
        cal.set(java.util.Calendar.YEAR, Integer.parseInt(yyyy));
        cal.set(java.util.Calendar.MONTH, Integer.parseInt(mm)-1);

        String startDd = cal.getActualMinimum(java.util.Calendar.DAY_OF_MONTH)+"";
        if (startDd.length() != 2) {
            startDd = "0"+cal.getActualMinimum(java.util.Calendar.DAY_OF_MONTH);
        }
        calendarSearchDTO.setStartDate(LocalDateTime.parse(yyyy+"-"+mm+"-"+startDd+" 01:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        calendarSearchDTO.setEndDate(LocalDateTime.parse(yyyy+"-"+mm+"-"+cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)+" 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return ObjectMapperUtil.mapAll(calendarRepository.findByMonthSearch(calendarSearchDTO), CalendarResultDTO.class);

    }

    /**
     * 해당 날짜 일정 건수 확인
     *
     * @param calendarSearchDTO the calendar search dto
     * @return the list
     * @author [윤태호]
     * @implNote
     * @since 2020. 9. 1. 오후 4:54:18
     */
    public List<CalendarResultDTO> findAllEach(final CalendarSearchDTO calendarSearchDTO) {
        calendarSearchDTO.setYyyyMm(calendarSearchDTO.getYyyyMm().replace(".", ""));
        List<Calendar> findAllList =  calendarRepository.findByMonthSearch(calendarSearchDTO);

        final String DATE_PATTERN = "yyyy-MM-dd";
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Calendar> eachCalendarList = new ArrayList<>();

        for (Calendar calendar : findAllList) {
            Date startDate = Timestamp.valueOf(calendar.getBeginDt());
            Date endDate = Timestamp.valueOf(calendar.getEndDt());

            Date currentDate = startDate;
            while (currentDate.compareTo(endDate) <= 0) {
                Calendar newCalendar = new Calendar();
                newCalendar.setBeginDt(LocalDateTime.parse(transFormat.format(currentDate), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                newCalendar.setEndDt(LocalDateTime.parse(transFormat.format(currentDate), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                newCalendar.setCalendarSeq(calendar.getCalendarSeq());
                newCalendar.setCalendarSectionCode(calendar.getCalendarSectionCode());
                newCalendar.setScheduleName(calendar.getScheduleName());
                newCalendar.setContents(calendar.getContents());
                eachCalendarList.add(newCalendar);

                java.util.Calendar c = java.util.Calendar.getInstance();
                c.setTime(currentDate);
                c.add(java.util.Calendar.DAY_OF_MONTH, 1);
                currentDate = c.getTime();

            }
        }

        return ObjectMapperUtil.mapAll(eachCalendarList, CalendarResultDTO.class);

    }

    /**
     * Calendar 수정
     *
     * @param calendarUpdateDTO the calendar update dto
     * @return the optional
     * @author [윤태호]
     * @implNote Calendar 수정
     * @since 2020. 7. 22. 오후 3:58:34
     */
    @Transactional
    public Calendar update(final CalendarUpdateDTO calendarUpdateDTO) {
        log.info("CalendarService.update");
        final Optional<Calendar> calendarEntity = calendarRepository.findById(calendarUpdateDTO.getCalendarSeq());

        if(calendarEntity.isPresent()){
            calendarEntity.ifPresent(
                    value -> {
                        value.setCalendarSectionCode(calendarUpdateDTO.getCalendarSectionCode());
                        value.setScheduleName(calendarUpdateDTO.getScheduleName());
                        value.setContents(calendarUpdateDTO.getContents());
                        value.setBeginDt(LocalDateUtil.strToLocalDateTime(
                                calendarUpdateDTO.getBeginDt()+" 00:00:00",DATE_FORMAT));
                        value.setEndDt(LocalDateUtil.strToLocalDateTime(
                                calendarUpdateDTO.getEndDt()+" 23:59:59",DATE_FORMAT));

                        value.setRegisterSeq(SecurityUtil.currentUser().getUserSeq());
                        value.setUpdaterSeq(SecurityUtil.currentUser().getUserSeq());
                    }
            );
        }else{
            throw new NotFoundHandleException();
        }
        return calendarEntity.get();
    }

    /**
     * Calendar 삭제
     *
     * @param calendarSeq the calendar seq
     * @return the optional
     * @author [윤태호]
     * @implNote Calendar 삭제
     * @since 2020. 7. 22. 오후 4:45:45
     */
    @Transactional
    public Long delete(final Long calendarSeq) {
        log.info("CalendarService.delete");
        calendarRepository.deleteById(calendarSeq);
        return calendarSeq;
    }

    /**
     * calendar 날짜 조회
     *
     * @param calendarDaySearchDTO the calendar day search dto
     * @return the list
     * @author [윤태호]
     * @implNote calendar 날짜 조회
     * @since 2020. 7. 22. 오후 4:45:45
     */
    public List<Calendar> findAllToday(final CalendarDaySearchDTO calendarDaySearchDTO) {
        log.info("CalendarService.findAllToday");
        LocalDateTime searchDt = LocalDateTime.now();
        if(!ObjectUtils.isEmpty(calendarDaySearchDTO.getSearchDt())){
            searchDt = LocalDateTime.of(
                    LocalDate.parse(calendarDaySearchDTO.getSearchDt() ,DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                    LocalTime.of(1,0,0));
        }
        return calendarRepository.findAllByBeginDtBeforeAndEndDtAfterOrderByRegistrationDtDesc(searchDt, searchDt);
    }
}
