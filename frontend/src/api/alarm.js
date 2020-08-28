import { alarm } from './index';

//알람 목록조회
function getAlarm(param) {
    return alarm.get(``, {
        params: param,
    });
}
//알람 삭제
function delAlarm(alarmSeq, param) {
    return alarm.delete(`/${alarmSeq}`, {
        params: param,
    });
}

export { getAlarm, delAlarm };
