import Vue from 'vue';
import Vuex from 'vuex';

import { loginUser } from '@/api/login';
import { getBasketList } from '@/api/basket.js';
import { getContentsBasket } from '@/api/contents';

import {
    deleteCookie,
    getAuthFromCookie,
    saveAuthToCookie,
    saveUserNickToCookie,
    saveUserIdToCookie,
} from '@/utils/cookies.js';

Vue.use(Vuex);
export default new Vuex.Store({
    state: {
        user: '',
        nick: '',
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
        SET_NICK(state, nick) {
            state.nick = nick;
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
            state.user = '';
            state.nick = '';
            state.token = '';
            state.basketItemDrag = false;
            state.fileMouseenter = false;
            state.basketListData = null;
            state.goodsBasketSeq = '';
            state.contBasketList = [];
            deleteCookie('user_id');
            deleteCookie('user_nick');
            deleteCookie('user_token');
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
            console.log(response);
            if (code === 'SUCCESS') {
                commit('SET_USER', data.userId);
                commit('SET_NICK', data.nickname);
                commit('SET_TOKEN', response.headers.authorization);
                saveUserIdToCookie(data.userId);
                saveUserNickToCookie(data.nickname);
                saveAuthToCookie(response.headers.authorization);
            }
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
            commit('SET_CONT_BASKET', data);
            return response;
        },
    },
});
