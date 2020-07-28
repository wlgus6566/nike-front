import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/pub',
        component: pages('pub'),
        meta: { layout: 'Pub', unauthorized: true },
        children: [
            {
                path: 'pageList',
                component: pages('pub/PageList'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            //guide
            {
                path: 'pagination',
                component: pages('pub/pagination.vue'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'button',
                component: pages('pub/button'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'input',
                component: pages('pub/input'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'radio',
                component: pages('pub/radio'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'checkbox',
                component: pages('pub/checkbox'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'nodata01',
                component: pages('pub/nodata01'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'nodata02',
                component: pages('pub/nodata02'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'nodata03',
                component: pages('pub/nodata03'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'nodata04',
                component: pages('pub/nodata04'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            //main
            {
                path: 'main',
                component: pages('pub/mainPage'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            //LOGIN
            {
                path: 'NIKE_P_LOGIN_01',
                component: pages('pub/NIKE_P_LOGIN_01'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_LOGIN_02',
                component: pages('pub/NIKE_P_LOGIN_02'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_LOGIN_03',
                component: pages('pub/NIKE_P_LOGIN_03'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_LOGIN_04',
                component: pages('pub/NIKE_P_LOGIN_04'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_LOGIN_05',
                component: pages('pub/NIKE_P_LOGIN_05'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_LOGIN_06',
                component: pages('pub/NIKE_P_LOGIN_06'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_LOGIN_07',
                component: pages('pub/NIKE_P_LOGIN_07'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            //gnb - ASSET
            {
                path: 'NIKE_P_ASSET_01',
                component: pages('pub/NIKE_P_ASSET_01'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'ASSET_SP_LIST',
                component: pages('pub/ASSET_SP_LIST'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'ASSET_SP_DETAIL',
                component: pages('pub/ASSET_SP_DETAIL'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            //gnb - TOOLKIT
            {
                path: 'NIKE_P_TOOLKIT_01',
                component: pages('pub/NIKE_P_TOOLKIT_01'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_TOOLKIT_02',
                component: pages('pub/NIKE_P_TOOLKIT_02'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_TOOLKIT_03',
                component: pages('pub/NIKE_P_TOOLKIT_03'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            //gnb - FOUNDATION
            {
                path: 'NIKE_P_FD_01',
                component: pages('pub/NIKE_P_FD_01'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_FD_02',
                component: pages('pub/NIKE_P_FD_02'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_FD_03',
                component: pages('pub/NIKE_P_FD_03'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            //gnb - ORDER: 부자재 주문
            {
                path: 'NIKE_P_ORDER_06',
                component: pages('pub/NIKE_P_ORDER_06'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_ORDER_01',
                component: pages('pub/NIKE_P_ORDER_01'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_ORDER_01P',
                component: pages('pub/NIKE_P_ORDER_01P'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_ORDER_02',
                component: pages('pub/NIKE_P_ORDER_02'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_ORDER_02P',
                component: pages('pub/NIKE_P_ORDER_02P'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_ORDER_07',
                component: pages('pub/NIKE_P_ORDER_07'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            //gnb - REPORT 시공보고서
            {
                path: 'NIKE_P_REPORT_01',
                component: pages('pub/NIKE_P_REPORT_01'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_REPORT_02',
                component: pages('pub/NIKE_P_REPORT_02'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_REPORT_03',
                component: pages('pub/NIKE_P_REPORT_03'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            //gnb - INFORMATION
            {
                path: 'NIKE_P_INFO_01_001',
                component: pages('pub/NIKE_P_INFO_01_001'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_INFO_01_001P',
                component: pages('pub/NIKE_P_INFO_01_001P'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_INFO_02_001P',
                component: pages('pub/NIKE_P_INFO_02_001P'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            //gnb - MANAGEMENT
            {
                path: 'NIKE_P_MANAGE_01',
                component: pages('pub/NIKE_P_MANAGE_01'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_MANAGE_02_001',
                component: pages('pub/NIKE_P_MANAGE_02_001'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_MANAGE_02_002P',
                component: pages('pub/NIKE_P_MANAGE_02_002P'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_MANAGE_03',
                component: pages('pub/NIKE_P_MANAGE_03'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            //UTIL - MYPAGE
            {
                path: 'NIKE_P_MY_04_001',
                component: pages('pub/NIKE_P_MY_04_001'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_MY_04_002',
                component: pages('pub/NIKE_P_MY_04_002'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_MY_04_003',
                component: pages('pub/NIKE_P_MY_04_003'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_MY_04_004',
                component: pages('pub/NIKE_P_MY_04_004'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_MY_04_005',
                component: pages('pub/NIKE_P_MY_04_005'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_MY_04_006',
                component: pages('pub/NIKE_P_MY_04_006'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_MY_04_007',
                component: pages('pub/NIKE_P_MY_04_007'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_MY_04_009',
                component: pages('pub/NIKE_P_MY_04_009'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_MY_01_001',
                component: pages('pub/NIKE_P_MY_01_001'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_MY_01_002',
                component: pages('pub/NIKE_P_MY_01_002'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_MY_02_001',
                component: pages('pub/NIKE_P_MY_02_001'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_MY_02_002',
                component: pages('pub/NIKE_P_MY_02_002'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_MY_03_001',
                component: pages('pub/NIKE_P_MY_03_001'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_MY_03_002',
                component: pages('pub/NIKE_P_MY_03_002'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            //이메일 발송
            {
                path: 'NIKE_P_LOGIN_01e',
                component: pages('pub/NIKE_P_LOGIN_01e'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            //ECT
            {
                path: 'NIKE_P_ERROR_01',
                component: pages('pub/NIKE_P_ERROR_01'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            {
                path: 'NIKE_P_ERROR_02',
                component: pages('pub/NIKE_P_ERROR_02'),
                meta: { layout: 'Pub', unauthorized: true },
            },
            // {
            //     path: 'NIKE_P_LOGIN_03e',
            //     component: pages('pub/NIKE_P_LOGIN_03e'),
            //    meta: { layout: 'Pub', unauthorized: true },
            // },
            // {
            //     path: 'NIKE_P_ASSET_02_001e',
            //     component: pages('pub/NIKE_P_ASSET_02_001e'),
            //    meta: { layout: 'Pub', unauthorized: true },
            // },
            // {
            //     path: 'NIKE_P_MANAGE_02_001e',
            //     component: pages('pub/NIKE_P_MANAGE_02_001e'),
            //    meta: { layout: 'Pub', unauthorized: true },
            // },
            // {
            //     path: 'NIKE_P_ORDER_01e',
            //     component: pages('pub/NIKE_P_ORDER_01e'),
            //    meta: { layout: 'Pub', unauthorized: true },
            // },
        ],
    },
];
export default routes;
