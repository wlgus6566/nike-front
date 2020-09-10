import { pages } from '@/utils/global-methods';
import store from '@/store';

const routes = [
    {
        path: '/report',
        component: pages('report/index.vue'),
        redirect: () => {
            const depth1Idx = store.state.gnbMenuListData.findIndex(
                (el) => el.menuCode === 'REPORT'
            );
            const depth2Idx = store.state.gnbMenuListData[
                depth1Idx
            ].menus.findIndex((el) => el.listYn === 'Y');

            return store.state.gnbMenuListData[depth1Idx].menus[depth2Idx]
                .menuPathUrl;
        },
        children: [
            {
                path: 'upload',
                component: pages('report/upload'),
                meta: {
                    layout: 'Default',
                    aside: 'Default',
                    title: 'REPORT <span class="ko">업로드</span>',
                    topMenuCode: 'REPORT',
                    menuCode: 'REPORT_UPLOAD',
                },
            },
            {
                path: 'modify/:id',
                component: pages('report/upload'),
                meta: {
                    layout: 'Default',
                    aside: 'Report',
                    title: 'REPORT <span class="ko">업로드 수정</span>',
                    topMenuCode: 'REPORT',
                    menuCode: 'REPORT_MANAGE',
                },
            },
            {
                path: 'management',
                component: pages('report/management'),
                meta: {
                    layout: 'Default',
                    aside: 'Report',
                    title: 'REPORT <span class="ko">관리</span>',
                    topMenuCode: 'REPORT',
                    menuCode: 'REPORT_MANAGE',
                },
            },
            {
                path: 'detail/:id',
                component: pages('report/detail-view'),
                meta: {
                    layout: 'Default',
                    aside: 'Report',
                    title: 'REPORT 상세',
                    topMenuCode: 'REPORT',
                    menuCode: 'REPORT_MANAGE',
                },
            },
        ],
    },
];
export default routes;
