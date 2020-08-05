import Vue from 'vue';
import Vuex from 'vuex';

import { loginUser } from '@/api/login';
import { getBasketList } from '@/api/basket.js';
import { getContentsBasket } from '@/api/contents';

import {
    deleteCookie,
    getAuthFromCookie,
    saveUserToCookie,
    saveAuthToCookie,
} from '@/utils/cookies.js';

Vue.use(Vuex);
export default new Vuex.Store({
    state: {
        user: {},
        token: '',
        basketItemDrag: false,
        fileMouseenter: false,
        basketListData: null,
        goodsBasketSeq: '',
        contBasketList: [],
        reload: false,
    },
    getters: {
        basketAppendCheck(state) {
            return state.basketItemDrag && state.fileMouseenter;
        },
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
        SET_CONT_BASKET(state, data) {
            state.contBasketList = data;
        },
        SET_BASKET_ITEM_DRAG(state, data) {
            state.basketItemDrag = data;
        },
        SET_FILE_MOUSEENTER(state, data) {
            state.fileMouseenter = data;
        },
        SET_RELOAD(state, data) {
            state.reload = data;
        },
    },
    actions: {
        async LOGIN({ commit }, data) {
            const response = await loginUser(data);
            commit('SET_USER', response.headers.username);
            saveUserToCookie(response.headers.username);
            commit('SET_TOKEN', response.headers.authorization);
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

        // 컨텐츠 장바구니 목록
        async getContBasket({ commit }) {
            const response = await getContentsBasket();
            commit('SET_CONT_BASKET', response.data.data);
            return response;
        },
    },
});
