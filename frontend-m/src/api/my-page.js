import { myPage } from './index';

// menu 상세 조회
function getGnbList(params) {
    console.log(params);
    return myPage.get(`/user/gnb`, {
        params: params,
    });
}
export { getGnbList };
