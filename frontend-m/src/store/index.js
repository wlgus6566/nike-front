import Vue from 'vue';
import Vuex from 'vuex';

import { loginUser } from '@/api/login';
import { getGnbMenu } from '@/api/my-page';

import {
    deleteCookie,
    saveAuthToCookie,
    saveUserNickToCookie,
    saveUserIdToCookie,
    saveAuthNameToCookie,
} from '@/utils/cookies.js';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        menuData: null,
        user: '',
        nick: '',
        token: '',
        authName: '',
        timerInterval: null,
    },
    getters: {},
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
            state.menuData = gnbMenu;
        },
        SET_BASKET(state, basketList) {
            state.basketListData = basketList;
        },
        SET_BASKETDEL(state, goodsBasketSeq) {
            state.goodsBasketSeq = goodsBasketSeq;
        },
        SET_LOGOUT_TIMER(state) {
            clearInterval(state.timerInterval);
            const expires = new Date();
            expires.setMinutes(expires.getMinutes() + 30);
            const countDownDate = expires.getTime();
            state.timerInterval = setInterval(() => {
                const now = new Date().getTime();
                const distance = countDownDate - now;
                if (distance <= 0) {
                    state.saveFolder = true;
                    this.commit('LOGOUT');
                    clearInterval(state.timerInterval);
                }
            }, 1000);
        },
        LOGOUT(state) {
            state.user = '';
            state.nick = '';
            state.token = '';
            state.authName = '';
            state.basketItemDrag = false;
            state.fileMouseenter = false;
            state.basketListData = null;
            state.goodsBasketSeq = '';
            state.contBasketList = [];
            deleteCookie('user_id');
            deleteCookie('user_nick');
            deleteCookie('user_token');
            deleteCookie('user_authName');
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
            if (response.data.code === 'SUCCESS') {
                commit('SET_USER', response.data.data.userId);
                commit('SET_NICK', response.data.data.nickname);
                commit('SET_AUTHNAME', response.data.data.authName);
                commit('SET_TOKEN', response.headers.authorization);
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
    },
});
