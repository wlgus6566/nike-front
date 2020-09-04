import { pages } from '@/utils/global-methods';
import store from '@/store';
const routes = [
    {
        path: '/report',
        component: pages('report/index.vue'),
        redirect: () => {
            const idx = store.state.menuData.findIndex(
                el => el.menuCode === 'REPORT'
            );
            return store.state.menuData[idx].menus[0].menuPathUrl;
        },
        children: [
            {
                path: 'upload',
                component: pages('report/upload.vue'),
                meta: {
                    layout: 'Default',
                    historyBack: null,
                },
            },
            {
                path: 'modify/:id',
                component: pages('report/upload'),
                meta: {
                    layout: 'Default',
                    aside: 'Report',
                    title: 'REPORT <span class="ko">업로드 수정</span>',
                },
            },
            {
                path: 'management',
                component: pages('report/management.vue'),
                meta: {
                    layout: 'Default',
                    historyBack: null,
                },
            },
            {
                path: 'detail/:id',
                component: pages('report/detail-view.vue'),
                meta: {
                    layout: 'Default',
                    topCode: 'report',
                    detail: true,
                    btn: true,
                },
            },
        ],
    },
];

export default routes;
