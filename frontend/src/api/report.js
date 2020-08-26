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
//REPORT 수정
function putReport(data, reportSeq) {
    return report.put(`/${reportSeq}`, data);
}

//REPORT 삭제
function delReport(reportSeq) {
    return report.delete(`/${reportSeq}`);
}

//REPORT 상세
function getReportDetail(reportSeq) {
    return report.get(`/${reportSeq}`);
}

//REPORT 댓글 목록조회
function getAnswerList(reportSeq) {
    return report.get(`/answer/${reportSeq}`);
}

//REPORT 댓글 등록
function postAnswerData(data) {
    return report.post(`/answer`, data);
}

//REPORT 댓글 단건 삭제
function deleteAnswerList(answerSeq) {
    return report.delete(`/answer/${answerSeq}`);
}

//REPORT 파일 목록
function getReportFile(reportSeq, param) {
    //http://10.80.1.212/api/report/file/4?size=20
    return report.get(`/file/${reportSeq}`, param);
}

//REPORT 장바구니 목록
function getReportBasket() {
    return report.get(`/basket`);
}

//REPORT 장바구니 등록
function postReportBasket(data) {
    return report.post(`/basket`, data);
}

//REPORT 장바구니 삭제
function deleteReportBasket(reportBasketSeq, data) {
    return report.delete(`/basket/${reportBasketSeq}`, data);
}

export {
    getReportList,
    getGroupAuthority,
    postReport,
    putReport,
    delReport,
    getReportDetail,
    getAnswerList,
    postAnswerData,
    deleteAnswerList,
    getReportBasket,
    postReportBasket,
    deleteReportBasket,
    getReportFile,
};
