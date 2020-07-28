// 상품 장바구니에 담기
import store from '@/store';
import {postBasketSave} from '@/api/basket';

const addProductBasket = async (goodsSeq, orderQuantity) => {
    try {
        const {
            data: { data: response },
        } = await postBasketSave({
            goodsSeq: goodsSeq,
            orderQuantity: orderQuantity,
        });
        await store.dispatch('basketList');
    } catch (error) {
        console.log(error);
    }
};

const deleteBasketItem = async (goodsBasketSeq) => {
    try {
        await store.dispatch('deleteBasketItem', goodsBasketSeq);
        await store.dispatch('basketList');
    } catch (error) {
        console.log(error);
    }
};

export { addProductBasket, deleteBasketItem };
