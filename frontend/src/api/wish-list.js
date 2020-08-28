import { wishList } from './index';

function getWishList(params) {
    return wishList.get(`/mypage/wishlist/list`, {
        params: params,
    });
}
function postWishList(data) {
    return wishList.post(`/wishlist/save`, { data });
}
function deleteWishList(seq) {
    return wishList.delete(`/mypage/wishlist/delete/${seq}`);
}
function deleteWishListCheck(data) {
    return wishList.delete(`/mypage/wishlist/delete/`, { data });
}
function getWishCheck(params) {
    return wishList.get(`/wishlist/check`, { params: params });
}

export {
    getWishList,
    postWishList,
    deleteWishList,
    deleteWishListCheck,
    getWishCheck,
};
