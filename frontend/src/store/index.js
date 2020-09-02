import Vue from 'vue';
import Vuex from 'vuex';

import router from '@/router';
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
    saveAuthNameToCookie,
} from '@/utils/cookies.js';

Vue.use(Vuex);
export default new Vuex.Store({
    state: {
        user: '',
        nick: '',
        token: '',
        authName: '',
        gnbMenuListData: [],
        basketItemDrag: false,
        fileMouseenter: false,
        basketListData: null,
        goodsBasketSeq: '',
        contBasketList: [],
        reportBasketList: [],
        reload: false,
        logoutTimer: {
            timerInterval: null,
            modalInterval: null,
            modalVisible: false,
            count: null,
        },
    },
    getters: {
        basketAppendCheck(state) {
            return state.basketItemDrag && state.fileMouseenter;
        },
        /*userToken(state) {
            console.log('userToken');
            return state.token || getAuthFromCookie();
        },*/
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
        SET_AUTHNAME(state, authName) {
            state.authName = authName;
        },
        SET_GNB(state, gnbMenu) {
            state.gnbMenuListData = gnbMenu;
        },
        SET_BASKET(state, basketList) {
            state.basketListData = basketList;
        },
        /*SET_BASKETDEL(state, goodsBasketSeq) {
            state.goodsBasketSeq = goodsBasketSeq;
        },*/

        SET_LOGOUT_TIMER(state) {
            clearInterval(state.logoutTimer.modalInterval);
            state.logoutTimer.modalInterval = setInterval(() => {
                state.logoutTimer.modalVisible = true;
                clearInterval(state.logoutTimer.modalInterval);
            }, 1000 * 60 * 25);

            clearInterval(state.logoutTimer.timerInterval);
            const expires = new Date();
            expires.setMinutes(expires.getMinutes() + 30);
            const countDownDate = expires.getTime();
            state.logoutTimer.timerInterval = setInterval(() => {
                const now = new Date().getTime();
                const distance = countDownDate - now;
                const minutes = Math.floor(
                    (distance % (1000 * 60 * 60)) / (1000 * 60)
                );
                const seconds = Math.floor((distance % (1000 * 60)) / 1000);
                state.logoutTimer.count = `${minutes}:${seconds}`;
                if (distance <= 0) {
                    state.logoutTimer.modalVisible = false;
                    this.commit('LOGOUT');
                    clearInterval(state.logoutTimer.timerInterval);
                }
            }, 1000);
        },
        LOGOUT_MODAL_STATE(state, visible) {
            state.logoutTimer.modalVisible = visible || false;
        },

        LOGOUT(state) {
            state.user = '';
            state.nick = '';
            state.token = '';
            state.authName = '';
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
            deleteCookie('user_authName');
            router.push('/login');
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
            console.log(response);
            if (response.data.code === 'SUCCESS') {
                /*commit('SET_USER', response.data.data.userId);
                commit('SET_NICK', response.data.data.nickname);
                commit('SET_AUTHNAME', response.data.data.authName);
                commit('SET_TOKEN', response.headers.authorization);*/
                saveUserIdToCookie(response.data.data.userId);
                saveUserNickToCookie(response.data.data.nickname);
                saveAuthNameToCookie(response.data.data.authName);
                saveAuthToCookie(response.headers.authorization);
            }
            return response;
        },
        // gnb 리스트 api
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
