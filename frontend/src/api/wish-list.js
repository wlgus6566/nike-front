import { wishList } from './index';

function getWishList(params) {
    return wishList.get(`/list`, {
        params: params,
    });
}
function postWishList(params) {
    return wishList.post(`/save`, null, {
        params: params,
    });
}
function deleteWishList(seq) {
    return wishList.delete(`/delete/${seq}`);
}
function deleteWishListCheck(data) {
    return wishList.delete(`/delete/`, { data });
}

export { getWishList, postWishList, deleteWishList, deleteWishListCheck };
