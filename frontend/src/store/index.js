import Vue from 'vue';
import Vuex from 'vuex';

import { loginUser } from '@/api/login';
import { getBasketList } from '@/api/basket.js';
import { getContentsBasket } from '@/api/contents';
import { getReportBasket } from '@/api/report';
import { getGnbMenu } from '@/api/mypage';

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
        gnbMenuListData: [],
        basketItemDrag: false,
        fileMouseenter: false,
        basketListData: null,
        goodsBasketSeq: '',
        contBasketList: [],
        reportBasketList: [],
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
        SET_GNB(state, gnbMenu) {
            state.gnbMenuListData = gnbMenu;
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
            state.gnbMenuListData = [];
            state.basketItemDrag = false;
            state.fileMouseenter = false;
            state.basketListData = null;
            state.goodsBasketSeq = '';
            state.contBasketList = [];
            state.reportBasketList = [];
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
        SET_REPORT_BASKET(state, data) {
            state.reportBasketList = data;
        },
    },
    actions: {
        async LOGIN({ commit }, data) {
            const response = await loginUser(data);
            if (response.data.code === 'SUCCESS') {
                commit('SET_USER', response.data.data.userId);
                commit('SET_NICK', response.data.data.nickname);
                commit('SET_TOKEN', response.headers.authorization);
                saveUserIdToCookie(response.data.data.userId);
                saveUserNickToCookie(response.data.data.nickname);
                saveAuthToCookie(response.headers.authorization);
            }
            return response;
        },
        // 장바구니 리스트 api
        async gnbMenuList({ commit }) {
            const {
                data: { data: response },
            } = await getGnbMenu();
            commit('SET_GNB', response);
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

        // report 장바구니 목록
        async getReportListBasket({ commit }) {
            const response = await getReportBasket();
            commit('SET_REPORT_BASKET', response.data.data);
            return response;
        },
    },
});
