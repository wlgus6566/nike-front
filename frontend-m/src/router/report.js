import { pages } from '@/utils/global-methods';

const routes = [
    // {
    //     path: '/mypage',
    //     name: 'mypage',
    //     component: pages('mypage/index.vue'),
    //     meta: {
    //         layout: 'Clean',
    //         pageTitle: '',
    //         title: '마이페이지 메인',
    //     },
    // },
    {
        path: '/report',
        component: pages('root'),
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
                path: 'management',
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
