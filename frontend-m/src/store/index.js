import Vue from 'vue';
import Vuex from 'vuex';

import { loginUser } from '@/api/login';
import { getGnbList } from '@/api/my-page';

import {
    deleteCookie,
    saveAuthToCookie,
    saveUserIdToCookie,
    saveUserNickToCookie,
} from '@/utils/cookies.js';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        menuData: [
            {
                menuSeq: 85,
                menuCode: 'HOME',
                menuName: 'HOME',
                menuPathUrl: '/',
                pcYn: 'Y',
                mobileYn: 'Y',
                managementYn: 'N',
            },
            {
                menuSeq: 16,
                menuCode: 'ASSET',
                menuName: 'ASSET',
                menuPathUrl: '/asset',
                pcYn: 'Y',
                mobileYn: 'N',
                managementYn: 'Y',
                menus: [
                    {
                        menuSeq: 51,
                        menuCode: 'ASSET_ALL',
                        upperMenuSeq: 16,
                        menuName: 'ALL',
                        upperMenuName: 'ASSET',
                        menuPathUrl: '/asset/all',
                        pcYn: 'Y',
                        mobileYn: 'N',
                        managementYn: 'N',
                    },
                    {
                        menuSeq: 23,
                        menuCode: 'ASSET_SP',
                        upperMenuSeq: 16,
                        menuName: 'SP',
                        upperMenuName: 'ASSET',
                        menuPathUrl: '/asset/sp',
                        pcYn: 'Y',
                        mobileYn: 'N',
                        managementYn: 'N',
                    },
                    {
                        menuSeq: 24,
                        menuCode: 'ASSET_SU',
                        upperMenuSeq: 16,
                        menuName: 'SU',
                        upperMenuName: 'ASSET',
                        menuPathUrl: '/asset/su',
                        pcYn: 'Y',
                        mobileYn: 'N',
                        managementYn: 'N',
                    },
                    {
                        menuSeq: 25,
                        menuCode: 'ASSET_FA',
                        upperMenuSeq: 16,
                        menuName: 'FA',
                        upperMenuName: 'ASSET',
                        menuPathUrl: '/asset/fa',
                        pcYn: 'Y',
                        mobileYn: 'N',
                        managementYn: 'N',
                    },
                    {
                        menuSeq: 26,
                        menuCode: 'ASSET_HO',
                        upperMenuSeq: 16,
                        menuName: 'HO',
                        upperMenuName: 'ASSET',
                        menuPathUrl: '/asset/ho',
                        pcYn: 'Y',
                        mobileYn: 'N',
                        managementYn: 'N',
                    },
                ],
            },
            {
                menuSeq: 17,
                menuCode: 'TOOLKIT',
                menuName: 'TOOLKIT',
                menuPathUrl: '/toolkit',
                pcYn: 'Y',
                mobileYn: 'Y',
                managementYn: 'Y',
                menus: [
                    {
                        menuSeq: 27,
                        menuCode: 'TOOLKIT_VMS',
                        upperMenuSeq: 17,
                        menuName: 'VMS',
                        upperMenuName: 'TOOLKIT',
                        menuPathUrl: '/toolkit/vms',
                        pcYn: 'Y',
                        mobileYn: 'Y',
                        managementYn: 'N',
                    },
                    {
                        menuSeq: 28,
                        menuCode: 'TOOLKIT_EKIN',
                        upperMenuSeq: 17,
                        menuName: 'EKIN',
                        upperMenuName: 'TOOLKIT',
                        menuPathUrl: '/toolkit/ekin',
                        pcYn: 'Y',
                        mobileYn: 'Y',
                        managementYn: 'N',
                    },
                    {
                        menuSeq: 29,
                        menuCode: 'TOOLKIT_SOCIAL',
                        upperMenuSeq: 17,
                        menuName: 'SOCIAL',
                        upperMenuName: 'TOOLKIT',
                        menuPathUrl: '/toolkit/social',
                        pcYn: 'Y',
                        mobileYn: 'Y',
                        managementYn: 'N',
                    },
                    {
                        menuSeq: 30,
                        menuCode: 'TOOLKIT_RB',
                        upperMenuSeq: 17,
                        menuName: 'RB',
                        upperMenuName: 'TOOLKIT',
                        menuPathUrl: '/toolkit/rb',
                        pcYn: 'Y',
                        mobileYn: 'Y',
                        managementYn: 'N',
                    },
                ],
            },
            {
                menuSeq: 18,
                menuCode: 'FOUNDATION',
                menuName: 'FOUNDATION',
                menuPathUrl: '/foundation',
                pcYn: 'Y',
                mobileYn: 'Y',
                managementYn: 'Y',
                menus: [
                    {
                        menuSeq: 31,
                        menuCode: 'FOUNDATION_VMS',
                        upperMenuSeq: 18,
                        menuName: 'VMS',
                        upperMenuName: 'FOUNDATION',
                        menuPathUrl: '/foundation/vms',
                        pcYn: 'Y',
                        mobileYn: 'Y',
                        managementYn: 'N',
                    },
                    {
                        menuSeq: 32,
                        menuCode: 'FOUNDATION_EKIN',
                        upperMenuSeq: 18,
                        menuName: 'EKIN',
                        upperMenuName: 'FOUNDATION',
                        menuPathUrl: '/foundation/ekin',
                        pcYn: 'Y',
                        mobileYn: 'Y',
                        managementYn: 'N',
                    },
                    {
                        menuSeq: 33,
                        menuCode: 'FOUNDATION_DIGITAL',
                        upperMenuSeq: 18,
                        menuName: 'DIGITAL',
                        upperMenuName: 'FOUNDATION',
                        menuPathUrl: '/foundation/digital',
                        pcYn: 'Y',
                        mobileYn: 'Y',
                        managementYn: 'N',
                    },
                    {
                        menuSeq: 34,
                        menuCode: 'FOUNDATION_RB',
                        upperMenuSeq: 18,
                        menuName: 'RB',
                        upperMenuName: 'FOUNDATION',
                        menuPathUrl: '/foundation/rb',
                        pcYn: 'Y',
                        mobileYn: 'Y',
                        managementYn: 'N',
                    },
                ],
            },
            {
                menuSeq: 19,
                menuCode: 'ORDER',
                menuName: 'ORDER',
                menuPathUrl: '/order',
                pcYn: 'Y',
                mobileYn: 'N',
                managementYn: 'Y',
                menus: [
                    {
                        menuSeq: 35,
                        menuCode: 'ORDER_PRODUCT',
                        upperMenuSeq: 19,
                        menuName: '상품관리',
                        upperMenuName: 'ORDER',
                        menuPathUrl: '/order/management',
                        pcYn: 'Y',
                        mobileYn: 'N',
                        managementYn: 'N',
                    },
                    {
                        menuSeq: 36,
                        menuCode: 'ORDER_SUB',
                        upperMenuSeq: 19,
                        menuName: '부자재',
                        upperMenuName: 'ORDER',
                        menuPathUrl: '/order/SUBSIDIARY',
                        pcYn: 'Y',
                        mobileYn: 'N',
                        managementYn: 'N',
                        menus: [
                            {
                                menuSeq: 54,
                                menuCode: 'SUBSIDIARY21',
                                upperMenuSeq: 36,
                                menuName: '운영 비품',
                                upperMenuName: '부자재',
                                menuPathUrl: '/order/SUBSIDIARY/SUBSIDIARY21',
                                pcYn: 'Y',
                                mobileYn: 'N',
                                managementYn: 'N',
                            },
                            {
                                menuSeq: 55,
                                menuCode: 'SUBSIDIARY22',
                                upperMenuSeq: 36,
                                menuName: '스태프 비품',
                                upperMenuName: '부자재',
                                menuPathUrl: '/order/SUBSIDIARY/SUBSIDIARY22',
                                pcYn: 'Y',
                                mobileYn: 'N',
                                managementYn: 'N',
                            },
                        ],
                    },
                    {
                        menuSeq: 37,
                        menuCode: 'ORDER_NBY',
                        upperMenuSeq: 19,
                        menuName: 'NIKE BY YOU',
                        upperMenuName: 'ORDER',
                        menuPathUrl: '/order/NIKE_BY_YOU',
                        pcYn: 'Y',
                        mobileYn: 'N',
                        managementYn: 'N',
                    },
                    {
                        menuSeq: 38,
                        menuCode: 'ORDER_CUSTOM',
                        upperMenuSeq: 19,
                        menuName: 'CUSTOM23',
                        upperMenuName: 'ORDER',
                        menuPathUrl: '/order/CUSTOM23',
                        pcYn: 'Y',
                        mobileYn: 'N',
                        managementYn: 'N',
                    },
                    {
                        menuSeq: 39,
                        menuCode: 'ORDER_MNQ',
                        upperMenuSeq: 19,
                        menuName: 'MNQ',
                        upperMenuName: 'ORDER',
                        menuPathUrl: '/order/MNQ',
                        pcYn: 'Y',
                        mobileYn: 'N',
                        managementYn: 'N',
                    },
                ],
            },
            {
                menuSeq: 20,
                menuCode: 'REPORT',
                menuName: 'REPORT',
                menuPathUrl: '/report',
                pcYn: 'Y',
                mobileYn: 'Y',
                managementYn: 'Y',
                menus: [
                    {
                        menuSeq: 41,
                        menuCode: 'REPORT_UPLOAD',
                        upperMenuSeq: 20,
                        menuName: 'REPORT UPLOAD',
                        upperMenuName: 'REPORT',
                        menuPathUrl: '/report/upload',
                        pcYn: 'Y',
                        mobileYn: 'Y',
                        managementYn: 'N',
                    },
                    {
                        menuSeq: 42,
                        menuCode: 'REPORT_MANAGE',
                        upperMenuSeq: 20,
                        menuName: 'REPORT 관리',
                        upperMenuName: 'REPORT',
                        menuPathUrl: '/report/management',
                        pcYn: 'Y',
                        mobileYn: 'Y',
                        managementYn: 'N',
                    },
                ],
            },
            {
                menuSeq: 21,
                menuCode: 'MANAGEMENT',
                menuName: 'MANAGEMENT',
                menuPathUrl: '/management',
                pcYn: 'Y',
                mobileYn: 'N',
                managementYn: 'Y',
                menus: [
                    {
                        menuSeq: 45,
                        menuCode: 'MANAGEMENT_VISUAL',
                        upperMenuSeq: 21,
                        menuName: '메인 비주얼 관리',
                        upperMenuName: 'MANAGEMENT',
                        menuPathUrl: '/management/visual',
                        pcYn: 'Y',
                        mobileYn: 'N',
                        managementYn: 'N',
                    },
                    {
                        menuSeq: 46,
                        menuCode: 'MANAGEMENT_ACCOUNT',
                        upperMenuSeq: 21,
                        menuName: '계정 관리',
                        upperMenuName: 'MANAGEMENT',
                        menuPathUrl: '/management/account',
                        pcYn: 'Y',
                        mobileYn: 'N',
                        managementYn: 'N',
                    },
                    {
                        menuSeq: 47,
                        menuCode: 'MANAGEMENT_GROUP',
                        upperMenuSeq: 21,
                        menuName: '권한 그룹관리',
                        upperMenuName: 'MANAGEMENT',
                        menuPathUrl: '/management/group',
                        pcYn: 'Y',
                        mobileYn: 'N',
                        managementYn: 'N',
                    },
                ],
            },
            {
                menuSeq: 40,
                menuCode: 'INFO',
                menuName: 'INFORMATION',
                menuPathUrl: '/information',
                pcYn: 'Y',
                mobileYn: 'Y',
                managementYn: 'Y',
                menus: [
                    {
                        menuSeq: 43,
                        menuCode: 'INFO_CONTACT',
                        upperMenuSeq: 40,
                        menuName: 'AGENCY CONTACT',
                        upperMenuName: 'information',
                        menuPathUrl: '/information/agency',
                        pcYn: 'Y',
                        mobileYn: 'Y',
                        managementYn: 'N',
                    },
                    {
                        menuSeq: 44,
                        menuCode: 'INFO_CALENDAR',
                        upperMenuSeq: 40,
                        menuName: 'CALENDAR',
                        upperMenuName: 'information',
                        menuPathUrl: '/information/calendar',
                        pcYn: 'Y',
                        mobileYn: 'Y',
                        managementYn: 'N',
                    },
                ],
            },
            {
                menuSeq: 75,
                menuCode: 'MYPAGE',
                menuName: 'MYPAGE',
                menuPathUrl: '/mypage',
                pcYn: 'Y',
                mobileYn: 'Y',
                managementYn: 'N',
                menus: [
                    {
                        menuSeq: 76,
                        menuCode: 'MYPAGE_HISTORY',
                        upperMenuSeq: 75,
                        menuName: 'HISTORY',
                        upperMenuName: 'MYPAGE',
                        menuPathUrl: '/mypage/history',
                        pcYn: 'Y',
                        mobileYn: 'Y',
                        managementYn: 'N',
                        menus: [
                            {
                                menuSeq: 79,
                                menuCode: 'MYPAGE_HISTORY_UPLOAD',
                                upperMenuSeq: 76,
                                menuName: '내가 업로드한 폴더',
                                upperMenuName: 'HISTORY',
                                menuPathUrl: '/mypage/upload',
                                pcYn: 'Y',
                                mobileYn: 'Y',
                                managementYn: 'N',
                            },
                            {
                                menuSeq: 80,
                                menuCode: 'MYPAGE_HISTORY_FOLDER',
                                upperMenuSeq: 76,
                                menuName: '최근 본 폴더',
                                upperMenuName: 'HISTORY',
                                menuPathUrl: '/mypage/latest',
                                pcYn: 'Y',
                                mobileYn: 'Y',
                                managementYn: 'N',
                            },
                        ],
                    },
                    {
                        menuSeq: 77,
                        menuCode: 'MYPAGE_ORDER',
                        upperMenuSeq: 75,
                        menuName: 'ORDER',
                        upperMenuName: 'MYPAGE',
                        menuPathUrl: '/mypage/order',
                        pcYn: 'Y',
                        mobileYn: 'N',
                        managementYn: 'N',
                        menus: [
                            {
                                menuSeq: 81,
                                menuCode: 'MYPAGE_ORDER_LIST',
                                upperMenuSeq: 77,
                                menuName: '주문내역확인',
                                upperMenuName: 'ORDER',
                                menuPathUrl: '/mypage/order',
                                pcYn: 'Y',
                                mobileYn: 'N',
                                managementYn: 'N',
                            },
                            {
                                menuSeq: 82,
                                menuCode: 'MYPAGE_ORDER_WISH',
                                upperMenuSeq: 77,
                                menuName: '위시리스트',
                                upperMenuName: 'ORDER',
                                menuPathUrl: '/mypage/wish-list',
                                pcYn: 'Y',
                                mobileYn: 'N',
                                managementYn: 'N',
                            },
                        ],
                    },
                    {
                        menuSeq: 78,
                        menuCode: 'MYPAGE_MYINFO',
                        upperMenuSeq: 75,
                        menuName: 'MY INFO',
                        upperMenuName: 'MYPAGE',
                        menuPathUrl: '/mypage/myinfo',
                        pcYn: 'Y',
                        mobileYn: 'Y',
                        managementYn: 'N',
                        menus: [
                            {
                                menuSeq: 83,
                                menuCode: 'MYPAGE_MYINFO_INFO',
                                upperMenuSeq: 78,
                                menuName: '회원정보 조회',
                                upperMenuName: 'MY INFO',
                                menuPathUrl: '/mypage/info',
                                pcYn: 'Y',
                                mobileYn: 'Y',
                                managementYn: 'N',
                            },
                            {
                                menuSeq: 84,
                                menuCode: 'MYPAGE_MYINFO_PASSWORD',
                                upperMenuSeq: 78,
                                menuName: '비밀번호 변경',
                                upperMenuName: 'MY INFO',
                                menuPathUrl: '/mypage/password',
                                pcYn: 'Y',
                                mobileYn: 'Y',
                                managementYn: 'N',
                            },
                        ],
                    },
                    {
                        menuSeq: 22,
                        menuCode: 'MYPAGE_CUSTOMER_CENTER',
                        upperMenuSeq: 75,
                        menuName: 'CUSTOMER CENTER',
                        upperMenuName: 'MYPAGE',
                        menuPathUrl: '/mypage/customer',
                        pcYn: 'Y',
                        mobileYn: 'Y',
                        managementYn: 'Y',
                        menus: [
                            {
                                menuSeq: 48,
                                menuCode: 'CUSTOMER_NOTICE',
                                upperMenuSeq: 22,
                                menuName: '공지사항',
                                upperMenuName: 'CUSTOMER CENTER',
                                menuPathUrl: '/mypage/notice',
                                pcYn: 'Y',
                                mobileYn: 'Y',
                                managementYn: 'N',
                            },
                            {
                                menuSeq: 49,
                                menuCode: 'CUSTOMER_NEWS',
                                upperMenuSeq: 22,
                                menuName: 'NEWS',
                                upperMenuName: 'CUSTOMER CENTER',
                                menuPathUrl: '/mypage/news',
                                pcYn: 'Y',
                                mobileYn: 'Y',
                                managementYn: 'N',
                            },
                            {
                                menuSeq: 50,
                                menuCode: 'CUSTOMER_FAQ',
                                upperMenuSeq: 22,
                                menuName: '자주 묻는 질문',
                                upperMenuName: 'CUSTOMER CENTER',
                                menuPathUrl: '/mypage/faq',
                                pcYn: 'Y',
                                mobileYn: 'Y',
                                managementYn: 'N',
                            },
                        ],
                    },
                ],
            },
        ],
    },
    getters: {},
    mutations: {
        SET_GNB(state, menuData) {
            state.menuData = menuData;
        },
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

        //gnbMenu api
        async gnbDataList({ commit }, params) {
            const {
                data: { data: response },
            } = await getGnbList(params);
            console.log('1');
            commit('SET_GNB', response);
        },
    },
});
