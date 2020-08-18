import { pages } from '@/utils/global-methods';

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
                path: 'management',
                component: pages('report/management.vue'),
                meta: {
                    layout: 'Default',
                    historyBack: null,
                },
            },
            {
                path: ':id',
                component: pages('report/detail-view.vue'),
                meta: {
                    layout: 'Default',
                    topCode: 'report',
                    detail: {
                        show: true,
                        btn: true,
                    },
                },
            },
        ],
    },
];

export default routes;
