import { alarm } from './index';

//알람 목록조회
function getAlarm(data) {
    return alarm.get(`/`, data);
}

export { getAlarm };
