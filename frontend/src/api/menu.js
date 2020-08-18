import { menu } from './index';

// 파일 업로드
function getMenu() {
    return menu.get(`/`);
}
// 파일 업로드 리스트
function getMenuManage() {
    return menu.get(`/manage`);
}
export { getMenu, getMenuManage };
