import {report} from './index';

//REPORT 목록 조회
function getReportList(params) {
    return report.get(`/`, {
        params: params,
    });
}

//REPORT 등록
function postReport(data) {
    return report.post(`/`, data);
}

export { getReportList, postReport };
