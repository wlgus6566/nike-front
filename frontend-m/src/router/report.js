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
                    pageTitle: 'REPORT',
                    historyBack: null,
                    tab: [
                        {
                            title: '업로드',
                            value: 'upload',
                        },
                        {
                            title: '관리',
                            value: 'management',
                        },
                    ],
                },
            },
            {
                path: 'manage',
                component: pages('report/management.vue'),
                meta: {
                    layout: 'Default',
                    pageTitle: 'REPORT',
                    historyBack: null,
                    tab: [
                        {
                            title: '업로드',
                            value: 'upload',
                        },
                        {
                            title: '관리',
                            value: 'management',
                        },
                    ],
                },
            },
        ],
    },
];

export default routes;
