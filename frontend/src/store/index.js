import Vue from 'vue';
import Vuex from 'vuex';
import {loginUser} from '@/api/login';
import {deleteBasket, getBasketList} from '@/api/basket.js';
import {deleteCookie, getAuthFromCookie, saveAuthToCookie,} from '@/utils/cookies.js';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        user: {},
        token: '',
        basketListData: null,
        goodsBasketSeq: '',
    },
    getters: {
        isLoggedIn(state) {
            return !!state.token || getAuthFromCookie();
        },
        userToken(state) {
            return state.token;
        },
    },
    mutations: {
        SET_USER(state, user) {
            state.user = user;
        },
        SET_TOKEN(state, token) {
            state.token = token;
        },
        SET_BASKET(state, basketList) {
            state.basketListData = basketList;
        },
        SET_BASKETDEL(state, goodsBasketSeq) {
            state.goodsBasketSeq = goodsBasketSeq;
        },
        LOGOUT(state) {
            state.user = null;
            state.token = null;
            deleteCookie('nike_token');
            //deleteCookie('nike_user');
        },
    },
    actions: {
        async LOGIN({ commit }, data) {
            const response = await loginUser(data);
            //commit('SET_USER', response.data.user);
            commit('SET_TOKEN', response.headers.authorization);
            //saveUserToCookie(response.data.user.username);
            saveAuthToCookie(response.headers.authorization);
            return response;
        },

        // 장바구니 리스트 api
        async basketList({ commit }, data) {
            const {
                data: { data: response },
            } = await getBasketList(data);
            commit('SET_BASKET', response);
        },

        //장바구니 삭제 api
        async deleteBasketItem({ commit }, goodsBasketSeq) {
            await deleteBasket(goodsBasketSeq);
            commit('SET_BASKETDEL', goodsBasketSeq);
        },
    },
});
