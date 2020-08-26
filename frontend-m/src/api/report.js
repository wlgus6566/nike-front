import { report } from './index';

//REPORT 목록 조회
function getReportList(params) {
    return report.get(`/`, {
        params: params,
    });
}
//REPORT 그룹 depth별 목록조회
function getGroupAuthority(params) {
    return report.get(`/groupList`, {
        params: params,
    });
}
//REPORT 등록
function postReport(data) {
    return report.post(`/`, data);
}

// REPORT 상세 조회
function getReportDetail(id) {
    return report.get(`/` + id);
}

// REPORT 상세 조회 파일 리스트
function getReportDetailFileList(id, params) {
    return report.get(`/file/` + id, {
        params: params,
    });
}

// REPORT 상세 조회 피드백 리스트
function getReportDetailFeedbackList(id) {
    return report.get(`/answer/` + id);
}

// REPORT 피드백 등록
function postReportFeedback(data) {
    return report.post(`/answer`, data);
}

function deleteReportFeedback(id) {
    return report.delete('/answer/' + id);
}

// REPORT 삭제
function deleteReport(id) {
    return report.delete('/' + id);
}

export {
    getReportList,
    getGroupAuthority,
    postReport,
    getReportDetail,
    getReportDetailFileList,
    getReportDetailFeedbackList,
    postReportFeedback,
    deleteReportFeedback,
    deleteReport,
};
