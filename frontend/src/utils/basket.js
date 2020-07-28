// 상품 장바구니에 담기
import store from '@/store';
import { postBasketSave, postBasketSaveList } from '@/api/basket';

const addProductBasket = async (goodsSeq, orderQuantity) => {
    console.log(goodsSeq, orderQuantity);
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

const deleteBasketItem = async goodsBasketSeq => {
    try {
        await store.dispatch('deleteBasketItem', goodsBasketSeq);
        await store.dispatch('basketList');
    } catch (error) {
        console.log(error);
    }
};

export { addProductBasket, addBasketList, deleteBasketItem };
