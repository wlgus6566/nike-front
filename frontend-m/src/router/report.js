import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/report',
        component: pages('report/index.vue'),
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
                    detail: true,
                },
            },
        ],
    },
];

export default routes;
