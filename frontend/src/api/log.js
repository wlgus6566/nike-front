import { log } from './index';

// 파일 업로드
function fileDownloadLog(data) {
    return log.post(`/download`, data);
}
export { fileDownloadLog };
