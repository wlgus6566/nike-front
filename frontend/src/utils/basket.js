import store from '@/store';
import { deleteBasket, postBasketSave, postBasketSaveList } from '@/api/basket';

/**
 * 장바구니 상품 단건 추가
 * @param {number} goodsSeq
 * @param {number} orderQuantity
 * @param {boolean} msg
 * @returns {Promise<void>}
 */
const addProductBasket = async (goodsSeq, orderQuantity, msg) => {
    try {
        const response = await postBasketSave({
            goodsSeq: goodsSeq,
            orderQuantity: orderQuantity,
        });
        //console.log(response);
        await store.dispatch('basketList');
        if (msg) {
            alert('CART에 담겼습니다');
        }
        if (response.data.existMsg) {
            alert(response.data.msg);
        }
    } catch (error) {
        console.error(error);
        alert(error.data.msg);
    }
};

/**
 * 장바구니 상품 다건 추가
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
        console.error(error);
    }
};

/**
 * 장바구니 상품 단건 삭제
 * @param {number} goodsBasketSeq
 * @returns {Promise<void>}
 */
const deleteBasketItem = async (goodsBasketSeq) => {
    try {
        await deleteBasket(goodsBasketSeq);
        await store.dispatch('basketList');
    } catch (error) {
        console.error(error);
    }
};

export { addProductBasket, addBasketList, deleteBasketItem };
