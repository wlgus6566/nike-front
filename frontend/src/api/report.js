import { report } from './index';

// 목록 조회
function getReportList(params) {
    return report.get(`/`, {
        params: params,
    });
}

export { getReportList };
