import store from '@/store';
import { postBasketSave, postBasketSaveList } from '@/api/basket';

/**
 * 컨텐츠 장바구니 목록
 * @param {number} goodsSeq
 * @param {number} orderQuantity
 * @returns {Promise<void>}
 */
const addProductBasket = async (goodsSeq, orderQuantity) => {
    try {
        await postBasketSave({
            goodsSeq: goodsSeq,
            orderQuantity: orderQuantity,
        });
        await store.dispatch('basketList');
    } catch (error) {
        console.log(error);
    }
};

/**
 * 컨텐츠 장바구니 추가
 * @param {array} goodsSeqList
 * @param {array} orderQuantityList
 * @returns {Promise<void>}
 */
const addBasketList = async (goodsSeqList, orderQuantityList) => {
    try {
        await postBasketSaveList({
            goodsSeqList: goodsSeqList,
            orderQuantityList: orderQuantityList,
        });
        await store.dispatch('basketList');
    } catch (error) {
        console.log(error);
    }
};

/**
 * 컨텐츠 장바구니 삭제
 * @param {number} goodsBasketSeq
 * @returns {Promise<void>}
 */
const deleteBasketItem = async (goodsBasketSeq) => {
    try {
        await store.dispatch('deleteBasketItem', goodsBasketSeq);
        await store.dispatch('basketList');
    } catch (error) {
        console.log(error);
    }
};

export { addProductBasket, addBasketList, deleteBasketItem };
