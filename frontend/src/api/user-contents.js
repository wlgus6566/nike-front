import { user } from './index';

// 유저 컨텐츠 권한
function contentsAuth(data) {
    return user.post(`/contents`, data);
}

// 유저 컨텐츠 권한 등록
function getContentsAuthSave(params) {
    return user.post(`/contents/save`, {
        params: params,
    });
}

export { contentsAuth, getContentsAuthSave };
