import { calendar } from './index';

//CALENDAR 목록 조회
function getCalendarList(params) {
    return calendar.get(`/`, {
        params: params,
    });
}
//CALENDAR 등록
function postCalendar(data) {
    return calendar.post(`/`, data);
}
//CALENDAR 오늘 조회
function getTodayCalendar(params) {
    return calendar.get(`/today`, {
        params: params,
    });
}
//CALENDAR 상세조회
function getDetailCalendar(calendarSeq) {
    return calendar.get(`/view/${calendarSeq}`);
}
//CALENDAR 삭제
function delCalendar(calendarSeq) {
    return calendar.delete(`/${calendarSeq}`);
}
//CALENDAR 수정
function putCalendar(calendarSeq, data) {
    return calendar.put(`/${calendarSeq}`, data);
}
export {
    getCalendarList,
    postCalendar,
    getTodayCalendar,
    getDetailCalendar,
    delCalendar,
    putCalendar
};
