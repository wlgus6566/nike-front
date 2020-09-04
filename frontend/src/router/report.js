import { pages } from '@/utils/global-methods';
import store from '@/store';

const routes = [
    {
        path: '/report',
        component: pages('report/index.vue'),
        redirect: () => {
            const idx = store.state.gnbMenuListData.findIndex(
                (el) => el.menuCode === 'REPORT'
            );
            return store.state.gnbMenuListData[idx].menus[0].menuPathUrl;
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
                    menuCode: 'MANAGE',
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
                    menuCode: 'MANAGE',
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
                    menuCode: 'MANAGE',
                },
            },
        ],
    },
];
export default routes;
