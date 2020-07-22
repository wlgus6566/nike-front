import Vue from 'vue';
import Vuex from 'vuex';
import { loginUser } from '@/api/login';
import {
    saveAuthToCookie,
    saveUserToCookie,
    getUserFromCookie,
    deleteCookie,
} from '@/utils/cookies.js';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        user: {},
        token: '',
    },
    getters: {
        isLoggedIn(state) {
            return !!state.token || getUserFromCookie();
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
        LOGOUT(state) {
            state.user = null;
            state.token = null;
            deleteCookie('til_auth');
            deleteCookie('til_user');
        },
    },
    actions: {
        async LOGIN({ commit }, data) {
            //const response = ;//await loginUser(data);
            commit('SET_USER', 'test@nike.co.kr' /*response.data.user*/);
            commit(
                'SET_TOKEN',
                'Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJyZHMiOiJhdXRoczp5dGg1OTE0MzE0OCIsInN1YiI6Inl0aCIsImV4cCI6MTYyNjc2NTk0MywiaWF0IjoxNTk1MjI5OTQzfQ.wg_VqONs3LYXKKHclCwYSHn0jyEv6jM13TMUqKp0vLo_Mhxp0Gwj-PWWFxNhGzsXQKhryIpGV85hXHX7t-DjVA' /*response.data.token*/
            );
            saveUserToCookie('test@nike.co.kr' /*response.data.user.username*/);
            saveAuthToCookie(
                'Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJyZHMiOiJhdXRoczp5dGg1OTE0MzE0OCIsInN1YiI6Inl0aCIsImV4cCI6MTYyNjc2NTk0MywiaWF0IjoxNTk1MjI5OTQzfQ.wg_VqONs3LYXKKHclCwYSHn0jyEv6jM13TMUqKp0vLo_Mhxp0Gwj-PWWFxNhGzsXQKhryIpGV85hXHX7t-DjVA' /*response.data.token*/
            );
            //return response;
        },
    },
});
