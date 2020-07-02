import { pages } from '@/util/global-methods';

const routes = [
    {
        path: '/pub',
        component: () => import('@/pub/index.vue'),
        meta: { layout: 'Pub' },
        children: [
            {
                path: 'pageList',
                component: () => import('@/pub/PageList.vue'),
                meta: { layout: 'Pub' },
            },
            //guide
            {
                path: 'button',
                component: () => import('@/pub/button.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'input',
                component: () => import('@/pub/input.vue'),
                meta: { layout: 'Pub' },
            },
            //main
            {
                path: 'main',
                component: () => import('@/pub/mainPage.vue'),
                meta: { layout: 'Pub' },
            },
            //LOGIN
            {
                path: 'NIKE_P_LOGIN_01',
                component: () => import('@/pub/NIKE_P_LOGIN_01.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_LOGIN_02',
                component: () => import('@/pub/NIKE_P_LOGIN_02.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_LOGIN_03',
                component: () => import('@/pub/NIKE_P_LOGIN_03.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_LOGIN_04',
                component: () => import('@/pub/NIKE_P_LOGIN_04.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_LOGIN_05',
                component: () => import('@/pub/NIKE_P_LOGIN_05.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_LOGIN_06',
                component: () => import('@/pub/NIKE_P_LOGIN_06.vue'),
                meta: { layout: 'Pub' },
            },
            //gnb - ASSET
            {
                path: 'NIKE_P_ASSET_01',
                component: () => import('@/pub/NIKE_P_ASSET_01.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'ASSET_SP_LIST',
                component: () => import('@/pub/ASSET_SP_LIST.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'ASSET_SP_DETAIL',
                component: () => import('@/pub/ASSET_SP_DETAIL.vue'),
                meta: { layout: 'Pub' },
            },
            //gnb - TOOLKIT
            {
                path: 'NIKE_P_TOOLKIT_01',
                component: () => import('@/pub/NIKE_P_TOOLKIT_01.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_TOOLKIT_02',
                component: () => import('@/pub/NIKE_P_TOOLKIT_02.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_TOOLKIT_03',
                component: () => import('@/pub/NIKE_P_TOOLKIT_03.vue'),
                meta: { layout: 'Pub' },
            },
            //gnb - FOUNDATION
            {
                path: 'NIKE_P_FD_01',
                component: () => import('@/pub/NIKE_P_FD_01.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_FD_02',
                component: () => import('@/pub/NIKE_P_FD_02.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_FD_03',
                component: () => import('@/pub/NIKE_P_FD_03.vue'),
                meta: { layout: 'Pub' },
            },
            //gnb - ORDER: 부자재 주문
            {
                path: 'NIKE_P_ORDER_01',
                component: () => import('@/pub/NIKE_P_ORDER_01.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_ORDER_02',
                component: () => import('@/pub/NIKE_P_ORDER_02.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_ORDER_07',
                component: () => import('@/pub/NIKE_P_ORDER_07.vue'),
                meta: { layout: 'Pub' },
            },
            //gnb - REPORT 시공보고서
            {
                path: 'NIKE_P_REPORT_01',
                component: () => import('@/pub/NIKE_P_REPORT_01.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_REPORT_02',
                component: () => import('@/pub/NIKE_P_REPORT_02.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_REPORT_03',
                component: () => import('@/pub/NIKE_P_REPORT_03.vue'),
                meta: { layout: 'Pub' },
            },
            //gnb - MANAGEMENT
            {
                path: 'NIKE_P_MANAGE_01',
                component: () => import('@/pub/NIKE_P_MANAGE_01.vue'),
                meta: { layout: 'Pub' },
            },
            //UTIL - MYPAGE
            {
                path: 'NIKE_P_MY_04_004',
                component: () => import('@/pub/NIKE_P_MY_04_004.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_MY_01_001',
                component: () => import('@/pub/NIKE_P_MY_01_001.vue'),
                meta: { layout: 'Pub' },
            },
            //이메일 발송
            {
                path: 'NIKE_P_LOGIN_01e',
                component: () => import('@/pub/NIKE_P_LOGIN_01e.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_LOGIN_03e',
                component: () => import('@/pub/NIKE_P_LOGIN_03e.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_ASSET_02_001e',
                component: () => import('@/pub/NIKE_P_ASSET_02_001e.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_MANAGE_02_001e',
                component: () => import('@/pub/NIKE_P_MANAGE_02_001e.vue'),
                meta: { layout: 'Pub' },
            },
            {
                path: 'NIKE_P_ORDER_01e',
                component: () => import('@/pub/NIKE_P_ORDER_01e.vue'),
                meta: { layout: 'Pub' },
            },
        ],
    },
];
export default routes;
