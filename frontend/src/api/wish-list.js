import { wishList } from './index';

function getWishList(params) {
    return wishList.get(`/mypage/wishlist/list`, {
        params: params,
    });
}
function postWishList(params) {
    return wishList.post(`/wishlist/save`, null, {
        params: params,
    });
}
function deleteWishList(seq) {
    return wishList.delete(`/mypage/wishlist/delete/${seq}`);
}
function deleteWishListCheck(data) {
    return wishList.delete(`/mypage/wishlist/delete/`, { data });
}
function getWishCheck(params) {
    console.log(params);
    return wishList.get(`/wishlist/check`, { params: params });
}

export {
    getWishList,
    postWishList,
    deleteWishList,
    deleteWishListCheck,
    getWishCheck,
};
