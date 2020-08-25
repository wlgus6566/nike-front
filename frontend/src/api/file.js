import { file } from './index';

// 파일 업로드
function fileUpLoad(data, config) {
    console.log(data);
    return file.post(`/upload`, data, config);
}
// 파일 업로드 리스트
function fileUpLoadList(data, config) {
    return file.put(`/uploadList`, data, config);
}
export { fileUpLoad, fileUpLoadList };
