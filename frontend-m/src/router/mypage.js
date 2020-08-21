import { pages } from '@/utils/global-methods';

const routes = [
    {
        path: '/mypage',
        name: 'mypage',
        component: pages('mypage/index.vue'),
        meta: {
            layout: 'Default',
            hideHeaderFooter: true,
            depth: true,
            title: '마이페이지 메인',
        },
    },
    {
        path: '/mypage',
        component: pages('root'),
        children: [
            {
                path: 'upload',
                component: pages('mypage/upload.vue'),
                meta: {
                    layout: 'Default',
                    depth: '/mypage/history',
                },
            },
            {
                path: 'latest',
                component: pages('mypage/latest.vue'),
                meta: {
                    layout: 'Default',
                    depth: '/mypage/history',
                },
            },
            {
                path: 'info',
                component: pages('mypage/info.vue'),
                meta: {
                    layout: 'Default',
                    depth: '/mypage/myinfo',
                },
            },
            {
                path: 'password',
                component: pages('mypage/password.vue'),
                meta: {
                    layout: 'Default',
                    depth: '/mypage/myinfo',
                },
            },
            {
                path: ':sectionCode/detail/:id',
                component: pages('mypage/customer-detail.vue'),
                meta: {
                    layout: 'Default',
                    depth: '/mypage/customer',
                },
            },
            {
                path: 'notice',
                component: pages('mypage/notice-list.vue'),
                meta: {
                    layout: 'Default',
                    depth: '/mypage/customer',
                },
            },
            {
                path: 'news',
                component: pages('mypage/news-list.vue'),
                meta: {
                    layout: 'Default',
                    depth: '/mypage/customer',
                },
            },
            {
                path: 'faq',
                component: pages('mypage/faq-list.vue'),
                meta: {
                    layout: 'Default',
                    depth: '/mypage/customer',
                },
            },
        ],
    },
];

export default routes;
