import {pages} from '@/utils/global-methods';

const routes = [
    {
        path: '/report',
        component: pages('report/index.vue'),
        redirect: '/report/upload/',
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
