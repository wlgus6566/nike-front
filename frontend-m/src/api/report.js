import axios from 'axios';
import {setInterceptors} from '@/api/config/interceptors';

const apiReport = axios.create({ baseURL: '/api/report', timeout: 3000 });
setInterceptors(apiReport);

// 목록 조회
function getReportList(params) {
    return apiReport.get(`/`, {
        params: params,
    });
}

export { getReportList };
