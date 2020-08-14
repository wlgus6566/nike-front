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

export { getWishList, postWishList, deleteWishList, deleteWishListCheck };
